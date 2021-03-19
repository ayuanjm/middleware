package com.yuan.middleware.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * 事件定义
 *
 * @author yuan
 * @date 2019/12/24
 */
public class EvenSource extends ApplicationEvent {
    private String message;

    public EvenSource(Object source, String message) {
        super(source);
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

}
