package com.yuan.middleware.jdk.structure.hashmap;

import java.util.Iterator;

public interface Map<K, V> {
    /**
     * 存入键值对
     *
     * @param key   key值
     * @param value value
     * @return 被覆盖的的value值
     */
    V put(K key, V value);

    /**
     * 移除键值对
     *
     * @param key key值
     * @return 被删除的value的值
     */
    V remove(K key);

    /**
     * 获取key对应的value值
     *
     * @param key key值
     * @return 对应的value值
     */
    V get(K key);

    /**
     * 获取key对应的节点
     *
     * @param key
     * @param index key在内部数组中对应的下标
     * @return
     */
    EntryNode<K, V> getNode(int index, K key);

    /**
     * 是否包含当前key值
     *
     * @param key key值
     * @return true：包含 false：不包含
     */
    boolean containsKey(K key);

    /**
     * 是否包含当前value值
     *
     * @param value value值
     * @return true：包含 false：不包含
     */
    boolean containsValue(V value);

    /**
     * 获得当前map存储的键值对数量
     *
     * @return 键值对数量
     */
    int size();

    /**
     * 当前map是否为空
     *
     * @return true：为空 false：不为空
     */
    boolean isEmpty();

    /**
     * 清空当前map
     */
    void clear();

    /**
     * 获得迭代器
     *
     * @return 迭代器对象
     */
    Iterator<EntryNode<K, V>> iterator();

    /**
     * 键值对节点 内部类
     */
    class EntryNode<K, V> {
        final K key;
        V value;
        EntryNode<K, V> next;

        EntryNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        boolean keyIsEquals(K key) {
            if (this.key == key) {
                return true;
            }

            if (key == null) {
                //:::如果走到这步，this.key不等于null，不匹配
                return false;
            } else {
                return key.equals(this.key);
            }
        }

        EntryNode<K, V> getNext() {
            return next;
        }

        void setNext(EntryNode<K, V> next) {
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
