package com.yuan.middleware.jdk.structure.list;


import lombok.Data;

import java.util.Iterator;

/**
 * 优点：ArrayList 是实现了基于动态数组的数据结构，因为地址连续，一旦数据存储好了，
 * 查询操作效率会比较高（在内存里是连着放的）。
 * 缺点：因为地址连续，ArrayList 要移动数据，所以插入和删除操作效率比较低。
 *
 * @author yuan
 * 数据结构向量实现
 */
@Data
public class ArrayList<E> implements List<E> {

    /**
     * 内部封装的数组
     */
    private Object[] elements;

    /**
     * 线性表默认的容量大小
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * 扩容翻倍的基数
     */
    private static final double EXPAND_BASE = 1.5;

    /**
     * 内部数组的实际大小
     */
    private int capacity;

    /**
     * 当前线性表的实际大小
     */
    private int size;

    /**
     * 默认无参构造方法
     */
    public ArrayList() {
        capacity = DEFAULT_CAPACITY;
        elements = new Object[capacity];
    }

    /**
     * 内部数组初始化大小的构造方法
     *
     * @param initialCapacity 内部数组初始大小
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity < DEFAULT_CAPACITY) {
            capacity = DEFAULT_CAPACITY;
        }
        elements = new Object[capacity];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }

    /**
     * list.add(null);
     * list.get(0).equals(null); list可以添加null元素，所以以list元素.equals也会空指针
     *
     * @param object
     * @return
     */
    @Override
    public int indexOf(Object object) {
        //判断当前参数是否为null
        if (object != null) {
            //参数不为空从前到后依次比较
            for (int i = 0; i < this.size; i++) {
                //判断当前元素是否等于object
                if (object.equals(elements[i])) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < this.size; i++) {
                //判断当前元素是否等于null
                if (elements[i] == null) {
                    return i;
                }
            }
        }
        //遍历列表未找到相等的元素，返回特殊值"-1"代表未找到
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        //返回-1代表不存在，反之存在
        return indexOf(o) != -1;
    }

    /**
     * 下标越界检查
     *
     * 部分增删改查接口会通过下标来进行操作，必须对访问数组的下标进行校验。检查方法为private
     */
    /**
     * 插入时下标越界检查
     *
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        // 如果下标小于0或者大于size抛出异常（等于size时说明是在内部数组末尾加入新值）
        if (index < 0 || index > this.size) {
            throw new RuntimeException("index error  index=" + index + " size=" + this.size);
        }
    }

    /**
     * 下标越界检查（删除修改查询）
     *
     * @param index
     */
    private void rangeCheck(int index) {
        // 如果下标小于0或者大于等于size的值抛出异常
        if (index < 0 || index >= this.size) {
            throw new RuntimeException("index error  index=" + index + " size=" + this.size);
        }
    }

    /**
     * 插入方法（数组末尾插入数据）
     *
     * @param e
     * @return
     */
    @Override
    public boolean add(E e) {
        //插入新数据前进行扩容检查
        expandCheck();
        //在内部数组末尾插入新元素
        elements[size] = e;
        //线性表实际大小加一（非线程安全）
        this.size++;
        return true;
    }

    /**
     * 插入方法（在指定下标插入元素）
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {
        //插入时数组下标越界检查
        rangeCheckForAdd(index);
        //扩容检查
        expandCheck();
        //插入下标位置和之后的元素后移一位
        System.arraycopy(this.elements, index, this.elements, index + 1, this.size - index);
        //在index位置赋值element
        this.elements[index] = element;
        this.size++;
    }

    /**
     * 内部数组扩容检查
     */
    private void expandCheck() {
        //如果当前元素个数 = 当前内部数组容量
        if (this.size == this.capacity) {
            //需要扩容，暂存当前内部数组的引用
            Object[] tempArray = this.elements;
            //当前内部数组扩容一定的倍数
            this.capacity = (int) (this.capacity * EXPAND_BASE);
            //内部数组指向扩容后的新数组
            this.elements = new Object[this.capacity];
            //要复制的数组(源数组),复制源数组的起始位置,目标数组,目标数组的下标位置,要复制的长度
            System.arraycopy(tempArray, 0, elements, 0, tempArray.length);
        }
    }

    /**
     * 删除指定下标的元素
     *
     * @param index
     * @return
     */
    @Override
    public E remove(int index) {
        //数组下标越界检查
        rangeCheck(index);
        //记录删除元素
        E deleteValue = (E) this.elements[index];
        fastRemove(index);
        //返回删除下标元素
        return deleteValue;
    }

    /**
     * 删除指定下标的元素
     *
     * @param index
     */
    private void fastRemove(int index) {
        //将删除下标位置之后的数据整体前移一位
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        //由于数据整体前移了一位，释放列表末尾的失效引用，增加GC效率
        this.elements[this.size - 1] = null;
        //实际大小减1
        this.size--;
    }

    /**
     * 删除元素,只会删除从前往后遍历的第一个相等的元素
     *
     * @param object
     * @return
     */
    @Override
    public boolean remove(Object object) {
        //判断当前参数是否为null
        if (object != null) {
            //参数不为空从前到后依次比较
            for (int i = 0; i < this.size; i++) {
                //判断当前元素是否等于object
                if (object.equals(elements[i])) {
                    //将删除下标位置之后的数据整体前移一位
                    fastRemove(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < this.size; i++) {
                //判断当前元素是否等于null
                if (elements[i] == null) {
                    //将删除下标位置之后的数据整体前移一位
                    fastRemove(i);
                    return true;
                }
            }
        }
        //遍历列表未找到相等的元素,未进行删除 返回false
        return false;
    }

    /**
     * 修改指定下标元素的值
     *
     * @param index
     * @param element
     * @return
     */
    @Override
    public E set(int index, E element) {
        //数组下标越界检查
        rangeCheck(index);
        //记录修改元素原有值
        E oldValue = (E) this.elements[index];
        //将下标index的值设置为element
        this.elements[index] = element;
        //返回旧值
        return oldValue;
    }


    /**
     * 得到指定下标元素
     *
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        //数组下标越界检查
        rangeCheck(index);
        return (E) this.elements[index];
    }

    /**
     * 清空向量内的元素，初始化向量。
     */
    @Override
    public void clear() {
        for (int i = 0; i < this.size; i++) {
            this.elements[i] = null;
        }
        this.size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return this.new ListItr();
    }

    /**
     * 收缩内部数组，使得"内部数组的大小"和"向量逻辑大小"相匹配，提高空间利用率
     */
    public void trimToSize() {
        //如果当前向量逻辑长度小于内部数组的大小
        if (this.size < this.capacity) {
            //创建一个和当前向量逻辑大小相等的新数组
            Object[] newElements = new Object[this.size];

            //将当前旧内部数组的数据复制到新数组中
            //Tips: 这里使用Arrays.copy方法进行复制,效率更高
            //System.arraycopy(this.elements,0,newElements,0,this.size);
            for (int i = 0; i < newElements.length; i++) {
                newElements[i] = this.elements[i];
            }
            //用新数组替换掉之前的老内部数组
            this.elements = newElements;
            //设置当前容量
            this.capacity = this.size;
        }
    }

    /**
     * 重写toString
     *
     * @return
     */
    @Override
    public String toString() {
        //空列表
        if (this.isEmpty()) {
            return "[]";
        }
        //列表开始使用[
        StringBuffer s = new StringBuffer("[");
        //从第一个到倒数第二个元素之间
        for (int i = 0; i < this.size - 1; i++) {
            //使用", "进行分割
            s.append(this.elements[i]).append(", ");
        }
        //最后一个元素使用"]"结尾
        s.append(this.elements[size - 1]).append("]");

        return s.toString();
    }

    /**
     * 向量 迭代器内部类
     * 循环时必须先调用next方法才能调用一次remove方法
     * 需要注意是通过下标找到元素还是通过指针(引用)找到元素
     * 下标：在进行迭代remove时需要返还下标值(一般是减1)，
     * 指针：在进行迭代remove时由于删除是改变引用指向因此不需要做修改，还是指向下一个节点
     * Iterator基本都有两个下标或两个节点，lastRet代表当前返回，current代表下一个元素。
     * next方法总是执行 lastRet = current,current = current.next或(current++)
     */
    private class ListItr implements Iterator<E> {
        /**
         * 迭代器下一个元素 指针下标
         */
        private int nextIndex = 0;
        /**
         * index of last element returned; -1 if no such
         */
        private int lastRet = -1;

        @Override
        public boolean hasNext() {
            //如果"下一个元素指针下标" 小于 "当前线性表长度" ==> 说明迭代还未结束
            return this.nextIndex < ArrayList.this.size;
        }

        @Override
        public E next() {
            //当前元素指针下标 = 下一个元素指针下标
            this.lastRet = nextIndex;
            //下一个元素指针下标自增,指向下一元素
            this.nextIndex++;

            //返回当前元素
            return (E) ArrayList.this.elements[this.lastRet];
        }

        /**
         * 在ArrayList中有个成员变量modCount，继承于AbstractList。
         * 这个成员变量记录着集合的修改次数，也就每次add或者remove它的值都会加1。
         * 会比较modCount和expectedModCount
         */
        @Override
        public void remove() {
            if (this.lastRet < 0) {
                throw new RuntimeException("迭代器状态异常: 可能在一次迭代中进行了多次remove操作");
            }

            //删除当前元素
            ArrayList.this.remove(this.lastRet);
            //由于删除了当前下标元素，数据段整体向前平移一位，因此nextIndex不用自增,在next方法时自增了，现在需要回到自增前的值
            nextIndex = lastRet;
            //为了防止用户在一次迭代(next调用)中多次使用remove方法，将currentIndex设置为-1
            this.lastRet = -1;
        }
    }

}
