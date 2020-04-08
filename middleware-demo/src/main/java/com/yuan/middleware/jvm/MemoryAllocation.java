package com.yuan.middleware.jvm;

/**
 * 内存分配是在JVM在内存分配的时候，新生代内存不足时，把新生代的存活的对象搬到老生代，然后新生代腾出来的空间用于为分配给最新的对象。
 * 这里老生代是担保人。在不同的GC机制下，也就是不同垃圾回收器组合下，担保机制也略有不同。在Serial+Serial Old的情况下，发现放不下就直接启动担保机制；
 * 在Parallel Scavenge+Serial Old的情况下，却是先要去判断一下要分配的内存是不是>=Eden区大小的一半(是否是大对象，直接放入老年代，不用触发gc)，
 * 如果是那么直接把该对象放入老生代，否则才会启动担保机制。
 *
 * @author yjm
 * @date 2020/3/26 6:31 下午
 */

public class MemoryAllocation {
    /**
     * 1M = 1024K = 1024 * 1024B
     */
    private static final int _1MB = 1024 * 1024;

    /**
     * 分配总的堆内存20M，新生代10M，老年代10M，新生代(eden from to) 8：1：1,使用Serial+SerialOld
     * -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
     * 运行空的main方法时内存布局日志如下：
     * def new generation   total 9216K, used 3690K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
     * eden space 8192K,  45% used [0x00000007bec00000, 0x00000007bef9abc8, 0x00000007bf400000)
     * from space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
     * to   space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
     * tenured generation   total 10240K, used 0K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
     * the space 10240K,   0% used [0x00000007bf6000
     * 年轻代可用空间为:total = eden + from = 9216
     * 空的main方法在eden区也有存活对象3690K，估计是运行main方法时系统自己内部的对象放在了堆空间
     */
    public static void main(String[] args) {
        //新建一个2M对象时，eden区的内存使用情况为：  eden space 8192K,  70% used = 3690 + 2048 = 5738K
        byte[] a1 = new byte[2 * _1MB];
        //新建一个2M对象时，eden区的内存使用情况为：  eden space 8192K,  95% used = 5738 + 2048 = 7786K
        byte[] a2 = new byte[2 * _1MB];
        /**
         * 再次创建一个2M对象时，发生 minor gc日志如下：
         * [GC (Allocation Failure) [DefNew: 7817K->1024K(9216K), 0.0037557 secs] 7817K->5126K(19456K), 0.0037782 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
         * 对象分配完成后堆内存分布如下：
         * def new generation   total 9216K, used 3401K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
         *   eden space 8192K,  29% used [0x00000007bec00000, 0x00000007bee52780, 0x00000007bf400000)
         *   from space 1024K, 100% used [0x00000007bf500000, 0x00000007bf600000, 0x00000007bf600000)
         *   to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
         * tenured generation   total 10240K, used 4102K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
         *    the space 10240K,  40% used [0x00000007bf600000, 0x00000007bfa01a78, 0x00000007bfa01c00, 0x00000007c0000000)
         * Metaspace       used 3070K, capacity 4496K, committed 4864K, reserved 1056768K
         *   class space    used 331K, capacity 388K, committed 512K, reserved 1048576K
         * 再次创建一个2M对象时发现eden区空间不足，发生一次gc，新生代从存活对象7817k变为存活1024k，整个堆从存活对象7817k变为5126k，
         * 整个堆的占用并没有多少变化，因为那两个2M的对象还存活，回收了一些main方法系统内部对象。
         *
         * 虚拟机会把分配的对象优先放在eden区，正常把2个2M的对象放入新生代后eden内存发现使用了95%其中还包含系统内部对象，当存放第3个2M对象时显然在新生代已经放不下了。
         * 于是发生了一次minor gc，本次gc期间发现eden存活的对象无法全部放入survivor from区（Survivor可用内存只有1MB）。
         * 这个时候虚拟机就启动了内存分配的担保机制，survivor from区满后，剩余的对象全部转移到老年代5126 - 1024 = 4102K也就是40%
         * 这个时候eden区为null了，将第3个2M对象放入eden区 2048/8192 = 0.25也就是约等于27%
         */
        byte[] a3 = new byte[2 * _1MB];
        byte[] a4 = new byte[4 * _1MB];
    }
}
