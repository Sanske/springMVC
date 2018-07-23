package com.sanske.service;

import com.sanske.annotation.Service;

import java.util.Map;

/**
 * @author sanske
 * @date 2018/7/20 下午5:08
 **/
@Service("MyServiceImpl")
public class MyServiceImpl implements MyService {
    @Override
    public int insert(Map map) {
        System.out.println("MyServiceImpl: insert");
        return 0;
    }

    @Override
    public int delete(Map map) {
        System.out.println("MyServiceImpl: delete");
        return 0;
    }

    @Override
    public int update(Map map) {
        System.out.println("MyserviceImpl: update");
        return 0;
    }

    @Override
    public int select(Map map) {
      System.out.println("MyServiceImpl:  select");
      return 0;
    }

}
