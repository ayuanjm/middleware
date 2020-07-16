package com.yuan.middleware.jdk.design.decorator;

/**
 * @author yjm
 * @date 2020/3/13 7:20 下午
 */
public class RedBean extends Decorator {
    public RedBean(Drink drink) {
        super(drink);
    }

    @Override
    public double money() {
        return super.money() + 1;
    }

    @Override
    public String describe() {
        return super.describe() + "加红豆";
    }

    public static void main(String[] args) {
        Soya soya = new Soya();
        System.out.println(soya.describe());
        RedBean redBean = new RedBean(soya);
        String describe = redBean.describe();
        System.out.println(describe);
    }
}
