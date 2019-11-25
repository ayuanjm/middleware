package com.yuan.middleware.structure.list;


import java.util.Iterator;

/**
 * 优点：LinkedList 基于链表的数据结构，地址是任意的，所以在开辟内存空间的时候不需要等一个连续的地址。
 * 对于新增和删除操作 add 和 remove ，LinedList 比较占优势。LinkedList 适用于要头尾操作或插入指定位置的场景。
 * 缺点：因为 LinkedList 要移动指针，所以查询操作性能比较低。
 * <p>
 * 链表内部维护着首、尾哨兵两个内部节点(双端)，作为链表的第一个和最后一个节点，
 * 新插入的节点总是处于这两个哨兵节点之间，用户也无法感知哨兵节点的存在。
 * 哨兵节点并不用于保存数据，其存在的主要目的是为了简化边界条件的逻辑。
 * <p>
 * ###jdk中的linkList首尾节点保存数据，实现方式和这里的实现略有不同。
 *
 * @author yuan
 */
public class LinkedList<E> implements List<E> {
    /**
     * 链表 头部哨兵节点
     */
    private Node<E> first;
    /**
     * 链表 尾部哨兵节点
     */
    private Node<E> last;
    /**
     * 链表 逻辑大小
     */
    private int size;

    public LinkedList() {
        this.first = new Node<>();
        this.last = new Node<>();

        //:::将首尾哨兵节点 进行连接
        this.first.right = this.last;
        this.last.left = this.first;

        //:::初始化size
        this.size = 0;
    }

    /**
     * 链表内部节点类
     */
    private static class Node<E> {
        /**
         * 左边关联的节点引用
         */
        Node<E> left;

        /**
         * 右边关联的节点引用
         */
        Node<E> right;

        /**
         * 节点存储的数据
         */
        E data;

        //===================================内部节点 构造函数==================================
        private Node() {
        }

        private Node(E data) {
            this.data = data;
        }

        /**
         * 将一个节点作为"当前节点"的"左节点" 插入链表
         *
         * @param node 需要插入的节点
         */
        private void linkAsLeft(Node<E> node) {
            //:::先设置新增节点的 左右节点
            node.left = this.left;
            node.right = this;

            //:::将新增节点插入 当前节点和当前节点的左节点之间
            this.left.right = node;
            this.left = node;
        }

        /**
         * 将一个节点作为"当前节点"的"右节点" 插入链表
         *
         * @param node 需要插入的节点
         */
        private void linkAsRight(Node<E> node) {
            //:::先设置新增节点的 左右节点
            node.left = this;
            node.right = this.right;

            //:::将新增节点插入 当前节点和当前节点的左节点之间
            node.right.left = node;
            node.right = node;
        }

        /**
         * 将"当前节点"移出链表
         */
        private void unlinkSelf() {
            //:::令当前链表的 左节点和右节点建立关联
            this.left.right = this.right;
            //:::令当前链表的 右节点和左节点建立关联
            this.right.left = this.left;

            //:::这样,当前节点就从链表中被移除了(同时,作为私有的内部类,当前节点不会被其它对象引用,很快会被GC回收)
        }
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
     * 因为可以保存null数据，所以需要做非空校验
     *
     * @param object
     * @return
     */
    @Override
    public int indexOf(Object object) {
        //:::当前节点 = 列表头部哨兵
        Node<E> currentNode = this.first;

        //如果不为null
        if (object != null) {
            //从前到后遍历
            for (int i = 0; i < this.size; i++) {
                //将当前节点引用指向当前节点的右节点 currentNode ==>currentNode.right
                //当i = 0时currentNode.right，为第一个真实节点，首尾节点是没有存储数据的。
                currentNode = currentNode.right;
                if (object.equals(currentNode.data)) {
                    //返回下标
                    return i;
                }
            }
        } else {
            //从前到后遍历
            for (int i = 0; i < this.size; i++) {
                //将当前节点引用指向当前节点的右节点 currentNode ==>currentNode.right
                //当i = 0时currentNode.right，为第一个真实节点，首尾节点是没有存储数据的。
                currentNode = currentNode.right;
                //如果为null
                if (currentNode.data == null) {
                    //返回下标
                    return i;
                }
            }
        }
        //遍历列表未找到相等的元素,返回特殊值"-1"代表未找到
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return (indexOf(o) != -1);
    }

    /**
     * 插入时下标越界检查
     *
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        //插入时index可以等于size，相当于在链表最后插入节点
        if (index > this.size) {
            throw new IndexOutOfBoundsException("index error  index=" + index + " size=" + this.size);
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("index error  index=" + index + " size=" + this.size);
        }
    }

    /**
     * 下标越界检查（删除修改查询）
     *
     * @param index
     */
    private void rangeCheck(int index) {
        //删除修改查询时如果index=size会下标越界
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("index error  index=" + index + " size=" + this.size);
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("index error  index=" + index + " size=" + this.size);
        }
    }

    /**
     * 返回下标对应的 内部节点
     * 因为链表要移动指针，所以查询操作性能比较低。
     * 先需要判断指定下标位于列表位置的大概位置，前半段还是后半段，尽量减少遍历查询的次数。
     *
     * @param index
     * @return
     */
    private Node<E> node(int index) {
        //要求调用该方法前,已经进行了下标越界校验
        //出于效率的考虑,不进行重复校验
        //<<n 乘2^n , >>n 除2^n
        if (index < (this.size >> 1)) {
            //下标位于前半段
            //从头部结点出发,进行遍历(从左到右)
            Node<E> currentNode = this.first.right;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.right;
            }
            return currentNode;
        } else {
            //下标位于后半段
            //从尾部结点出发,进行遍历(从右到左)
            Node<E> currentNode = this.last.left;
            //迭代(size-index-1)次
            for (int i = index; i < this.size - 1; i++) {
                currentNode = currentNode.left;
            }
            return currentNode;
        }
    }

    /**
     * 向链表尾部添加节点
     *
     * @param e
     * @return
     */
    @Override
    public boolean add(E e) {
        //将新的数据 插入到列表末尾 ==> 作为last节点的前一个节点插入
        this.last.linkAsLeft(new Node<>(e));
        //size自增
        this.size++;
        return true;
    }

    /**
     * 向链表指定位置添加节点
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {
        //插入时,下标越界检查
        rangeCheckForAdd(index);
        //获取指定下标对应的节点
        Node<E> currentNode = node(index);
        //将新的数据节点 作为目标节点的前一个节点插入
        currentNode.linkAsLeft(new Node<>(element));
        //size自增
        this.size++;
    }

    /**
     * 删除节点,只会删除从前往后遍历的第一个相等的节点
     *
     * @param object
     * @return
     */
    @Override
    public boolean remove(Object object) {
        //:::当前节点 = 列表头部哨兵
        Node<E> currentNode = this.first;

        //如果不为null
        if (object != null) {
            //从前到后遍历
            for (int i = 0; i < this.size; i++) {
                //将当前节点引用指向当前节点的右节点 currentNode ==>currentNode.right
                //当i = 0时currentNode.right，为第一个真实节点，首尾节点是没有存储数据的。
                currentNode = currentNode.right;
                if (object.equals(currentNode.data)) {
                    currentNode.unlinkSelf();
                    return true;
                }
            }
        } else {
            //从前到后遍历
            for (int i = 0; i < this.size; i++) {
                //将当前节点引用指向当前节点的右节点 currentNode ==>currentNode.right
                //当i = 0时currentNode.right，为第一个真实节点，首尾节点是没有存储数据的。
                currentNode = currentNode.right;
                //如果为null
                if (currentNode.data == null) {
                    currentNode.unlinkSelf();
                    return true;
                }
            }
        }
        //遍历列表未找到相等的元素,返回特殊值"-1"代表未找到
        return false;
    }

    /**
     * 删除指定下标节点
     *
     * @param index
     * @return
     */
    @Override
    public E remove(int index) {
        //下标越界检查
        rangeCheck(index);

        //获得下标对应的 Node
        Node<E> targetNode = node(index);

        //将目标节点从链表中移除
        targetNode.unlinkSelf();
        //size自减
        this.size--;

        //返回被移除的节点data值
        return targetNode.data;
    }

    @Override
    public E set(int index, E element) {
        //下标越界检查
        rangeCheck(index);
        //获得下标对应的 Node
        Node<E> targetNode = node(index);
        //先暂存要被替换的"data"
        E oldData = targetNode.data;
        //将当前下标对应的"data"替换为"e"
        targetNode.data = element;
        //返回被替换的data
        return oldData;
    }

    @Override
    public E get(int index) {
        //下标越界检查
        rangeCheck(index);
        return node(index).data;
    }

    @Override
    public void clear() {
        /**
         * <p>
         * 首尾节点互相指向，这样也可以但是不利于GC
         * this.first.right = this.last;
         * this.last.left = this.first;
         * </p>
         * 清除节点之间的所有链接是“不必要的”，但如果丢弃的节点驻留在内存
         * 即使有一个可访问的迭代器，也有一个以上的生成可以释放内存
         */
        for (Node<E> node = this.first; node.right != this.last; ) {
            Node<E> next = node.right;
            node.left = null;
            node.right = null;
            node.data = null;
            node = next;
        }
        this.size = 0;
    }

    @Override
    public String toString() {
        Iterator<E> iterator = this.iterator();
        if (!iterator.hasNext()) {
            return "[]";
        }
        StringBuffer sb = new StringBuffer("[");
        while (iterator.hasNext()) {
            //获得迭代的当前元素,每次调用iterator.next()方法都会获取下一个节点
            E data = iterator.next();
            if (data != this.last) {
                //不是最后一个元素 使用", "分割，拼接到后面
                sb = sb.append(data).append(",");
            } else {
                //是最后一个元素，用"]"收尾
                sb = sb.append(data).append("]");
            }
        }
        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * 链表迭代器实现
     */
    private class Itr implements Iterator<E> {
        /**
         * 当前迭代器光标位置
         * 初始化指向 第一个节点(不是首部节点)
         */
        private Node<E> currentNode = LinkedList.this.first.right;
        /**
         * 最近一次迭代返回的数据
         */
        private Node<E> lastReturned;

        @Override
        public boolean hasNext() {
            //判断当前节点的下一个节点 是否是 尾部哨兵节点
            return currentNode != LinkedList.this.last;
        }

        @Override
        public E next() {
            //设置最近一次返回的节点
            this.lastReturned = currentNode;

            //指向当前节点的下一个节点
            this.currentNode = this.currentNode.right;

            //返回当前节点的data
            return this.lastReturned.data;
        }

        @Override
        public void remove() {
            if (this.lastReturned == null) {
                throw new RuntimeException("迭代器状态异常: 可能在一次迭代中进行了多次remove操作");
            }

            //当前光标指向的节点要被删除,先暂存引用
            Node<E> nodeWillRemove = this.lastReturned;

            //移除操作需要将最近一次返回设置为null，防止多次remove
            this.lastReturned = null;

            //将节点从链表中移除
            nodeWillRemove.unlinkSelf();
        }
    }
}
