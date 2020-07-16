package com.yuan.middleware.jdk.base.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * BIO通信(一请求一应答)
 * 采用 BIO 通信模型 的服务端，通常由一个独立的 Acceptor 线程负责监听客户端的连接。我们一般通过在 while(true) 循环中服务端会调用
 * accept() 方法等待接收客户端的连接的方式监听请求，请求一旦接收到一个连接请求，就可以建立通信套接字在这个通信套接字上进行读写操作，
 * 此时不能再接收其他客户端连接请求，只能等待同当前连接的客户端的操作执行完成， 不过可以通过多线程来支持多个客户端的连接。
 * <p>
 * 如果要让 BIO 通信模型 能够同时处理多个客户端请求，就必须使用多线程（主要原因是 socket.accept()、 socket.read()、 socket.write() 涉及的
 * 三个主要函数都是同步阻塞的），也就是说它在接收到客户端连接请求之后为每个客户端创建一个新的线程进行链路处理，处理完成之后，通过输出流返回应答给客户端，线程销毁。
 * 这就是典型的 一请求一应答通信模型 。我们可以设想一下如果这个连接不做任何事情的话就会造成不必要的线程开销，不过可以通过 线程池机制 改善，
 * 线程池还可以让线程的创建和回收成本相对较低。使用FixedThreadPool 可以有效的控制了线程的最大数量，保证了系统有限的资源的控制，
 * 实现了N(客户端请求数量):M(处理客户端请求的线程数量)的伪异步I/O模型（N 可以远远大于 M），n个客户端请求，m个服务器端线程处理，其余的进去线程池的阻塞队列,
 * FixedThreadPool的阻塞队列长度为Integer.MAX_VALUE
 * <p>
 * 在 Java 虚拟机中，线程是宝贵的资源，线程的创建和销毁成本很高，除此之外，线程的切换成本也是很高的。尤其在 Linux 这样的操作系统中，线程本质上就是一个进程，
 * 创建和销毁线程都是重量级的系统函数。如果并发访问量增加会导致线程数急剧膨胀可能会导致线程堆栈溢出、创建新线程失败等问题，最终导致进程宕机或者僵死，不能对外提供服务。
 *
 * @author yjm
 * @date 2020/3/28 9:32 下午
 */
public class BIOSocket {
    private static final int PORT = 4343;

    public static void main(String[] args) {
        //创建多个线程，模拟多个客户端连接服务器
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    //在客户端创建多个线程依次连接服务器，服务器为每个客户端创建一个线程来处理。
                    Socket socket = new Socket(InetAddress.getLocalHost(), PORT);
                    socket.getOutputStream().write(("客户端" + finalI + "的请求 " + new Date() + ":hello world").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    static class IOService {
        public static void main(String[] args) throws Exception {
            //启动服务器，请求队列长度为5,
            ServerSocket serverSocket = new ServerSocket(PORT, 5);
            while (true) {
                //执行阻塞方法，直到建立连接
                Socket socket = serverSocket.accept();
                //每一个新的连接都创建一个线程，负责读取数据,如果不采用多线程，第6个客户端以后的请求就会被拒绝(包含6)，因为在阻塞的情况下前5个还没有处理完
//                new Thread(() -> {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        bufferedReader.lines().forEach(ss -> System.out.println("服务器处理，" + ss));
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                }).start();
            }

        }
    }
}
