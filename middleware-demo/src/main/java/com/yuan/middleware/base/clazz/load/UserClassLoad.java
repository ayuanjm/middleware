package com.yuan.middleware.base.clazz.load;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 自定义类加载器，重写findClass
 * 破坏双亲委派原则，重写loadClass
 * https://www.cnblogs.com/luoyetl/p/10656134.html
 * https://www.jianshu.com/p/09f73af48a98
 * @author yjm
 * @date 2020/3/13 9:07 下午
 */
public class UserClassLoad extends ClassLoader {

//    @Override
//    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        File file = new File("/Users/yuan/Downloads/People.class");
//        try {
//            byte[] bytes = getClassBytes(file);
//            //defineClass方法可以把二进制流字节组成的文件转换为一个java.lang.Class
//            Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
//            return c;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return super.findClass(name);
//    }

    private byte[] getClassBytes(File file) throws Exception {
        // 这里要读入.class的字节，因此要使用字节流
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel wbc = Channels.newChannel(baos);
        ByteBuffer by = ByteBuffer.allocate(1024);

        while (true) {
            int i = fc.read(by);
            if (i == 0 || i == -1) {
                break;
            }
            by.flip();
            wbc.write(by);
            by.clear();
        }
        fis.close();
        return baos.toByteArray();
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        UserClassLoad mcl = new UserClassLoad();
        Class<?> clazz = Class.forName("com.yuan.middleware.Demo", true, mcl);
    }
}
