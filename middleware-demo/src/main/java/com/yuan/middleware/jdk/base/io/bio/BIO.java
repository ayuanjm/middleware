package com.yuan.middleware.jdk.base.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 同步阻塞I/O模式，数据的读取写入必须阻塞在一个线程内等待其完成。
 * 通常由一个独立的 Acceptor 线程负责监听客户端的连接。我们一般通过在 while(true) 循环中服务端会调用 accept() 方法等待接收客户端的连接的方式监听请求，
 * 请求一旦接收到一个连接请求，就可以建立通信套接字在这个通信套接字上进行读写操作，此时不能再接收其他客户端连接请求，只能等待同当前连接的客户端的操作执行完成，
 * 不过可以通过多线程来支持多个客户端的连接。
 * 如果要让 BIO 通信模型 能够同时处理多个客户端请求，就必须使用多线程（主要原因是 socket.accept()、 socket.read()、 socket.write()
 * 涉及的三个主要函数都是同步阻塞的），也就是说它在接收到客户端连接请求之后为每个客户端创建一个新的线程进行链路处理，处理完成之后，通过输出流返回应答给客户端，
 * 线程销毁。这就是典型的 一请求一应答通信模型 。我们可以设想一下如果这个连接不做任何事情的话就会造成不必要的线程开销
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
                int finalI = i;
                new Thread(()->{
                   try {
                       sockets[finalI] = new Socket(InetAddress.getLocalHost(), SERVER_PORT);
                       System.out.println("客户端" + finalI + "建立连接成功");
                       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sockets[finalI].getInputStream()));
                       bufferedReader.lines().forEach(ss -> System.out.println("客户端" + ss));
                       bufferedReader.close();
                       //客户端关闭连接
                       sockets[finalI].close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }).start();
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
                //每一个新的连接都有建立一个新的线程，负责处理客户端请求，如果客户端只是建立连接，没有进行读写，会造成不必要的线程开销
                //当并发量大时，创建大量的线程可能会导致OOM，并且线程的创建销毁非常消耗性能，但是你在BIO模式下如果不用多线程就只能阻塞等待前一个请求处理完，再处理下一个。
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