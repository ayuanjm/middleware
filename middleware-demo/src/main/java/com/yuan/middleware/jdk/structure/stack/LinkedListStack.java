package com.yuan.middleware.jdk.structure.stack;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 链表为基础实现的 栈结构
 */
public class LinkedListStack<E> implements CustomStack<E> {
    /**
     * 内部链表
     */
    private LinkedList<E> innerLinkedList;

    /**
     * 默认构造方法
     */
    public LinkedListStack() {
        this.innerLinkedList = new LinkedList<>();
    }

    @Override
    public boolean push(E e) {
        innerLinkedList.add(e);
        return false;
    }

    @Override
    public E pop() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        //将链表末尾处元素删除并返回(出栈)
        return innerLinkedList.remove(innerLinkedList.size() - 1);
    }

    @Override
    public E peek() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        //返回链表末尾处元素(窥视)
        return innerLinkedList.getLast();
    }

    @Override
    public int size() {
        return innerLinkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return innerLinkedList.isEmpty();
    }

    @Override
    public void clear() {
        innerLinkedList.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return innerLinkedList.iterator();
    }
}
