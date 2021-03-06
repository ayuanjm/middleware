package com.yuan.middleware.spring.dao;


import com.yuan.middleware.spring.entity.Dept;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yuan
 */
@Mapper
public interface DeptDao {
    boolean addDept(Dept dept);

    Dept findById(Long id);

    List<Dept> findAll();

    Integer updateDept(Dept dept);
}
