package com.sanske.controller;

import com.sanske.annotation.Controller;
import com.sanske.annotation.Quantifier;
import com.sanske.annotation.RequestMapping;
import com.sanske.service.MyService;
import com.sanske.service.SpringmvcService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sanske
 * @date 2018/7/23 上午9:48
 **/
@Controller("sanske")
public class SpringmvcController {
    // 注解实现，实例化接口
    @Quantifier("MyServiceImpl")
    MyService myService;
    @Quantifier("SpringmvcServiceImpl")
    SpringmvcService smService;

    @RequestMapping("insert")
    public String insert(HttpServletRequest request, HttpServletResponse reponse, String param) {
        myService.insert(null);
        myService.insert(null);
        return null;
    }

    @RequestMapping("delete")
    public String delete(HttpServletRequest request, HttpServletResponse response, String param) {
        myService.delete(null);
        smService.delete(null);
        return null;
    }

    @RequestMapping("update")
    public String update(HttpServletRequest request, HttpServletResponse response, String param) {
        myService.update(null);
        smService.update(null);
        return null;
    }

    @RequestMapping("select")
    public String select(HttpServletRequest request, HttpServletResponse response, String param) {
        myService.select(null);
        smService.select(null);
        return null;
    }

}
