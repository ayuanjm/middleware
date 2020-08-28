package com.yuan.middleware.ans;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 缓冲区中的数据保存直到缓冲区满后才写出，也可以使用flush方法将缓冲区中的数据强制写出或使用close()方法关闭流
 *
 * @author yuanjm
 * @date 2020/8/15 4:35 下午
 */
public class Ans08 {
    public static void main(String[] args) {
//        b();
        a();

    }

    private static void b() {
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.write("uu");
        //buffer没有满，输出为空。使用flush或close后把buffer中的数据写入磁盘
        printWriter.flush();
        printWriter.close();
    }

    private static void a() {
        // 默认缓冲去大小8192字节,当设置数组长度为8192时没有输出，设置8193时有输出。
        PrintWriter writer = new PrintWriter(System.out);
        char[] chars = new char[8193];
        //将数组的每一个元素赋值为 's'
        Arrays.fill(chars, 's');
        writer.write(chars);
    }
}
