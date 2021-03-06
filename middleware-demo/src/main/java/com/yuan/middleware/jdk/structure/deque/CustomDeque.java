package com.yuan.middleware.jdk.structure.deque;

import java.util.Iterator;

/**
 * 双端队列 ADT接口
 */
public interface CustomDeque<E> {

    /**
     * 头部元素插入
     */
    void addHead(E e);

    /**
     * 尾部元素插入
     */
    void addTail(E e);

    /**
     * 头部元素删除
     */
    E removeHead();

    /**
     * 尾部元素删除
     */
    E removeTail();

    /**
     * 窥视头部元素(不删除)
     */
    E peekHead();

    /**
     * 窥视尾部元素(不删除)
     */
    E peekTail();

    /**
     * @return 返回当前队列中元素的个数
     */
    int size();

    /**
     * 判断当前队列是否为空
     *
     * @return 如果当前队列中元素个数为0，返回true；否则，返回false
     */
    boolean isEmpty();

    /**
     * 清除队列中所有元素
     */
    void clear();

    /**
     * 获得迭代器
     */
    Iterator<E> iterator();

}
