package com.yuan.middleware.base.io.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yjm
 * @date 2020/3/27 11:21 下午
 */
public class NIO {
    private static final int SERVER_PORT = 4555;

    public static void main(String[] args) throws Exception {
        //Socket 客户端（接收信息并打印）
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        boolean connect = socketChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), SERVER_PORT));
        //如果连接到了服务器
        if (connect) {
            //把通道socketChannel在多路复用器selector上注册为OP_READ
            socketChannel.register(selector, SelectionKey.OP_READ);
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
        client(selector);
    }

    private static void client(Selector selector) throws IOException {
        // 阻塞等待就绪的Channel，最大阻塞时间
        selector.select(100);
        //获取多路复用器的事件值selectionKeys并存放在迭代器中
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            //获取后从迭代器中移除
            iterator.remove();
            //是否为连接状态
            if (key.isConnectable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                //是否完成连接
                if (channel.finishConnect()) {
                    try {
                        //根据SelectionKey进行相应的读操作
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int read = channel.read(byteBuffer);
                        if (read > 0) {
                            byteBuffer.flip();
                            byte[] bytes = new byte[byteBuffer.remaining()];
                            byteBuffer.get(bytes);
                            String string = new String(bytes, "UTF-8");
                            System.out.println("客户端接收消息: " + string);
                        }
                    } finally {
                        channel.close();
                    }
                }
            }
        }
    }

    static class Service {
        private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor
                (3,
                        5,
                        60,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(3),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.CallerRunsPolicy());

        public static void main(String[] args) {
            //IO多路复用
            POOL_EXECUTOR.execute(() -> {
                try {
                    //多路复用器selector会对注册在其上面的channel进行轮询，当某个channel发生读写操作时
                    //就会处于相应的就绪状态，通过SelectionKey的值，进行io操作
                    Selector selector;
                    //打开多路复用器
                    selector = Selector.open();
                    //打开socketChannel
                    ServerSocketChannel socketChannel = ServerSocketChannel.open();
                    //管道socketChannel绑定地址端口
                    socketChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(), SERVER_PORT));
                    //非阻塞
                    socketChannel.configureBlocking(false);
                    //将管道socketChannel在多路复用器selector上注册为接收操作
                    socketChannel.register(selector, SelectionKey.OP_ACCEPT);
                    while (true) {
                        // 阻塞等待就绪的Channel，最大阻塞时间
                        selector.select(100);
                        //获取多路复用器的事件值selectionKeys并存放在迭代器中
                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            //获取后从迭代器中移除
                            iterator.remove();
                            SocketChannel channel = null;
                            try {
                                channel = ((ServerSocketChannel) key.channel()).accept();
                                //根据SelectionKey进行相应的写操作,这里先不进行读（以后再写读的代码）
                                channel.write(Charset.defaultCharset().encode("hello yuan"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                channel.close();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
