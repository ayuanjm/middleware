package com.yuan.middleware.spring.transaction;

import com.yuan.middleware.spring.dao.PlatformMessageMapper;
import com.yuan.middleware.spring.entity.PlatformMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <p>
 * #标准的JDK 基于接口的代理将起作用 (proxy-target-class: false)
 * 自我注入@Resource PlatformMessageServiceImpl platformMessageService;会报错
 * BeanNotOfRequiredTypeException: Bean named 'platformMessageServiceImpl'
 * is expected to be of type 'com.yuan.middleware.spring.transaction.PlatformMessageServiceImpl'
 * but was actually of type 'com.sun.proxy.$Proxy120'
 * 需要的是PlatformMessageServiceImpl这个类型的bean，但是由于是JDK动态代理$Proxy120是PlatformMessageService类型的代理类
 * 类似于子类引用不能指向父类对象，类型转化异常。 只有父类引用可以指向指向子类对象。
 * 从JVM层看，父类指向子类，可以拿到和父类一样的方法和属性。 子类指向父类，子类独有的属性和方法在父类中不存在，指向报错。
 * <p>
 * # CGLIB代理将起作用 (proxy-target-class: true)
 * 这时可以自我注入，因为代理类代理的就是PlatformMessageServiceImpl而不是接口。
 * <p>
 * JDK因为他产生的代理对象默认继承了一个Proxy,而java是单集成的,所以我们的代理对象不能再去继承,只能去实现一个接口,故而要求目标对象实现一个借口
 * CGLIB则不同,cglib产生的代理对象是直接继承目标对象的
 * JDK利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用invokeHandler来处理。
 * CGLIB利用ASM开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。但是因为采用的是继承，所以该类或方法最好不要声明成final，对于final类或方法，是无法继承的。
 *
 * @author: YuanJiaMin
 * @date: 2021/3/19 9:16 下午
 * @describe:
 */
@Service
public class PlatformMessageServiceImpl implements PlatformMessageService {
    @Resource
    PlatformMessageServiceImpl platformMessageService;
    @Resource
    private PlatformMessageMapper platformMessageMapper;

    @Override
    public Integer updateMessage(PlatformMessage platformMessage) {
        return platformMessageService.extracted(platformMessage);
    }

    @Transactional(rollbackFor = Exception.class)
    public int extracted(PlatformMessage platformMessage) {
        int i = platformMessageMapper.insertSelective(platformMessage);
        int a = 1 / 0;
        return i;
    }
}
