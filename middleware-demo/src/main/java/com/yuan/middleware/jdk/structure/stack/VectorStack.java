package com.yuan.middleware.jdk.structure.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;

/**
 * @author yuan
 * 向量栈
 */
public class VectorStack<E> implements CustomStack<E> {
    /**
     * 内部向量
     */
    private ArrayList<E> innerArrayList;

    /**
     * 默认构造方法
     */
    public VectorStack() {
        this.innerArrayList = new ArrayList<>();
    }

    /**
     * 构造方法,确定初始化时的内部向量大小
     */
    public VectorStack(int initSize) {
        this.innerArrayList = new ArrayList<>(initSize);
    }

    @Override
    public int size() {
        return innerArrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return innerArrayList.isEmpty();
    }

    @Override
    public void clear() {
        innerArrayList.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return innerArrayList.iterator();
    }

    @Override
    public String toString() {
        return innerArrayList.toString();
    }

    @Override
    public boolean push(E e) {
        //将元素插入向量末尾（入栈）
        innerArrayList.add(e);
        return true;
    }

    @Override
    public E pop() {
        //空异常
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        //移除末尾元素
        return innerArrayList.remove(innerArrayList.size() - 1);
    }

    @Override
    public E peek() {
        //空异常
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        //返回末尾元素
        return innerArrayList.get(innerArrayList.size() - 1);
    }

    public static void main(String[] args) {
        System.out.println(5%2);
    }
}
