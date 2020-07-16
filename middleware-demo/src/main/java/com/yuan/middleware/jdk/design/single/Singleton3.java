package com.yuan.middleware.jdk.design.single;

import lombok.Data;

import java.io.IOException;
import java.util.Properties;

/**
 * 饿汉式
 * <p>
 * 静态代码块饿汉式，也是线程安全的
 * 适合于需要灵活初始化属性
 *
 * @author yuan
 */
@Data
public class Singleton3 {
    public static final Singleton3 INSTANCE;

    private String info;

    static {
        Properties pro = new Properties();
        try {
            //通过类加载器加载配置文件,需要特别注意文件路径否则NullPointerException
            pro.load(Singleton3.class.getClassLoader().getResourceAsStream("single.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        INSTANCE = new Singleton3(pro.getProperty("info"));
    }

    private Singleton3(String info) {
        this.info = info;
    }

    public static void main(String[] args) {

        Singleton3 instance = Singleton3.INSTANCE;
        System.out.println(instance.info);
    }
}
