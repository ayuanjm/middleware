package com.yuan.middleware.design.decorator;

/**
 * 被装饰者
 *
 * @author yjm
 * @date 2020/3/13 7:17 下午
 */
public class Soya implements Drink {
    @Override
    public double money() {
        return 5;
    }

    @Override
    public String describe() {
        return "豆浆";
    }
}
