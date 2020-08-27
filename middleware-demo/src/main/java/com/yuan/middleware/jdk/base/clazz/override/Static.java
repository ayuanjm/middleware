package com.yuan.middleware.jdk.base.clazz.override;

/**
 * @author yuanjm
 * @date 2020/8/27 2:34 下午
 */
public class Static {
    int num = 9;

    static {
        System.out.println("demo static");
    }

    public Static() {
        System.out.println("demo con");
    }

    {
        System.out.println("demo method");
    }

    void show() {
        System.out.println(num);
    }

    static class A extends Static {
        int num = 1;

        static {
            System.out.println("a static");
        }

        public A() {
            System.out.println("a con");
        }

        {
            System.out.println("a method");
        }

        @java.lang.Override
        void show() {
            super.show();
            System.out.println(num);
        }

        public static void main(String[] args) {
            A a = new A();
            a = new A();
            a.show();
        }
    }
}
