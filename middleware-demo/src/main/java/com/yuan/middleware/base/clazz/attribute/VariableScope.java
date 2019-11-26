package com.yuan.middleware.base.clazz.attribute;

/**
 * 成员变量与局部变量
 * 1、全局变量：直接在类中声明的变量叫成员变量(又称全局变量)。注：如果未对成员变量设置初始值，则系统会根据成员变量的类型自动分配初始值：int分配初始值0、boolean分配初始值false，而自定义类型则分配初始值null。
 * 2、作用范围：成员变量定义后，其作用域是其所在的整个类。注：成员变量的定义没有先后顺序，但是最好将成员变量的定义集中在类的顶部。
 * 3、局部变量：方法形参、方法中定义的变量和代码块中定义的变量统称为局部变量。注：局部变量在使用以前必须显式初始化或赋值，局部变量没有默认值。
 * 注：声明局部变量时，数据类型前除final外不允许有其他关键字，即其定义格式为： [final] 数据类型 变量名 = 初始值；
 * 4、作用范围：局部变量的作用域范围从定义的位置开始到其所在语句块结束。
 * 5注意点：
 * 1、如果局部变量的名字与全局变量的名字相同，则在局部变量的作用范围内全局变量被隐藏，即这个全局变量在同名局部变量所在方法内暂时失效。
 * 2、如果在局部变量的作用域范围内访问该成员变量，则必须使用关键字this来引用成员变量
 * 变量具有优先级，如果方法中有局部变量，则局部变量优先读取，
 * 如果方法中没有局部变量，类体内、方法外有成员变量，则读取成员变量
 * 俗称【就近原则】
 *
 * 局部变量：栈
 * 实例变量：堆
 * 成员变量：方法区
 *
 * @author yuan
 * @date 2019/11/26
 */
public class VariableScope {
    /**
     * 成员变量，类变量
     */
    static int s;
    /**
     * 成员变量
     */
    int i;
    /**
     * 成员变量
     */
    int j;

    {
        //代码块中的局部变量
        int i = 1;
        //就近原则，取的是局部变量i,如果要取成员变量i可以使用this.i
        i++;
        j++;
        s++;
    }

    /**
     * @param j 形参，局部变量
     */
    public void test(int j) {
        //j取的是参数的j不会影响原来的j(局部变量的j)
        j++;
        i++;
        s++;
    }

    /**
     * @param args 形参，局部变量
     */
    public static void main(String[] args) {
        //局部变量
        VariableScope scope = new VariableScope();
        //局部变量
        VariableScope scope1 = new VariableScope();
        scope.test(10);
        scope.test(20);
        scope1.test(30);
        System.out.println(scope.i + "," + scope.j + "," + VariableScope.s);
        System.out.println(scope1.i + "," + scope1.j + "," + VariableScope.s);
    }
}
