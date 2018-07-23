package com.sanske.servlet;

import com.sanske.annotation.Controller;
import com.sanske.annotation.Quantifier;
import com.sanske.annotation.RequestMapping;
import com.sanske.annotation.Service;
import com.sanske.controller.SpringmvcController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sanske
 * @date 2018/7/23 上午10:05
 **/
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersinUID = 1L;
    List<String> packageNames = new ArrayList<String>();
    Map<String, Object> instanceMap = new HashMap<String, Object>();
    Map<String, Object> handleMap = new HashMap<String, Object>();

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //包扫描， 获取包中的文件放入的一个 List 集合中
        scanPackage("com.sanske");
        try {
            filterAndIntance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //处理映射关系
        handleMap();
        //实现注入
        ioc();
    }

    /**
     * 将所有的扫描到的包加入的到List集合
     *
     * @param packagePath
     */
    private void scanPackage(String packagePath) {
        URL url = this.getClass().getClassLoader().getResource("/" + packagePath.replaceAll("\\.", "/"));
        String pathFile = url.getFile();
        File file = new File(pathFile);
        String fileList[] = file.list();
        for (String path : fileList) {
            File eachFile = new File(pathFile + path);
            //文件和文件夹是同一个
            if (eachFile.isDirectory()) {
                scanPackage(packagePath + "." + eachFile.getName());
            } else {
                packageNames.add(packagePath + "." + eachFile.getName());
            }
        }

    }

    /**
     * 查找 Controller和Service中的所有注解，再实例化对象
     */
    private void filterAndIntance() throws Exception {
        if (packageNames.size() <= 0) {
            return;
        }
        for (String className : packageNames) {
            Class<?> cName = Class.forName(className.replaceAll(".class", "").trim());
            if (cName.isAnnotationPresent(Controller.class)) {
                Object instance = cName.newInstance();
                Controller controller = (Controller) cName.getAnnotation(Controller.class);
                String key = controller.value();
                instanceMap.put(key, instance);
            } else if (cName.isAnnotationPresent(Service.class)) {
                Object instance = cName.newInstance();
                Service service = (Service) cName.getAnnotation(Service.class);
                String key = service.value();
                instanceMap.put(key, instance);
            } else {
                continue;
            }
        }

    }


    /**
     * 映射处理Controller中的方法
     */
    private void handleMap() {
        if (instanceMap.size() <= 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            if (entry.getValue().getClass().isAnnotationPresent(Controller.class)) {
                Controller controller = (Controller) entry.getValue().getClass().getAnnotation(Controller.class);
                String ctvalue = controller.value();
                Method[] methods = entry.getValue().getClass().getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping rm = (RequestMapping) method.getAnnotation(RequestMapping.class);
                        String rmvalue = rm.value();
                        handleMap.put("/" + ctvalue + "/" + rmvalue, method);
                    } else {
                        continue;
                    }
                }
            } else {
                continue;
            }
        }

    }


    private void ioc() {
        if (instanceMap.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            Field fields[] = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                //默认属性是不可访问的
                field.setAccessible(true);
                if (field.isAnnotationPresent(Quantifier.class)) {
                    Quantifier quantifier = field.getAnnotation(Quantifier.class);
                    String value = quantifier.value();
                    try {
                        field.set(entry.getValue(), instanceMap.get(value));
                    } catch (Exception e) {

                    }
                } else {
                    continue;
                }
            }

        }

        SpringmvcController sanske = (SpringmvcController) instanceMap.get("sanske");
        System.out.println(sanske);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String context = req.getContextPath();
        String path = url.replace(context, "");
        Method method = (Method) handleMap.get(path);
        SpringmvcController controller = (SpringmvcController) instanceMap.get(path.split("/")[1]);
        try {
            method.invoke(controller, new Object[]{req, resp, null});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
