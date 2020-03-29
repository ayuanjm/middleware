package com.yuan.middleware.base.io.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

/**
 * @author yjm
 * @date 2020/3/29 11:11 上午
 */
public class NIOSocket {
    private static final int PORT = 4343;

    public static void main(String[] args) {
        //创建多个线程，模拟多个客户端连接服务器
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    Socket socket = new Socket(InetAddress.getLocalHost(), PORT);
                    socket.getOutputStream().write(("客户端" + finalI + "的请求 " + new Date() + ":hello world").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    static class IOService {
        /**
         * 多路复用选择器，也可以创建多个多路复用选择器，比如一个serverSelector一个clientSelector
         * serverSelector负责轮询是否有新的连接isAcceptable，服务端检测到新的连接后不会创建新的线程
         * 而是将新的连接绑定到clientSelector，clientSelector再负责轮询是否有数据需要读写
         */
        private static Selector selector;
        /**
         * 建立缓冲区
         */
        private static ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        private static ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

        public static void main(String[] args) throws Exception {
            //打开复用器
            selector = Selector.open();
            //打开服务器通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //设置服务器通道为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //绑定地址
            serverSocketChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(), PORT));
            //把服务器通道注册到多路复用器上，并监听阻塞事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server start port:" + PORT);

            while (true) {
                //让多路复用器开始监听，阻塞时间为10ms，每10ms轮询一次
                selector.select(10);
                //返回多路复用器已选择的结果集
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                //进行遍历
                while (keys.hasNext()) {
                    //获取一个选择的连接
                    SelectionKey key = keys.next();
                    //已经获取的需要移除
                    keys.remove();
                    //如果是有效的
                    if (key.isValid()) {
                        //如果是阻塞状态
                        if (key.isAcceptable()) {
                            accept(key, selector);
                        }
                        //如果是可读状态
                        if (key.isReadable()) {
                            read(key);
                        }
                        //写数据
//                        if (key.isWritable()) {
//                            //this.write(key); //ssc
//                        }
                    }
                }
            }
        }

        private void write(SelectionKey key) {
            //ServerSocketChannel ssc =  (ServerSocketChannel) key.channel();
            //ssc.register(this.seletor, SelectionKey.OP_WRITE);
        }

        private static void read(SelectionKey key) throws IOException {
            //清空缓冲区旧的数据
            readBuffer.clear();
            //获取之前注册的SocketChannel对象，之前在accept方法注册的是SocketChannel不是ServerSocketChannel
            SocketChannel socketChannel = (SocketChannel) key.channel();
            //读取数据
            int read = socketChannel.read(readBuffer);
            //如果没有数据
            if (read == -1) {
                //关闭通道
                socketChannel.close();
                key.cancel();
                return;
            }
            //有数据则进行读取，读取之前需要进行复位方法(把position 和limit进行复位)
            readBuffer.flip();
            //根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
            byte[] bytes = new byte[readBuffer.remaining()];
            //接收缓冲区数据
            readBuffer.get(bytes);
            String s = new String(bytes, "utf-8");
            System.out.println("Server:" + s);
            //TODO...可以在这里写回给客户端的数据
        }

        private static void accept(SelectionKey key, Selector selector) throws IOException {
            //根据SelectionKey获取对应的服务通道,由于是通过服务器通道获取的阻塞事件，所以得到的通道是ServerSocketChannel
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            //执行阻塞方法，直到建立连接，每新来一个连接不需要创建一个线程
            SocketChannel socketChannel = serverSocketChannel.accept();
            //将新的客户端连接设置阻塞模式
            socketChannel.configureBlocking(false);
            //将新的客户端连接注册到多路复用器上，并设置读取标示
            socketChannel.register(selector, SelectionKey.OP_READ);
        }
    }
}
