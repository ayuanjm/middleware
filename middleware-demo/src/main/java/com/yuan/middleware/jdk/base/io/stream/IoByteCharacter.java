package com.yuan.middleware.jdk.base.io.stream;

import java.io.*;

/**
 * @author yuanjm
 * @date 2020/8/28 2:56 下午
 */
public class IoByteCharacter {
    public static void main(String[] args) throws Exception {
        //字节输入流
        FileInputStream inputStream = new FileInputStream("/Users/yuan/Downloads/init.md");

        //字节流转字符流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        //输入缓冲流
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        //新建输出文件
        File file = new File("/Users/yuan/Downloads/yuan.txt");

        //字节输出流
        FileOutputStream outputStream = new FileOutputStream(file);

        //字节流转字符流
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

        //输出缓冲流
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        //使用char 数组传输    -----字节流byte数组
        char[] chs = new char[1024 * 8];

        //标记
        int len;

        // read() 方法，读取输入流的下一个字节，返回一个0-255之间的int类型整数。如果到达流的末端，返回-1
        while ((len = bufferedReader.read(chs)) != -1) {
            bufferedWriter.write(chs, 0, len);

            //刷新缓冲区
            bufferedWriter.flush();
        }

        //关闭流对象
        bufferedReader.close();
        bufferedWriter.close();
    }
}
