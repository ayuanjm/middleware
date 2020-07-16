package com.yuan.middleware.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuan
 * @date 2019/11/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeanScope {
    private String name;
    private int age;

    public static void main(String[] args) {
        //builder 模式
        BeanScope yuan = BeanScope.builder().name("yuan").age(22).build();
        System.out.println(yuan.toString());
    }
}
