package com.yuan.middleware.jdk.jvm;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: sy
 * Author: yuanjiamin
 * CreateDate: 2020/11/27 9:52 上午
 * Description: -Xss512k 设置栈容量大小
 */
public class JavaVMStackSOF {
    /**
     * 栈深
     */
    private int stackLength = 1;

    /**
     * @return
     * @Description: -Xss512k，没有局部变量，栈帧较小，输出栈深为5762
     * @author yuanjiamin
     * @date 2020/11/27 10:47 上午
     */
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    /**
     * @return
     * @Description: -Xss512k，定义多个局部变量，栈帧较大，输出的栈深为1728
     * @author yuanjiamin
     * @date 2020/11/27 10:46 上午
     */
    public void stackLeakWithBigVariable() {
        long var1, var2, var3, var4, var5, var6, var7, var8, var9, var10;
        stackLength++;
        stackLeakWithBigVariable();
        var1 = var2 = var3 = var4 = var5 = var6 = var7 = var8 = var9 = var10 = 10;
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
//            oom.stackLeakWithBigVariable();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
