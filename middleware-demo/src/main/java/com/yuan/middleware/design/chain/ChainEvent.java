package com.yuan.middleware.design.chain;

import lombok.Data;

import java.util.Date;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: sy
 * Author: yuanjiamin
 * CreateDate: 2020/12/1 8:56 下午
 * Description:
 */
@Data
public class ChainEvent {
    private Date date;
    private String name;
    private Integer count = 0;
}
