package com.yuan.middleware.jdk.jvm;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <p>
 * -Xmx200M -Xmn50m -XX:TargetSurvivorRatio=60 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:MaxTenuringThreshold=3
 * 最小堆为50M，默认SurvivorRatio为8，那么可以知道Eden区为40M，S0和S1为5M
 * TargetSurvivorRatio：设定survivor区的目标使用率。默认50，即survivor区对象目标使用率为50%。
 * 可以在JVM启动参数中加上-XX:+PrintTenuringDistribution，该参数可以输出age的额外信息。
 * </P>
 *
 * @author YuanJiaMin
 * @date 2021/5/11 7:22 下午
 * @description https://www.jianshu.com/p/2e69aa552b01
 */
public class GcDetail {
    public static void main(String[] args) throws InterruptedException {

        // main方法作为主线程，变量不会被回收
        byte[] byte1 = new byte[1 * 1024 * 1024];
        byte[] byte2 = new byte[1 * 1024 * 1024];

        YGC(40);
        Thread.sleep(3000);

        YGC(40);
        Thread.sleep(3000);

        YGC(40);
        Thread.sleep(3000);

        // 这次再ygc时, 由于byte1和byte2的年龄经过3次ygc后已经达到3(-XX:MaxTenuringThreshold=3), 所以会晋升到old
        YGC(40);
        // ygc后, s0(from)/s1(to)的空间为0
        Thread.sleep(3000);


        // 达到TargetSurvivorRatio这个比例指定的值,即 5M(S区)*60%(TargetSurvivorRatio)=3M(Desired survivor size)
        byte[] byte4 = new byte[1 * 1024 * 1024];
        byte[] byte5 = new byte[1 * 1024 * 1024];
        byte[] byte6 = new byte[1 * 1024 * 1024];

        // 这次ygc时, 由于s区已经占用达到了60%(-XX:TargetSurvivorRatio=60),
        // 所以会重新计算对象晋升的min(age, MaxTenuringThreshold) = 1
        YGC(40);
        Thread.sleep(3000);

        // 由于前一次ygc时算出age=1, 所以这一次再ygc时, byte4, byte5, byte6就要晋升到Old,
        // 而不需要等MaxTenuringThreshold这么多次, 此次ygc后, s0(from)/s1(to)的空间再次为0, 对象全部晋升到old
        YGC(40);
        Thread.sleep(3000);

        System.out.println("GC end!");
    }

    /**
     * 塞满Eden区，局部变量会被回收,作为触发GC的小工具
     */
    private static void YGC(int edenSize) {
        for (int i = 0; i < edenSize; i++) {
            byte[] byte1m = new byte[1 * 1024 * 1024];
        }
    }

}


