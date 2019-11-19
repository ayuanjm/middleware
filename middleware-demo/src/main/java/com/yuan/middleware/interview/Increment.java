package com.yuan.middleware.interview;

import lombok.extern.slf4j.Slf4j;

/**
 * 自增操作
 * 赋值=，最后计算
 * =右边的从左到右加载值，依次压入操作数栈
 * 实际先算哪个，看运算符优先级
 * 自增，自减操作都是直接修改变量的值，不经过操作数栈
 * 最后赋值之前，临时结果也是存储在操作数栈中
 * jvm指令
 * @author yuan
 * @date 2019-11-19 10:51
 */
@Slf4j
public class Increment {
    public static void main(String[] args) {
        incr();
    }

    /**
     * (操作栈也是栈:先进后出)
     * int k = i + ++i * i++;
     * 1. 把i的值压入操作数栈   2
     * 2. i变量自增1
     * 3. 把i的值压入操作数栈   3
     * 4. 把i的值压入操作数栈   3
     * 5. i变量自增1
     * 6. 把操作数中前两个弹出求乘积结果再压入栈（2,3,3）
     * 由于是栈 先进后出 前两个为(3,3)乘积为9 压入栈,这时候栈为(2,9) 9+2=11
     */
    private static void incr() {
        int i = 1;
        i = i++;
        int j = i++;
        int k = i + ++i * i++;
        //i:4,j:1,k:11
        log.info("i:{},j:{},k:{}", i, j, k);
    }

    /**
     * i++ 相当于这个方法
     * 先读取i的值，再将tmp+1，最后赋值给i
     *
     * @param i
     * @return
     */
    static int firstIncr(int i) {
        int temp = i;
        i = temp + 1;
        return temp;
    }

    /**
     * ++i 相当于这个方法
     * 取i值，再加1，最后返回
     *
     * @param i
     * @return
     */
    static int lastIncr(int i) {
        i = i + 1;
        return i;
    }

}
