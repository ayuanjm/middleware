package com.yuan.middleware.jdk.base.clazz.override;

/**
 * private
 * 被private修饰的属性和方法，不能被其他类访问，子类不能继承也不能访问。只能在所在类内部访问。
 * <p>
 * default
 * 变量或者方法前没有访问修饰符时，可以被所在类访问，可以被同一包内的其他类访问 或者继承。但是不能被其他包访问。
 * <p>
 * protected
 * 被protected修饰的方法和属性，在同一包内可被访问和继承。不同包内，子类可继承，非子类不能访问。
 * <p>
 * public
 * 方法和属性前有public修饰，可以被任意包内的类访问。
 * 另外，类要想被其他包导入，必须声明为public。被public修饰的类，类名必须与文件名相同。
 *
 * @author yuanjm
 * @date 2020/8/27 2:01 下午
 */
public abstract class Override {

    /**
     * 抽象类的属性可以任意修饰,默认为default
     */
    private final int a = 10;

    int b;

    /**
     * 抽象类中的方法修饰符只能是protected、public，默认为public
     */
    protected abstract int show(int a, String b);

    static class Ov extends Override {
        /**
         * 重写：方法名、参数、返回值必须一样，修饰类型范围必须不能比父类小。
         */
        @java.lang.Override
        protected int show(int a, String b) {
            return 0;
        }

        /**
         * 重载：方法名必须相同、方法参数必须不同、方法返回值可以不同、方法修饰符可以不同没有范围限制。
         */
        private short show(String b) {
            return 0;
        }

        public static void main(String[] args) {
            Ov ov = new Ov();
            System.out.println(ov.b);
        }
    }
}
