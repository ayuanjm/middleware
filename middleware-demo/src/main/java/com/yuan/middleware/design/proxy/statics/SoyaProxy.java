package com.yuan.middleware.design.proxy.statics;

/**
 * 代理类
 *
 * @author yjm
 * @date 2020/3/13 7:19 下午
 */
public class SoyaProxy implements Drink {
    private Drink drink;

    public SoyaProxy() {
        //关系在编译时确定
        this.drink = new Soya();
    }

    @Override
    public double money() {
        return drink.money();
    }

    @Override
    public String describe() {
        return drink.describe() + "proxy";
    }

    public static void main(String[] args) {
        Drink soya = new Soya();
        System.out.println(soya.describe());
        Drink soyaProxy = new SoyaProxy();
        System.out.println(soyaProxy.describe());
    }
}
