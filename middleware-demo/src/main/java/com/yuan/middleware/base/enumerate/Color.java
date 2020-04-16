package com.yuan.middleware.base.enumerate;

import java.util.Arrays;

/**
 * @author yjm
 * @date 2020/3/31 3:01 下午
 */
public enum Color {

    /**
     * 抽象类不能实例化，只能子类继承，重写抽象方法
     * 相当于继承了抽象类Color的类，生成的实例
     */
    RED("红色", 1) {
        @Override
        void show(String message) {
            System.out.println(message);
        }
    },
    GREEN("绿色", 2) {
        @Override
        void show(String message) {
            System.out.println(message);
        }
    },
    BLANK("白色", 3) {
        @Override
        void show(String message) {
            System.out.println(message);
        }
    },
    YELLOW("黄色", 4) {
        @Override
        void show(String message) {
            System.out.println(message);
        }
    };

    /**
     * 因为这个抽象方法Color类变成了一个抽象类
     *
     * @param message
     */
    abstract void show(String message);

    private String name;
    private int code;

    Color(String name, int code) {
        this.name = name;
        this.code = code;
    }

    /**
     * 根据name选择对应的Color实例
     *
     * @param name
     * @return
     * @throws Exception
     */
    public static Color nameOf(String name) throws Exception {
        return Arrays.stream(Color.values()).
                filter(color -> color.name.equals(name)).
                findFirst().orElseThrow(() -> new Exception("not find color where name =" + name)
        );
    }

    public static void main(String[] args) {
        System.out.println(1);
    }
}
