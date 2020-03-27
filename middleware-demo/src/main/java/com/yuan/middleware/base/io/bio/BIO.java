package com.yuan.middleware.base.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * https://blog.csdn.net/tian779278804/article/details/50922354/
 *
 * @author yjm
 * @date 2020/3/27 12:15 下午
 */
public class BIO {
    private static final int SERVER_PORT = 4343;

    public static void main(String[] args) throws Exception {
        //服务器启动
        Service service = new Service();
        new Thread(() -> {
            Socket[] sockets = new Socket[3];
            for (int i = 0; i < sockets.length; i++) {
                //模拟Socket客户端请求建立连接，接收信息并打印
                try {
                    sockets[i] = new Socket(InetAddress.getLocalHost(), SERVER_PORT);
                    System.out.println("客户端" + i + "建立连接成功");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sockets[i].getInputStream()));
                    bufferedReader.lines().forEach(ss -> System.out.println("客户端" + ss));
                    bufferedReader.close();
                    //客户端关闭连接
                    sockets[i].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        service.accept();


    }

    static class Service {
        private ServerSocket serverSocket;

        public Service() throws IOException {
            //同步阻塞io,请求建立连接队列长度为3
            serverSocket = new ServerSocket(SERVER_PORT, 3);
            System.out.println("服务器启动。。。");
        }

        /**
         * 模拟socket服务器端（简单的发送信息）
         *
         * @throws IOException
         * @throws InterruptedException
         */
        public void accept() throws Exception {
            while (true) {
                //等待连接时会阻塞，建立连接后放行，从连接请求队列中取出下一个连接
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    System.out.println("建立连接" + socket.toString());
                    try {
                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                        printWriter.println("hello socket");
                        printWriter.println("yuan");
                        printWriter.flush();
                        printWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (socket != null) {
                            try {
                                //服务器发送完处理结果后,关闭Socket,3次握手与4次挥手
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        }
    }
}