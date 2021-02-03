package com.yuan.middleware.jdk.base.io.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 流操作工具类
 *
 * @author yuan
 * @since 1.0.0
 */
public class StreamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取字符串
     */
    public static String getString(InputStream is) {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while (((line = reader.readLine()) != null)) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("get string fail", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 把输入流的文件输出到指定文件位置
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void createFile(InputStream inputStream, OutputStream outputStream) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            String s;
            while ((s = reader.readLine()) != null) {
                writer.write(s, 0, s.length());
                //换行
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] getUrlFileData(String fileUrl) {
        InputStream cin = null;
        ByteArrayOutputStream outStream = null;
        byte[] fileData = new byte[0];
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.connect();
            cin = httpConn.getInputStream();
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = cin.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            fileData = outStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (cin != null) {
                    cin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return fileData;
    }

    /**
     * 读取url内容
     *
     * @param savePath 需要保存的路径
     * @param urlPath  url地址
     */
    private static void urlInputStream(String savePath, String urlPath) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            //使用File类创建一个要操作的文件路径
            File file = new File(savePath);
            //可以创建url实例
            URL url = new URL(urlPath);
            //字节输入流
            InputStream inputStream = url.openStream();

            //字节流转字符流
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            //再转缓冲流  提高读取效率
            br = new BufferedReader(isr);
            //文件输出流
            OutputStream output = new FileOutputStream(file);
            //字符缓冲流输出转化流
            bw = new BufferedWriter(new OutputStreamWriter(output));
            //使用char 数组传输    -----字节流byte数组
            char[] chs = new char[1024];
            //标记
            int len;
            // read() 方法，读取输入流的下一个字节，返回一个0-255之间的int类型整数。如果到达流的末端，返回-1
            while ((len = br.read(chs)) != -1) {
                //清除缓存
                bw.write(chs, 0, len);
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(bw, br);
        }
    }

    /**
     * 关闭方法
     */
    private static void close(AutoCloseable... ac) {
        for (AutoCloseable autoCloseable : ac) {
            if (autoCloseable != null) {
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("/Users/yuan/Downloads/Java综合总结.pdf");
        FileOutputStream outputStream = new FileOutputStream("/Users/yuan/Downloads/outputStream.pdf");
//        System.out.println(getString(inputStream));
        createFile(inputStream, outputStream);
    }
}
