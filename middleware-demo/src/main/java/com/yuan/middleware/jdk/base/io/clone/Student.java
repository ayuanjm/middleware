package com.yuan.middleware.jdk.base.io.clone;

import lombok.Data;

import java.io.*;

/**
 * @author yuanjm
 * @date 2020/9/3 4:13 下午
 */
@Data
public class Student implements Serializable {
    private String name;
    private int age;
    private Major major;

    public Student(String name, int age, Major major) {
        this.name = name;
        this.age = age;
        this.major = major;
    }

    public static void main(String[] args) {
        Major major = new Major("计算机", 1);
        Student student = new Student("yuan", 22, major);
        Student newStudent = (Student) student.clone();
        Major major1 = newStudent.getMajor();
        major1.setMajorId(2);
        System.out.println(student);
        System.out.println(newStudent);
    }

    @Override
    protected Object clone() {
        try {
            //将对象本身序列化到字节流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            //再将字节流反序列化得到对象副本
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object o = objectInputStream.readObject();
            return o;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
