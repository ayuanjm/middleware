package com.yuan.middleware.structure.treemap.binarytree;

import java.util.Iterator;
import java.util.Objects;

/**
 * 二叉搜索树的左子树上结点的值均小于根结点的值，右子树上结点的值均大于根结点的值；
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
        //比较结果
        int compareResult = 0;
        EntryNode<K, V> parent = null;
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
                return new TargetEntryNode<>(currentNode, parent, RelativePosition.CURRENT);
            }
        }

        //没有找到目标节点
        if (compareResult > 0) {
            //:::返回 右孩子 哨兵节点
            return new TargetEntryNode<>(null, parent, RelativePosition.RIGHT);
        } else if (compareResult < 0) {
            //:::返回 左孩子 哨兵节点
            return new TargetEntryNode<>(null, parent, RelativePosition.LEFT);
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
     *
     * @param parent 双亲节点
     * @param target 目标节点
     * @return 相对位置(左孩子 / 右孩子)
     */
    private RelativePosition getRelativeByParent(EntryNode<K, V> parent, EntryNode<K, V> target) {
        if (parent.left == target) {
            return RelativePosition.LEFT;
        } else if (parent.right == target) {
            return RelativePosition.RIGHT;
        } else {
            throw new RuntimeException("不是父子节点关系");
        }
    }

    /**
     * 获得当前节点的后继节点
     * 后继节点value值大于该节点value值并且值最小的节点
     *
     * @param targetEntryNode 当前节点
     * @return 获得当前节点的后继节点
     */
    private EntryNode<K, V> getSuccessor(EntryNode<K, V> targetEntryNode) {
        if (targetEntryNode == null) {
            //当前节点为null，则后继也为null
            return null;
        }
        if (targetEntryNode.right != null) {
            //右节点不为null
            EntryNode<K, V> rightEntryNode = targetEntryNode.right;
            //取右节点的左节点->左节点 = 最左节点
            while (rightEntryNode.left != null) {
                rightEntryNode = rightEntryNode.left;
            }
            return rightEntryNode;
        } else {
            EntryNode<K, V> child = targetEntryNode;
            EntryNode<K, V> parent = targetEntryNode.parent;
            //大于当前节点的必然是当前节点的右节点 或者 当前节点是父节点的左节点，因此取第一个把当前节点视为左节点的父节点。
            while (parent != null && parent.left != child) {
                child = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    /**
     * 获取当前节点的前驱节点
     * 前驱节点value值小于该节点value值并且值最大的节点
     *
     * @param targetEntryNode 当前节点
     * @return
     */
    private EntryNode<K, V> getPrecursor(EntryNode<K, V> targetEntryNode) {
        //当前节点为null返回null
        if (targetEntryNode == null) {
            return null;
        }
        if (targetEntryNode.left != null) {
            //当前节点的左节点不为null
            EntryNode<K, V> leftEntryNode = targetEntryNode.left;
            //取左节点的最右节点，左节点比当前节点小，左节点的右节点比左节点大，取左节点的最右节点，比当前节点小，但是是最大值
            while (leftEntryNode.right != null) {
                leftEntryNode = leftEntryNode.right;
            }
            return leftEntryNode;
        } else {
            EntryNode<K, V> child = targetEntryNode;
            EntryNode<K, V> parent = targetEntryNode.parent;
            //小于当前节点的必然是当前节点的左节点 或者 当前节点是父节点的右节点，因此取第一个把当前节点视为右节点的父节点。
            while (parent != null && parent.right != child) {
                child = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    /**
     * 获得二叉搜索树的第一个节点
     *
     * @return
     */
    private EntryNode<K, V> getFirstNode() {
        if (this.root == null) {
            return null;
        }
        EntryNode<K, V> entryNode = this.root;
        //循环往复，寻找整棵树的最左节点(最小节点、第一个节点)
        while (entryNode.left != null) {
            entryNode = entryNode.left;
        }
        return entryNode;
    }

    @Override
    public V put(K key, V value) {
        if (this.size == 0) {
            this.root = new EntryNode<>(key, value);
            this.size++;
            return null;
        }
        //获得key对应的目标节点
        TargetEntryNode<K, V> targetEntryNode = getTargetEntryNode(key);
        //目标节点对应父节点
        EntryNode<K, V> parent = targetEntryNode.parent;
        //key存在,目标节点存在于当前容器，value覆盖
        if (targetEntryNode.relativePosition == RelativePosition.CURRENT) {
            //暂存旧value
            V oldValue = targetEntryNode.target.value;
            targetEntryNode.target.value = value;
            //返回旧的value
            return oldValue;
        } else {
            //新增加的节点
            EntryNode<K, V> entryNode = new EntryNode<>(key, value, parent);
            //新节点是父节点的左节点
            if (targetEntryNode.relativePosition == RelativePosition.LEFT) {
                parent.left = entryNode;
            } else {
                //新节点是父节点的右节点
                parent.right = entryNode;
            }
            this.size++;
            return null;
        }
    }

    @Override
    public V remove(K key) {
        if (this.root == null) {
            return null;
        }
        //查询目标节点
        TargetEntryNode<K, V> targetEntryNode = getTargetEntryNode(key);
        if (targetEntryNode.relativePosition != RelativePosition.CURRENT) {
            //没有找到目标节点
            return null;
        } else {
            //找到了目标节点
            //从二叉树中删除目标节点
            deleteEntryNode(targetEntryNode.target);
            return targetEntryNode.target.getValue();
        }
    }

    /**
     * 将目标节点从二叉搜索树中删除
     *
     * @param target
     */
    private void deleteEntryNode(EntryNode<K, V> target) {
        /*
         * 删除二叉搜索树节点
         *     1.无左右孩子
         *         直接删除
         *     2.只有左孩子或者右孩子
         *         将唯一的孩子和parent节点直接相连
         *     3.既有左孩子，又有右孩子
         *         找到自己的直接前驱/后继（左侧的最右节点/右侧的最左节点）
         *         将自己和直接后继进行交换，转换为第1或第2种情况，并将自己删除
         * */
        //size自减1
        this.size--;
        //既有左孩子又有右孩子
        if (target.left != null && target.right != null) {
            //可以将当前节点的后继节点或者前驱节点替换当前节点，都能保证替换后的二叉树还是有序
            EntryNode<K, V> successor = getSuccessor(target);
            //target的key/value和自己的后继交换
            target.value = successor.value;
            target.key = successor.key;
            //target指向自己的后继，转换为第一/第二种情况,由于原来的目标节点是既有左孩子又有右孩子，后继节点必然是右孩子的最左节点或者是右孩子自己，
            //所以必然后继节点只会是没有孩子或者只有一个右孩子，不可能同时有左右孩子。
            target = successor;
        }
        EntryNode<K, V> parent = target.parent;
        //获得删除节点或者(被代替删除节点):从左右孩子中选择一个,左右孩子只会存在一个
        EntryNode<K, V> replacement = (target.left == null ? target.right : target.left);
        //如果没有孩子节点
        if (replacement == null) {
            //被删除的target没有父节点说明是根节点，且无左右孩子
            if (parent == null) {
                this.root = null;
            } else {
                //判断target和parent的相对位置，删除他们之间的引用关系
                RelativePosition relativeByParent = getRelativeByParent(parent, target);
                if (relativeByParent == RelativePosition.LEFT) {
                    //是父节点的左节点
                    parent.left = null;
                } else {
                    //是父节点的右节点
                    parent.right = null;
                }
            }
        } else {
            //只有左孩子或者右孩子
            //被删除的target是根节点，且只有左孩子或者右孩子
            if (parent == null) {
                this.root = (target.left == null ? target.right : target.left);
            } else {
                //将被删除节点的孩子节点的父节点引用指向被删除节点的父节点
                replacement.parent = target.parent;
                RelativePosition relativePosition = getRelativeByParent(parent, target);
                //:::被删除节点的双亲节点指向被代替的节点
                if (relativePosition == RelativePosition.LEFT) {
                    parent.left = replacement;
                } else {
                    parent.right = replacement;
                }
            }
        }
    }

    @Override
    public V get(K key) {
        return (getEntryNode(key) == null ? null : getEntryNode(key).value);
    }

    /**
     * 获取目标节点
     *
     * @param key
     * @return
     */
    private EntryNode<K, V> getEntryNode(K key) {
        //判断二叉树是否有节点
        if (this.root == null) {
            return null;
        } else {
            TargetEntryNode<K, V> targetEntryNode = getTargetEntryNode(key);
            if (targetEntryNode.relativePosition == RelativePosition.CURRENT) {
                return targetEntryNode.target;
            }
            //没有找到目标节点
            return null;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return getEntryNode(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        //获取key值最小的节点
        EntryNode<K, V> firstNode = getFirstNode();
        while (firstNode != null) {
            if (Objects.equals(firstNode.getValue(), value)) {
                //当前节点value匹配，返回true
                return true;
            }
            //最小的节点->指向下一个直接后继节点，相当于二叉树遍历的中序遍历，当一个节点找不到后继节点说明遍历完了
            firstNode = getSuccessor(firstNode);
        }
        //遍历整颗树之后，还未匹配，返回false
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.root == null);
    }

    @Override
    public void clear() {
        this.size = 0;
        this.root = null;
    }

    @Override
    public String toString() {
        Iterator<Map.EntryNode<K, V>> iterator = this.iterator();

        //:::空容器
        if (!iterator.hasNext()) {
            return "[]";
        }

        //:::容器起始使用"["
        StringBuilder s = new StringBuilder("[");

        //:::反复迭代
        while (true) {
            //:::获得迭代的当前元素
            Map.EntryNode<K, V> data = iterator.next();

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

    @Override
    public Iterator<Map.EntryNode<K, V>> iterator() {
        return new Itr();
    }

    /**
     * 二叉搜索树 内部节点
     */
    private static class EntryNode<K, V> implements Map.EntryNode<K, V> {
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

    /**
     * 二叉搜索树 迭代器实现
     * 1. 二叉搜索树从最左节点开始，以中序遍历的方式遍历整颗树
     * <p>
     * 2. 在迭代器初始化时，迭代器指向最小的节点(也就是最左节点)
     * <p>
     * 3. 迭代器迭代时，下一个节点总是指向当前节点的直接后继
     */
    private class Itr implements Iterator<Map.EntryNode<K, V>> {
        /**
         * 当前迭代节点
         */
        private EntryNode<K, V> currentNode;

        /**
         * 下一个节点
         */
        private EntryNode<K, V> nextNode;

        private Itr() {
            //初始化时，nextNode指向第一个节点
            this.nextNode = TreeMap.this.getFirstNode();
        }

        @Override
        public boolean hasNext() {
            return this.nextNode != null;
        }

        @Override
        public Map.EntryNode<K, V> next() {
            this.currentNode = this.nextNode;
            //采用中序遍历迭代算法
            this.nextNode = TreeMap.this.getSuccessor(this.nextNode);
            return currentNode;
        }

        /**
         * 删除采用中序遍历每次删除最小的值
         * 中序遍历:每次都找后继节点就能从小到大不会遗漏的遍历完
         */
        @Override
        public void remove() {
            if (this.currentNode == null) {
                throw new RuntimeException("迭代器状态异常: 可能在一次迭代中进行了多次remove操作");
            }
            if (currentNode.left != null && currentNode.right != null) {
                // 同时存在左右孩子时删除的是后继节点，当前节点没有被删除只是key/value改变了，相关联的节点引用没变
                // nextNode不能指向后继节点了，因为后继节点不存在，还是指向当前节点，当前节点的值是原来后继节点的值。
                //(第一小的节点)当前节点有左右节点，删除后删除的是后继节点，当前节点变成(第二小的节点)，所以下个节点还是当前节点
                this.nextNode = this.currentNode;
            }
            //删除当前节点，删除节点后二叉树的顺序还是符合，左子树上结点的值均小于父结点的值，右子树上结点的值均大于父结点的值
            //当前节点被删除后，所以取后继节点，后继节点就是整个二叉树中最小的，每次都找后继节点就能从小到大不会遗漏的遍历完。
            deleteEntryNode(currentNode);
            //currentNode设置为null，防止反复调用remove方法
            this.currentNode = null;
        }
    }

    /**
     * 先序遍历递归: 首先访问根，再先序遍历左子树，最后先序遍历右子树
     * 后序遍历递归: 首先后序遍历左子树，再后序遍历右子树，最后访问根
     * 中序遍历递归: 首先中序遍历左子树，再访问根，最后中序遍历右子树
     * 3种遍历的递归代码相似，就是什么时候输出root节点，先输出root为先序，后输出root为后序，左右节点判断非null中间输出root为中序
     * 其中 中序遍历是有序的，采用迭代时，可以先查找最左节点，然后查找后继节点。
     * 调用该方法前先校验root不为null
     *
     * @param root 根节点
     */
    public void preorderTraversal(EntryNode<K, V> root) {
        //先序遍历
//        System.out.println(root);
        if (root.left != null) {
            preorderTraversal(root.left);
        }
        //中序遍历
        System.out.println(root.value);
        if (root.right != null) {
            preorderTraversal(root.right);
        }
        //后序遍历
//        System.out.println(root);
    }

    /**
     * 递归遍历二叉树
     *
     * @param root
     */
    public void traversal(EntryNode<K, V> root) {
        if (root == null) {
            return;
        }
//        System.out.println(root);//先序
        traversal(root.left);
//        System.out.println(root);//中序
        traversal(root.right);
//        System.out.println(root);//后序
    }

    public static void main(String[] args) {
        TreeMap treeMap = new TreeMap();
        treeMap.put(12, 12);
        for (int i = 8; i < 15; i++) {
            treeMap.put(i, i);
        }
        treeMap.preorderTraversal(treeMap.root);

    }

}
