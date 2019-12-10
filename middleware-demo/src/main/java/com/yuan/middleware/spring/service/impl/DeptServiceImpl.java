package com.yuan.middleware.spring.service.impl;


import com.yuan.middleware.spring.dao.DeptDao;
import com.yuan.middleware.spring.entity.Dept;
import com.yuan.middleware.spring.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao dao;

    @Override
    public boolean add(Dept dept) {
        return dao.addDept(dept);
    }

    @Override
    public Dept get(Long id) {
        dao.findById(id);
        //测试可重复读REPEATABLE-READ
        /**
         *
         */
		log.info("Repeatable Read（可重复读）：即：事务A在读到一条数据之后，此时事务B对该数据进行了修改并提交，那么事务A再读该数据，读到的还是原来的内容。");
        return dao.findById(id);
    }

    @Override
    public List<Dept> list() {
        return dao.findAll();
    }

    @Override
    public Dept updateDept(Dept dept) {
        System.out.println(dept);
        return dao.updateDept(dept);
    }
}
