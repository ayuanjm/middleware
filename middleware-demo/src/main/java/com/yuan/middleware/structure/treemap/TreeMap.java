package com.yuan.middleware.structure.treemap;

import java.util.Iterator;

/**
 * 二叉搜索树的左子树上结点的值均小于根结点的值；
 * 右子树上结点的值均大于根结点的值；
 * 二叉搜索树的左、右子树也分别为二叉搜索树。
 *
 * @param <K>
 * @param <V>
 */
public class TreeMap<K, V> implements Map<K, V> {
    /**
     * 根节点
     */
    private EntryNode<K, V> root;

    /**
     * 比较器(初始化之后，不能改)
     */
    private final Comparator<? super K> comparator;

    /**
     * 当前二叉树的大小
     */
    private int size;

    /**
     * 默认构造函数
     */
    public TreeMap() {
        this.comparator = null;
    }

    /**
     * 指定了比较器的构造函数
     */
    public TreeMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    /**
     * 获得key对应的目标节点，调用该方法前做map.size判断不为0
     *
     * @param key 对应的key
     * @return 对应的目标节点
     * 返回null代表 目标节点不存在
     */
    private TargetEntryNode<K, V> getTargetEntryNode(K key) {
        int compareResult = 0;
        EntryNode<K, V> parent = null;
        EntryNode<K, V> parentTemp = null;
        EntryNode<K, V> currentNode = this.root;
        //当currentNode不为null时
        while (currentNode != null) {
            parent = currentNode;
            //当前key 和 currentNode.key进行比较
            compareResult = compare(key, currentNode.key);
            if (compareResult > 0) {
                //当前key大于currentNode的key 指向右边节点
                currentNode = parent.right;
            } else if (compareResult < 0) {
                //当前key小于currentNode的key 指向左边节点
                currentNode = parent.left;
            } else {
                //当前key等于currentNode的key 指向currentNode,当currentNode=root时表示当前节点为根节点父节点为null
                return new TargetEntryNode<>(currentNode, parentTemp, RelativePosition.CURRENT);
            }
            parentTemp = parent;
        }

        //没有找到目标节点
        if (compareResult > 0) {
            //:::返回 右孩子 哨兵节点
            return new TargetEntryNode<>(null, parent, RelativePosition.LEFT);
        } else if (compareResult < 0) {
            //:::返回 左孩子 哨兵节点
            return new TargetEntryNode<>(null, parent, RelativePosition.RIGHT);
        } else {
            //说明map.size=0但是调用这个方法之前已经做了非0判断
            throw new RuntimeException("状态异常");
        }
    }

    /**
     * key值进行比较
     * 主要还是看比较的对象有没有重写compareTo或compare方法
     */
    private int compare(K k1, K k2) {
        //迭代器不存在
        if (this.comparator == null) {
            //依赖对象本身的 Comparable，可能会转型失败
            return ((Comparable) k1).compareTo(k2);
        } else {
            //通过迭代器逻辑进行比较
            return this.comparator.compare(k1, k2);
        }
    }

    /**
     * 判断双亲节点和目标节点 相对位置
     * @param parent    双亲节点
     * @param target    目标节点
     * @return          相对位置(左孩子/右孩子)
     */
    private RelativePosition getRelativeByParent(EntryNode<K,V> parent,EntryNode<K,V> target){
        if(parent.left == target){
            return RelativePosition.LEFT;
        }else if(parent.right == target){
            return RelativePosition.RIGHT;
        }else{
            throw new RuntimeException("不是父子节点关系");
        }
    }


    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Iterator<Map.EntryNode<K, V>> iterator() {
        return null;
    }

    /**
     * 二叉搜索树 内部节点
     */
    static class EntryNode<K, V> implements Map.EntryNode<K, V> {
        /**
         * key值
         */
        K key;

        /**
         * value值
         */
        V value;

        /**
         * 左孩子节点
         */
        EntryNode<K, V> left;

        /**
         * 右孩子节点
         */
        EntryNode<K, V> right;

        /**
         * 双亲节点
         */
        EntryNode<K, V> parent;

        EntryNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        EntryNode(K key, V value, EntryNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * 查找目标节点 返回值
     */
    private static class TargetEntryNode<K, V> {
        /**
         * 目标节点
         */
        private EntryNode<K, V> target;

        /**
         * 目标节点的双亲节点
         */
        private EntryNode<K, V> parent;

        /**
         * 相对位置
         */
        private RelativePosition relativePosition;

        TargetEntryNode(EntryNode<K, V> target, EntryNode<K, V> parent, RelativePosition relativePosition) {
            this.target = target;
            this.parent = parent;
            this.relativePosition = relativePosition;
        }
    }
}
