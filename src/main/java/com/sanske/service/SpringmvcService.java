package com.sanske.service;

import java.util.Map;

/**
 * @author sanske
 * @date 2018/7/23 上午9:29
 **/
public interface SpringmvcService {
    int insert(Map map);
    int delete(Map map);
    int update(Map map);
    int select(Map map);
}
