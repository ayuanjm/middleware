package com.yuan.middleware.base.io.bio;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 传统的 IO 大致可以分为4种类型：
 * InputStream、OutputStream 基于字节操作的 IO
 * Writer、Reader 基于字符操作的 IO
 * File 基于磁盘操作的 IO
 * Socket 基于网络操作的 IO
 * <p>
 * 链接：http://www.imooc.com/article/265871
 *
 * @author yjm
 * @date 2020/3/27 11:29 上午
 */
public class IoDemo {
    private static final String FILE_NAME = "/Users/yuan/Downloads/yuan.sql";

    public static void main(String[] args) throws IOException {
//        input();
//        output();
//        writer();
//        read();
        files();
        return;
    }

    /**
     * Java 7 引入了Files（java.nio包下）的，大大简化了文件的读写
     * 读写文件都是一行代码搞定，没错这就是最优雅的文件操作。内部都封装好了
     *
     * @throws IOException
     */
    private static void files() throws IOException {
        String content = "yuan";
        Files.write(Paths.get(FILE_NAME), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        byte[] bytes = Files.readAllBytes(Paths.get(FILE_NAME));
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

    private static void read() throws IOException {
        Reader reader = new FileReader(FILE_NAME);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer stringBuffer = new StringBuffer();
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            stringBuffer.append(str + "\n");
        }
        bufferedReader.close();
        reader.close();
        System.out.println(stringBuffer.toString());
    }

    private static void writer() throws IOException {
        //append:是否追加，true表示追加
        Writer writer = new FileWriter(FILE_NAME, true);
        writer.append("\n hello world!");
        writer.close();
    }

    private static void output() throws IOException {
        //append:是否追加，true表示追加
        OutputStream outputStream = new FileOutputStream(FILE_NAME, true);
        outputStream.write("hello world".getBytes("utf-8"));
        outputStream.close();
    }

    private static void input() throws IOException {
        InputStream inputStream = new FileInputStream(FILE_NAME);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String s = new String(bytes, "utf-8");
        System.out.println(s);
        inputStream.close();
    }
}
