package com.yuan.middleware.jdk.structure.deque;

import java.util.Iterator;

/**
 * 基于数组的 双端队列
 * 本文实现的是逆时针数组队列，从左到右，数组的方向和head，tail的出队入队下标变化有关
 * 头指针始终指向队列头元素，而尾指针始终指向队列尾元素的下一个位置
 *
 * @author yuan
 */
public class ArrayDeque<E> implements Deque<E> {
    /**
     * 内部封装的数组
     */
    private Object[] elements;

    /**
     * 队列默认的容量大小
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * 扩容翻倍的基数
     */
    private static final int EXPAND_BASE = 2;

    /**
     * 队列头部下标
     */
    private int head;

    /**
     * 队列尾部下标
     */
    private int tail;

    /**
     * 默认构造方法
     */
    public ArrayDeque() {
        //:::设置数组大小为默认
        this.elements = new Object[DEFAULT_CAPACITY];

        //:::初始化队列 头部,尾部下标
        this.head = 0;
        this.tail = 0;
    }

    /**
     * 由于是循环队列移动指针可能会导致下标越界
     * 获得移动后的指针位置
     *
     * @param logicIndex
     * @return
     */
    private int getMod(int logicIndex) {
        if (logicIndex < 0) {
            //(logicIndex + elements.length) % elements.length;
            return logicIndex + elements.length;
        } else if (logicIndex >= elements.length) {
            //(logicIndex - elements.length) % elements.length;
            return logicIndex - elements.length;
        }
        return logicIndex;
    }

    /**
     * 内部数组扩容
     * 要复制的数组(源数组),复制源数组的起始位置,目标数组,目标数组的下标位置,要复制的长度
     * System.arraycopy(tempArray, 0, elements, 0, tempArray.length);
     */
    private void expand() {
        //内部数组 扩容两倍
        int elementsLength = elements.length;
        Object[] newElements = new Object[elementsLength * EXPAND_BASE];
        //将"head -> 数组尾部"的元素 复制在新数组的前面
        System.arraycopy(elements, head, newElements, 0, elements.length - head);
        //将"0 -> head"的元素 复制在新数组的后面
        System.arraycopy(elements, 0, newElements, elements.length - head, tail);
        //初始化head,tail下标(head始终指向头元素，tail指向尾元素的下一个位置)
        this.head = 0;
        this.tail = this.elements.length;

        //内部数组指向 新扩容的数组
        this.elements = newElements;
    }

    public static void main(String[] args) {
        Object[] old = {1, 2, 3, 4, 5};
        //头部位置为2，old[2]==3;
        int head = 2;
        int tail = 2;
        Object[] newArray = new Object[10];
        System.arraycopy(old, head, newArray, 0, old.length - head);
        System.arraycopy(old, 0, newArray, old.length - head, tail);
        for (Object o : newArray) {
            System.out.println(o);
        }
    }

    /**
     * 使用addTail，removeTail时 相当于栈
     * 使用addTail，removeHead时 相当于队列
     * <----------
     * |                 |
     * | 0   1 2 3 4   5 |
     * head ----------> tail
     */
    @Override
    public void addHead(E e) {
        //由于是逆时针数组队列，头部插入元素，head下标左移一位
        this.head = getMod(this.head - 1);
        this.elements[this.head] = e;
        //队列为空时 head == tail,当插入元素后如果head == tail 说明内部数组容量已满，需要扩容。
        if (this.head == this.tail) {
            //内部数组扩容
            expand();
        }
    }

    @Override
    public void addTail(E e) {
        //存放新插入的元素，原来的tail位置没有元素，指向的是尾部的下一个位置
        this.elements[this.tail] = e;
        //由于是逆时针数组队列，尾部插入元素，tail下标右移一位
        this.tail = getMod(this.tail + 1);
        //队列为空时 head == tail,当插入元素后如果head == tail 说明内部数组容量已满，需要扩容。
        if (this.head == this.tail) {
            //内部数组扩容
            expand();
        }
    }

    @Override
    public E removeHead() {
        //暂存需要被删除的数据
        E removeValue = (E) this.elements[this.head];
        //将当前头部元素引用释放，引用传递
        this.elements[this.head] = null;
        //由于是逆时针数组队列，头部插入元素，head下标右移一位
        this.head = getMod(this.head + 1);
        return removeValue;
    }

    @Override
    public E removeTail() {
        //由于是逆时针数组队列，尾部删除元素，tail下标左移一位
        this.tail = getMod(this.tail - 1);
        //暂存需要被删除的数据
        E removeValue = (E) this.elements[this.tail];
        //将当前头部元素引用释放，引用传递
        this.elements[this.tail] = null;
        return removeValue;
    }

    @Override
    public E peekHead() {
        return (E) this.elements[this.head];
    }

    @Override
    public E peekTail() {
        //获得尾部元素下标(左移一位)
        int lastIndex = getMod(this.tail - 1);
        return (E) this.elements[lastIndex];
    }

    @Override
    public int size() {
        //当tail==head时只会为空，不会是队满，因为每次进行增加元素都会判断队满，是否扩容。
        return getMod(this.tail - this.head);
    }

    @Override
    public boolean isEmpty() {
        //当且仅当 头尾下标相等时 队列为空
        return (head == tail);
    }

    @Override
    public void clear() {
        this.head = 0;
        this.tail = 0;
        for (int i = 0; i < this.elements.length; i++) {
            this.elements[i] = null;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * 双端队列 迭代器实现
     */
    private class Itr implements Iterator<E> {
        /**
         * 当前迭代下标 = head
         * 代表遍历从头部开始
         */
        private int currentIndex = ArrayDeque.this.head;

        /**
         * 头指针始终指向队列头元素，而尾指针始终指向队列尾元素的下一个位置
         * 目标终点下标 = tail
         * 代表遍历至尾部结束
         */
        private int targetIndex = ArrayDeque.this.tail;

        /**
         * 上一次返回的位置下标
         */
        private int lastRet = -1;

        @Override
        public boolean hasNext() {
            //由于targetIndex=tail，tail又始终指向队列尾部元素的下一个位置，所以当currentIndex=targetIndex时说明已经遍历完了
            return currentIndex != targetIndex;
        }

        @Override
        public E next() {
            //暂存需要返回的元素
            E value = (E) ArrayDeque.this.elements[currentIndex];
            //上一次返回的位置下标=当前迭代下标，用作remove方法删除校验
            lastRet = currentIndex;
            //当前迭代下标 向右移动一位(需要取模)
            currentIndex = getMod(currentIndex + 1);
            return value;
        }

        @Override
        public void remove() {
            if (lastRet == -1) {
                throw new RuntimeException("迭代器状态异常: 可能在一次迭代中进行了多次remove操作");
            }


            //:::删除当前迭代下标的元素
            boolean deleteFromTail = delete(this.currentIndex);
            //:::如果从尾部进行收缩
            if (deleteFromTail) {
                //:::当前迭代下标前移一位
                this.currentIndex = getMod(this.currentIndex - 1);
            }

            //:::为了防止用户在一次迭代(next调用)中多次使用remove方法，将lastReturned设置为-1
            this.lastRet = -1;
        }

    }

    /**
     * 删除队列内部数组特定下标处的元素
     *
     * @param currentIndex 指定的下标
     * @return true 被删除的元素靠近尾部
     * false 被删除的元素靠近头部
     */
    private boolean delete(int currentIndex) {
        Object[] elements = ArrayDeque.this.elements;
        int head = ArrayDeque.this.head;
        int tail = ArrayDeque.this.tail;

        //:::当前下标 之前的元素个数
        int beforeCount = getMod(currentIndex - head);
        //:::当前下标 之后的元素个数
        int afterCount = getMod(tail - currentIndex);

        //:::判断哪一端的元素个数较少
        if (beforeCount < afterCount) {
            //:::距离头部元素较少，整体移动前半段元素

            //:::判断头部下标 是否小于 当前下标
            if (head < currentIndex) {
                //:::小于，正常状态  仅需要复制一批数据

                //:::将当前数组从"头部下标"开始，整体向右平移一位，移动的元素个数为"当前下标 之前的元素个数"
                System.arraycopy(elements, head, elements, head + 1, beforeCount);
            }
            //TODO 没有看懂 感觉这种情况不可能出现
            else {
                //:::不小于，说明存在溢出环  需要复制两批数据

                //:::将数组从"0下标处"的元素整体向右平移一位，移动的元素个数为"从0到当前下标之间的元素个数"
                System.arraycopy(elements, 0, elements, 1, currentIndex);
                //:::将数组最尾部的数据设置到头部，防止被覆盖
                elements[0] = elements[(elements.length - 1)];
                //:::将数组尾部的数据整体向右平移一位
                System.arraycopy(elements, head, elements, head + 1, (elements.length - head - 1));
            }
            //:::释放被删除元素的引用
            elements[currentIndex] = null;
            //:::头部下标 向右移动一位
            ArrayDeque.this.head = getMod(ArrayDeque.this.head + 1);

            //:::没有删除尾部元素 返回false
            return false;
        } else {
            //:::距离尾部元素较少，整体移动后半段元素

            //:::判断尾部下标 是否小于 当前下标
            if (currentIndex < tail) {
                //:::小于，正常状态  仅需要复制一批数据

                //:::将当前数组从"当前"开始，整体向左平移一位，移动的元素个数为"当前下标 之后的元素个数"
                System.arraycopy(elements, currentIndex + 1, elements, currentIndex, afterCount);
            } else {
                //:::不小于，说明存在溢出环  需要复制两批数据

                //:::将数组从"当前下标处"的元素整体向左平移一位，移动的元素个数为"从当前下标到数组末尾的元素个数-1 ps：因为要去除掉被删除的元素"
                System.arraycopy(elements, currentIndex + 1, elements, currentIndex, (elements.length - currentIndex - 1));
                //:::将数组头部的元素设置到末尾
                elements[elements.length - 1] = elements[0];
                //:::将数组头部的数据整体向左平移一位，移动的元素个数为"从0到尾部下标之间的元素个数"
                System.arraycopy(elements, 1, elements, 0, tail);
            }
            //:::尾部下标 向左移动一位
            ArrayDeque.this.tail = getMod(ArrayDeque.this.tail - 1);

            //:::删除了尾部元素 返回true
            return true;
        }
    }
}
