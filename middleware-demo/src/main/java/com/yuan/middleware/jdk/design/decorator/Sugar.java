package com.yuan.middleware.jdk.design.decorator;

/**
 * @author yjm
 * @date 2020/3/13 7:27 下午
 */
public class Sugar extends Decorator {
    public Sugar(Drink drink) {
        super(drink);
    }

    @Override
    public double money() {
        return super.money() + 1;
    }

    @Override
    public String describe() {
        return super.describe() + "加糖";
    }

    public static void main(String[] args) {
        //初始豆浆
        Soya soya = new Soya();
        System.out.println(soya.describe());

        //加红豆的豆浆
        RedBean redBean = new RedBean(soya);
        System.out.println(redBean.describe());

        //加糖的豆浆
        Sugar sugar = new Sugar(soya);
        System.out.println(sugar.describe());

        //在加糖的豆浆 上再次修饰 加红豆
        RedBean redBean1 = new RedBean(sugar);
        System.out.println(redBean1.describe());
    }

}
