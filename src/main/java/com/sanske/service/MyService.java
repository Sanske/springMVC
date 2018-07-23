package com.sanske.service;

import java.util.Map;

/**
 * @author sanske
 * @date 2018/7/20 下午5:06
 **/
public interface MyService {
    int insert(Map map);
    int delete(Map map);
    int update(Map map);
    int select(Map map);
}
