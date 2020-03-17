package com.yuan.middleware.spring.service;


import com.yuan.middleware.spring.entity.Dept;

import java.util.List;

public interface DeptService {
    boolean add(Dept dept);

    Dept get(Long id);

    List<Dept> list();

    /**
     * 测试mybatis<if test="dname != null">
     * @param dept
     * @return
     */
    Dept updateDept(Dept dept);

    void show(String message);

}
