package com.yuan.middleware.jdk.base.exception;

import java.io.*;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * Project: message
 *
 * @author: yuanjiamin
 * CreateDate: 2021/2/3 11:08 上午
 * Description: 要构造一个能被try-with-resources块正确处理的自定义资源，类应该实现Closeable或AutoCloseable接口，并重写close方法:
 */
public class Automatically {
    public static void main(String[] args) {
        //try-with-resource
        try (FileInputStream fileInputStream = new FileInputStream("/Users/yuan/Downloads/阿里开发手册");
             FileOutputStream fileOutputStream = new FileOutputStream("/Users/yuan/Downloads/io")) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            char[] chars = new char[1024 * 8];
            int len;
            while ((len = bufferedReader.read(chars)) != -1) {
                bufferedWriter.write(chars, 0, len);
                bufferedWriter.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
