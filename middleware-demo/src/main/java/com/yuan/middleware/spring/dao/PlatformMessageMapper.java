package com.yuan.middleware.spring.dao;

import com.yuan.middleware.spring.entity.PlatformMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlatformMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PlatformMessage record);

    int insertSelective(PlatformMessage record);

    PlatformMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlatformMessage record);

    int updateByPrimaryKey(PlatformMessage record);
}