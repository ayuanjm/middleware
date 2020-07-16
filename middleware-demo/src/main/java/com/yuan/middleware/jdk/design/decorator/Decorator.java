package com.yuan.middleware.jdk.design.decorator;

/**
 * 装饰者
 *
 * @author yjm
 * @date 2020/3/13 7:19 下午
 */
public class Decorator implements Drink {
    private Drink drink;

    public Decorator(Drink drink) {
        this.drink = drink;
    }

    @Override
    public double money() {
        return drink.money();
    }

    @Override
    public String describe() {
        return drink.describe();
    }
}
