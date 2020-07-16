package com.yuan.middleware.jdk.base.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP通信的服务器端：接收客户端的请求，读取客户端发送的数据，给客户端回写数据
 * 表示服务器的类：
 *      java.net.ServerSocket:此类实现服务器套接字
 *      构造方法：
 *          ServerSocket(int port)创建绑定到特定端口的服务器套接字
 *      服务器端必须明确一件事情，必须知道是哪个客户端请求的服务器
 *      所以可以使用accept方法获取到请求的饿客户端对象Socket
 *      成员方法：
 *          Socket accept() 侦听并接收到此套接字的连接
 *      服务器的实现步骤：
 *          1、创建服务器ServerSocket对象和系统要指定的端口号
 *          2、使用ServerSocket对象中的方法accept，获取到请求的客户端对象Socket
 *          3、使用Socket对象中的方法getInputStream()获取网络字节输入流对象InputStream对象
 *          4、使用网络字节输入流InputStream对象中的方法read，读取客户端发送的数据
 *          5、使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
 *          6、使用网络字节输出流OutputStream对象中的方法write，给客户端回写数据
 *          7、释放资源(Socket、ServerSocket)
 *
 * @author yuanjm
 * @date 2020/5/12 2:53 下午
 */
public class TcpServer {
    public static void main(String[] args) throws IOException {
        // 1、创建服务器ServerSocket对象和系统要指定的端口号
        ServerSocket serverSocket = new ServerSocket(8088);
        while (true) {
            // 2、使用ServerSocket对象中的方法accept，获取到请求的客户端对象Socket
            Socket socket = serverSocket.accept();
            // 3、使用Socket对象中的方法getInputStream()获取网络字节输入流对象InputStream对象
            InputStream inputStream = socket.getInputStream();
            // 4、使用网络字节输入流InputStream对象中的方法read，读取客户端发送的数据
            byte[] bytes = new byte[1024];
            int length;
            // 这里的inputStream是Socket的SocketInputStream读取的是客户端发送过来的数据
            // 如果客户端发送完了数据，没有使用 socket.shutdownOutput方法，回造成read一直阻塞等待客户端继续发送数据，不会返回-1
            // 这里和普通的FileInputStream的read方法不同，FileInputStream的read方法会返回-1，不会阻塞，暂时是这样的现象，不知底层是否是这样。
            while ((length=inputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes,0,length));
            }
            // 5、使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
            OutputStream outputStream = socket.getOutputStream();
            // 6、使用网络字节输出流OutputStream对象中的方法write，给客户端回写数据
            outputStream.write("hello client".getBytes());
            // 7、释放资源(ServerSocket、Socket)
            socket.close();
        }
        //serverSocket.close();
    }
}
