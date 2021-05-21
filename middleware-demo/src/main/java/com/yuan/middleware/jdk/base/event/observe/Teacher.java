package com.yuan.middleware.jdk.base.event.observe;

import java.util.Observable;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P>https://zhuanlan.zhihu.com/p/27185877</P>
 *
 * @author YuanJiaMin
 * @date 2021/5/20 3:58 下午
 * @description
 */
public class Teacher extends Observable {

    private String name;

    public String getName() {
        return this.name;
    }

    public Teacher(String name) {
        this.name = name;
    }

    public void setHomework(String homework) {
        System.out.printf("%s布置了作业%s \n", this.name, homework);
        setChanged();
        notifyObservers(homework);
    }

    public static void main(String[] args) {
        Student student1 = new Student("张三");
        Student student2 = new Student("李四");
        Teacher teacher1 = new Teacher("yuanjm");
        teacher1.addObserver(student1);
        teacher1.addObserver(student2);
        teacher1.setHomework("事件机制第一天作业");
    }
}
