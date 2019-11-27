package com.yuan.middleware.structure.hashmap;

import java.util.Iterator;

/**
 * 哈希表存储的是key-value键值对结构的数据，其基础是一个数组。
 * 由于采用hash算法会出现hash冲突，一个数组下标对应了多个元素。常见的解决hash冲突的方法有：开放地址法、重新哈希法、拉链法等等，我们的哈希表实现采用的是拉链法解决hash冲突。
 * 采用拉链法的哈希表将内部数组的每一个元素视为一个插槽(slot)或者桶(bucket)，并将数据存放在键值对节点(EntryNode)中。EntryNode除了存放key和value，
 * 还维护着一个next节点的引用。为了解决hash冲突，单个插槽内的多个EntryNode构成一个简单的单向链表，插槽指向链表的头部节点，新的数据将会插入当前链表的尾部。
 * key值不同但映射的hash值相同的元素在哈希表的同一个插槽中以链表的形式共存。
 *
 * @author yuan
 */
public class HashMap<K, V> implements Map<K, V> {
    /**
     * 内部数组
     */
    private EntryNode<K, V>[] elements;

    /**
     * 当前哈希表的大小
     */
    private int size;

    /**
     * 负载因子
     */
    private float loadFactor;

    /**
     * 默认的哈希表容量
     */
    private final static int DEFAULT_CAPACITY = 16;

    /**
     * 扩容翻倍的基数
     */
    private final static int REHASH_BASE = 2;

    /**
     * 默认的负载因子
     */
    private final static float DEFAULT_LOAD_FACTOR = 0.75f;

    //========================================构造方法===================================================

    /**
     * 默认构造方法
     */
    @SuppressWarnings("unchecked")
    public HashMap() {
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        elements = new EntryNode[DEFAULT_CAPACITY];
    }

    /**
     * 指定初始容量的构造方法
     *
     * @param capacity 指定的初始容量
     */
    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        elements = new EntryNode[capacity];
    }

    /**
     * 指定初始容量和负载因子的构造方法
     *
     * @param capacity   指定的初始容量
     * @param loadFactor 指定的负载因子
     */
    @SuppressWarnings("unchecked")
    public HashMap(int capacity, int loadFactor) {
        this.size = 0;
        this.loadFactor = loadFactor;
        elements = new EntryNode[capacity];
    }

    /**
     * 通过key的hashCode获得对应的内部数组下标
     *
     * @param key 传入的键值key
     * @return 对应的内部数组下标
     */
    private int getIndex(K key) {
        return getIndex(key, this.elements);
    }

    /**
     * 通过key的hashCode获得对应的内部数组插槽slot下标
     *
     * @param key      传入的键值key
     * @param elements 内部数组
     * @return 对应的内部数组下标
     */
    private int getIndex(K key, EntryNode<K, V>[] elements) {
        if (key == null) {
            //::: null 默认存储在第0个桶内
            return 0;
        } else {
            int hashCode = key.hashCode();
            //:::通过 高位和低位的异或运算，获得最终的hash映射，减少碰撞的几率
            int finalHashCode = hashCode ^ (hashCode >>> 16);
            //jdk的hashMap: newTab[e.hash & (newCap - 1)] = e;  jdk数组扩容翻倍:newCap = oldCap << 1;   阈值 = 数组长度 * 负载因子;数组总是扩容2倍
            return (elements.length - 1) & finalHashCode;
        }
    }

    /**
     * 获得目标节点的前一个节点
     *
     * @param currentNode 当前桶链表头节点
     * @param key         对应的key
     * @return 返回当前桶链表中"匹配key的目标节点"的"前一个节点"
     * 注意：当桶链表中不存在匹配节点时，返回桶链表的最后一个节点,返回最后一个节点是为了将key值放在最后一个节点的下个节点。
     */
    private EntryNode<K, V> getTargetPreviousEntryNode(EntryNode<K, V> currentNode, K key) {
        //:::不匹配
        EntryNode<K, V> nextNode = currentNode.next;
        //:::遍历当前桶后面的所有节点
        while (nextNode != null) {
            //:::如果下一个节点的key匹配
            if (nextNode.keyIsEquals(key)) {
                return currentNode;
            } else {
                //:::不断指向下一个节点
                currentNode = nextNode;
                nextNode = nextNode.next;
            }
        }
        //:::到达了桶链表的末尾，返回最后一个节点
        return currentNode;
    }

    @Override
    public V put(K key, V value) {
        // 判断是否需要扩容
        if (needReHash()) {
            reHash();
        }
        //先查找key值对应的数组下标
        int index = getIndex(key);
        //获得数组下标链表的第一个节点
        EntryNode<K, V> firstEntryNode = elements[index];
        //如果当前下标链表不存在任何节点
        if (firstEntryNode == null) {
            //将当前key,value创建为第一个节点
            elements[index] = new EntryNode<>(key, value);
            //map的大小加一,size指的是实际有多少key，不是内部数组的大小
            size++;
            //新添加的key不返回value，只有key已经存在返回旧的value
            return null;
        }
        //获得目标节点的上一个节点
        EntryNode<K, V> previousEntryNode = getTargetPreviousEntryNode(firstEntryNode, key);
        //如果上一个节点的下节点不为null说明key已经存在了
        if (previousEntryNode.next != null) {
            EntryNode<K, V> oldEntryNode = previousEntryNode.next;
            V oldValue = oldEntryNode.value;
            oldEntryNode.value = value;
            //返回旧的value
            return oldValue;
        } else {
            //在桶链表的末尾 新增一个节点
            previousEntryNode.next = new EntryNode<>(key, value);
            //创建了新节点，size加1
            size++;
            return null;
        }
    }

    @Override
    public V remove(K key) {
        //先查找key值对应的数组下标
        int index = getIndex(key);
        //获得数组下标链表的第一个节点
        EntryNode<K, V> firstEntryNode = elements[index];
        //第一个节点为空key值不存在
        if (firstEntryNode == null) {
            return null;
        }
        //第一个节点的key是否等于key
        if (firstEntryNode.keyIsEquals(key)) {
            if (firstEntryNode.next == null) {
                //第一个节点就是key并且该链表只有一个节点，直接将该位置的数组元素=null
                this.elements[index] = null;
                //移除了一个节点 size减一
                this.size--;
                //返回移除的value
                return firstEntryNode.value;
            } else {
                //将第一个节点移除，链表首部节点指向原来第一个节点的后一个节点（可以将这一步与只有一个节点的情况合并）
                this.elements[index] = firstEntryNode.next;
                //移除了一个节点 size减一
                this.size--;
                //返回移除的value
                return firstEntryNode.value;
            }
        } else {
            //第一个节点不是key,获取key值的前一个节点
            EntryNode<K, V> previousEntryNode = getTargetPreviousEntryNode(firstEntryNode, key);
            //获得匹配的目标节点
            EntryNode<K, V> targetEntryNode = previousEntryNode.next;
            //如果目标节点为空，说明key并不存在于哈希表中
            if (targetEntryNode == null) {
                return null;
            } else {
                //将"前一个节点的next" 指向 "目标节点的next" ---> 相当于将目标节点从桶链表移除
                previousEntryNode.next = targetEntryNode.next;
                //移除了一个节点 size减一
                this.size--;
                return targetEntryNode.value;
            }
        }
    }

    /**
     * 要找到某个key在map中的位置，先通过key计算hash，通过hash计算在内部数组中对应的下标，通过下标找到链表的头节点，
     * 通过头节点遍历找到key对应的节点。
     * 可以使用getNode方法得到value
     *
     * @param key key值
     * @return
     */
    @Override
    public V get(K key) {
        //通过key的hashCode获得对应的内部数组下标
        int index = getIndex(key);
        //获得数组下标链表的第一个节点
        EntryNode<K, V> firstEntryNode = elements[index];
        //第一个节点为空key值不存在
        if (firstEntryNode == null) {
            return null;
        }
        if (firstEntryNode.keyIsEquals(key)) {
            //当前第一个节点的key与之匹配
            return firstEntryNode.value;
        } else {
            //获得匹配的目标节点的前一个节点
            EntryNode<K, V> targetPreviousNode = getTargetPreviousEntryNode(firstEntryNode, key);
            //获得匹配的目标节点
            EntryNode<K, V> targetNode = targetPreviousNode.next;
            if (targetNode != null) {
                return targetNode.value;
            } else {
                //如果目标节点为空，说明key并不存在于哈希表中
                return null;
            }
        }
    }

    /**
     * 获取key对应的节点
     *
     * @param index getIndex(key) key在内部数组中对应的下标
     * @param key
     * @return
     */
    @Override
    public EntryNode<K, V> getNode(int index, K key) {
        //获得数组下标链表的第一个节点
        EntryNode<K, V> firstEntryNode = elements[index];
        //第一个节点为空key值不存在
        if (firstEntryNode == null) {
            return null;
        }
        if (firstEntryNode.keyIsEquals(key)) {
            //当前第一个节点的key与之匹配
            return firstEntryNode;
        } else {
            //获得匹配的目标节点的前一个节点
            EntryNode<K, V> targetPreviousNode = getTargetPreviousEntryNode(firstEntryNode, key);
            //获得匹配的目标节点
            EntryNode<K, V> targetNode = targetPreviousNode.next;
            if (targetNode != null) {
                //返回目标节点
                return targetNode;
            } else {
                //如果目标节点为空，说明key并不存在于哈希表中
                return null;
            }
        }
    }

    /**
     * 哈希表扩容
     */
    private void reHash() {
        //:::扩容两倍
        EntryNode<K, V>[] newElements = new EntryNode[this.elements.length * REHASH_BASE];

        //:::遍历所有的插槽
        for (int i = 0; i < this.elements.length; i++) {
            //:::为单个插槽内的元素 rehash
            reHashSlot(i, newElements);
        }

        //:::内部数组 ---> 扩容之后的新数组
        this.elements = newElements;
    }

    /**
     * 单个插槽内的数据进行rehash
     * 当hash冲突比较频繁时，查询效率急剧降低。jdk在1.8版本的哈希表实现(java.util.HashMap)中，对这一场景进行了优化。
     * 当内部桶链表的节点个数超过一定数量(默认为8)时，会将插槽中的桶链表转换成一个红黑树(查询效率为O(logN))。
     *
     * @param index       内部数组下标
     * @param newElements 内部数组
     */
    private void reHashSlot(int index, EntryNode<K, V>[] newElements) {
        //:::获得当前插槽第一个元素
        EntryNode<K, V> currentEntryNode = this.elements[index];
        if (currentEntryNode == null) {
            //:::当前插槽为空，直接返回
            return;
        }

        //:::低位桶链表 头部节点、尾部节点
        EntryNode<K, V> lowListHead = null;
        // lowListTail 只是表示它指向的是尾部节点，当它指向其它地方时也不影响链表内存，
        // 尾部节点内存还是没有改变，改变的只是lowListTail指向对应堆内存的位置。lowListTail只是为了方便操作。
        EntryNode<K, V> lowListTail = null;
        //:::高位桶链表 头部节点、尾部节点
        EntryNode<K, V> highListHead = null;
        EntryNode<K, V> highListTail = null;
        while (currentEntryNode != null) {
            //:::获得当前节点 在新数组中映射的插槽下标
            int entryNodeIndex = getIndex(currentEntryNode.key, newElements);
            //:::是否和当前插槽下标相等
            if (entryNodeIndex == index) {
                //:::和当前插槽下标相等
                if (lowListHead == null) {
                    //:::初始化低位链表
                    lowListHead = currentEntryNode;
                    lowListTail = currentEntryNode;
                } else {
                    //:::在低位链表尾部拓展新的节点，将尾节点的下一个节点引用指向当前节点。操作的是堆内存中的值，真正的改变了堆内存。
                    lowListTail.next = currentEntryNode;
                    // 将尾节点指向新插入的节点 lowListTail = currentEntryNode，只是改变了lowListTail的指向，
                    // 原来的lowListTail指向的堆内存还在原来的位置（currentEntryNode的前节点引用),每个节点的next指针一直存在。
                    // lowListTail的前节点的next引用一直存在，forwardNode.next = lowListTail,虽然lowListTail的引用改变了，
                    // 但是forwardNode.next指向的堆内存没有改变。lowListTail.next（是属性）的改变会导致堆内存的改变而lowListTail引用的改变不会。lowListTail.next相当于堆内存的属性。
                    // 改变lowListTail.next 和 lowListTail 相当于改变属性值和改变整个对象的引用的区别。
                    lowListTail = lowListTail.next;
                }
            } else {
                //:::和当前插槽下标不相等
                if (highListHead == null) {
                    //:::初始化高位链表
                    highListHead = currentEntryNode;
                    highListTail = currentEntryNode;
                } else {
                    //:::在高位链表尾部拓展新的节点
                    highListTail.next = currentEntryNode;
                    highListTail = highListTail.next;
                }
            }
            //:::指向当前插槽的下一个节点
            currentEntryNode = currentEntryNode.next;
        }

        //:::新扩容elements(index)插槽 存放lowList
        newElements[index] = lowListHead;
        //:::lowList末尾截断
        if (lowListTail != null) {
            lowListTail.next = null;
        }

        //:::新扩容elements(index + this.elements.length)插槽 存放highList
        newElements[index + this.elements.length] = highListHead;
        //:::highList末尾截断
        if (highListTail != null) {
            highListTail.next = null;
        }
    }

    /**
     * 判断是否需要 扩容
     * jdk判断扩容 ++size > threshold，实际长度大于阈值扩容，阈值 = 数组长度 * 负载因子
     * <p>
     * 当插入数据时发现哈希表过于拥挤，超过了负载因子指定的值时，会触发一次rehash扩容操作。
     * 哈希表在查询数据时通过直接计算数据hash值对应的插槽，迅速获取到key值对应的数据，进行非常高效的数据查询。
     * 但依然存在一个问题：虽然设计良好的hash函数可以尽可能的降低hash冲突的概率，但hash冲突还是不可避免的。当发生频繁的哈希冲突时，对应的插槽内可能会存放较多的元素，
     * 导致插槽内的链表数据过多。而链表的查询效率是非常低的，在极端情况下，甚至会出现所有元素都映射存放在同一个插槽内，此时的哈希表退化成了一个链表，查询效率急剧降低。
     * 一般的，哈希表存储的数据量一定时，内部数组的大小和数组插槽指向的链表长度成反比。换句话说，总数据量一定，内部数组的容量越大(插槽越多)，平均下来桶链表的长度也就越小，查询效率越高。
     * 同等数据量下，哈希表内部数组容量越大，查询效率越高，但同时空间占用也越高，这本质上是一个空间换时间的取舍。
     * 哈希表允许用户在初始化时指定负载因子(loadFactor)：负载因子代表着存储的总数据量和内部数组大小的比值。插入新数据时，
     * 判断哈希表当前的存储量和内部数组的比值是否超过了负载因子。当比值超过了负载因子时，哈希表认为内部过于拥挤，查询效率太低，会触发一次扩容的rehash操作。
     * rehash会对内部数组扩容，将存储的元素重新进行hash映射，使得哈希表始终保持一个合适的查询效率。
     * <p>
     * 通过指定自定义的负载因子，用户可以控制哈希表在空间和时间上取舍的程度，使哈希表能更有效地适应用户的使用场景。
     * 指定的负载因子越大，哈希表越拥挤(负载高，紧凑)，查询效率越低，空间效率越高。
     * 指定的负载因子越小，哈希表越稀疏(负载小，松散)，查询效率越高，空间效率越低。
     */
    private boolean needReHash() {
        return ((this.size / this.elements.length) > this.loadFactor);
    }

    @Override
    public boolean containsKey(K key) {
        return (getNode(getIndex(key), key) != null);
    }

    @Override
    public boolean containsValue(V value) {
        //循环遍历内部数组
        for (int i = 0; i < this.elements.length; i++) {
            EntryNode<K, V> targetEntryNode = this.elements[i];
            //遍历链表
            while (targetEntryNode != null) {
                if (targetEntryNode.value == value) {
                    return true;
                }
                //将目标节点指向下一个节点，遍历链表
                targetEntryNode = targetEntryNode.next;
            }
        }
        //没有找到匹配的value
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }

    @Override
    public void clear() {
        //:::遍历内部数组，将所有桶链表全部清空
        for (int i = 0; i < this.elements.length; i++) {
            this.elements[i] = null;
        }

        //:::size设置为0
        this.size = 0;
    }

    @Override
    public Iterator<EntryNode<K, V>> iterator() {
        return new Itr();
    }

    @Override
    public String toString() {
        Iterator<EntryNode<K, V>> iterator = this.iterator();

        //:::空容器
        if (!iterator.hasNext()) {
            return "[]";
        }

        //:::容器起始使用"["
        StringBuilder s = new StringBuilder("[");

        //:::反复迭代
        while (true) {
            //:::获得迭代的当前元素
            EntryNode<K, V> data = iterator.next();

            //:::判断当前元素是否是最后一个元素
            if (!iterator.hasNext()) {
                //:::是最后一个元素，用"]"收尾
                s.append(data).append("]");
                //:::返回 拼接完毕的字符串
                return s.toString();
            } else {
                //:::不是最后一个元素
                //:::使用", "分割，拼接到后面
                s.append(data).append(", ");
            }
        }
    }

    /**
     * 哈希表 迭代器实现
     */
    private class Itr implements Iterator<EntryNode<K, V>> {
        /**
         * 迭代器 当前节点
         */
        private EntryNode<K, V> currentNode;

        /**
         * 迭代器 下一个节点
         */
        private EntryNode<K, V> nextNode;

        /**
         * 迭代器 当前内部数组的下标
         */
        private int currentIndex;

        /**
         * 默认构造方法
         */
        private Itr() {
            //:::如果当前哈希表为空，直接返回
            if (HashMap.this.isEmpty()) {
                return;
            }
            //:::在构造方法中，将迭代器下标移动到第一个有效的节点上

            //:::遍历内部数组，找到第一个不为空的数组插槽slot
            for (int i = 0; i < HashMap.this.elements.length; i++) {
                //:::设置当前index
                this.currentIndex = i;

                EntryNode<K, V> firstEntryNode = HashMap.this.elements[i];
                //:::找到了第一个不为空的插槽slot
                if (firstEntryNode != null) {
                    //:::nextNode = 当前插槽第一个节点
                    this.nextNode = firstEntryNode;

                    //:::构造方法立即结束
                    return;
                }
            }
        }


        @Override
        public boolean hasNext() {
            return this.nextNode != null;
        }

        @Override
        public EntryNode<K, V> next() {
            //将下一个节点赋值给当前节点
            currentNode = nextNode;
            //下一个节点指向自己的下一个节点
            nextNode = nextNode.next;
            //如果下一个节点为null说明当前内部数组下标的元素遍历完，需要再次遍历内部数组查找下一个节点
            if (nextNode == null) {
                //当前下标自增
                currentIndex++;
                for (; currentIndex < HashMap.this.elements.length; currentIndex++) {
                    EntryNode<K, V> firstEntryNode = HashMap.this.elements[currentIndex];
                    //:::找到了第一个不为空的插槽slot
                    if (firstEntryNode != null) {
                        this.nextNode = firstEntryNode;
                        break;
                    }
                }
            }
            return currentNode;
        }


        @Override
        public void remove() {
            if (this.currentNode == null) {
                throw new RuntimeException("迭代器状态异常: 可能在一次迭代中进行了多次remove操作");
            }
            //将其从哈希表中移除
            HashMap.this.remove(currentNode.key);
            //currentNode设置为null，防止反复调用remove方法
            this.currentNode = null;
        }
    }

    public static void main(String[] args) {
        System.out.println();
        java.util.Map<Object, Object> map = new java.util.HashMap(3);
        int i = 0;
        for (; i < 10; ++i) {
            System.out.println(i);
            if (i == 3) {
                break;
            }
            map.put(i, i);
        }
        System.out.println(i);
//        for (map.values())

    }
}
