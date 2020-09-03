package com.yuan.middleware.jdk.base.io.clone;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yuanjm
 * @date 2020/9/3 4:14 下午
 */
@Data
public class Major implements Serializable {
    private String majorName;
    private int majorId;

    public Major(String majorName, int majorId) {
        this.majorName = majorName;
        this.majorId = majorId;
    }
}
