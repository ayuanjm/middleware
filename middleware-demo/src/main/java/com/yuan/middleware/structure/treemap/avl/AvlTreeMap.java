package com.yuan.middleware.structure.treemap.avl;

import com.yuan.middleware.structure.treemap.Comparator;
import com.yuan.middleware.structure.treemap.Map;
import com.yuan.middleware.structure.treemap.RelativePosition;

import java.util.Iterator;

/**
 * AVL树:平衡二叉搜索树
 *
 * @author yuan
 * @date 2019/12/16
 */
public class AvlTreeMap<K, V> implements Map<K, V> {
    /**
     * 根节点
     */
    private EntryNode<K, V> root;

    /**
     * 比较器(初始化之后，不能改)
     */

    /**
     * 当前二叉树的大小
     */
    private int size;
    /**
     * 比较器(初始化之后，不能改)
     */
    private final Comparator<? super K> comparator;

    /**
     * 默认构造函数
     */
    public AvlTreeMap() {
        this.comparator = null;
    }

    @Override
    public V put(K key, V value) {
        if (this.root == null) {
            this.root = new EntryNode<>(key, value);
            this.size++;
            return null;
        }

        //获得目标节点
        TargetEntryNode<K, V> targetEntryNode = getTargetEntryNode(key);
        if (targetEntryNode.relativePosition == RelativePosition.CURRENT) {
            //目标节点存在于当前容器

            //暂存之前的value
            V oldValue = targetEntryNode.target.value;
            //替换为新的value
            targetEntryNode.target.value = value;
            //返回之前的value
            return oldValue;
        } else {
            //目标节点不存在于当前容器
            EntryNode<K, V> parent = targetEntryNode.parent;
            EntryNode<K, V> newEntryNode = new EntryNode<>(key, value, parent);
            if (targetEntryNode.relativePosition == RelativePosition.LEFT) {
                //目标节点位于左边
                parent.left = newEntryNode;
            } else {
                //目标节点位于右边
                parent.right = newEntryNode;
            }

            //插入新节点后进行重平衡操作
            afterNewNodeInsert(newEntryNode);

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
            EntryNode<K, V> target = targetEntryNode.target;
            //先保存被删除节点 删除之前的双亲节点
            EntryNode<K, V> parent = target.parent;

            //从二叉树中删除目标节点
            deleteEntryNode(target);

            //删除节点后，对其历代祖先节点进行重平衡操作
            afterNodeRemove(parent);

            return targetEntryNode.target.value;
        }
    }

    /**
     * 当前节点 是否满足AVL树约定的平衡条件
     */
    private boolean isAVLBalanced(EntryNode<K, V> entryNode) {
        //获得 左子树高度
        int leftChildHeight = getHeight(entryNode.left);
        //获得右子树高度
        int rightChildHeight = getHeight(entryNode.right);

        //获得左右子树高度差
        int difference = leftChildHeight - rightChildHeight;

        //高度差绝对值在1之内,认为是满足AVL平衡条件
        return -1 <= difference && difference <= 1;
    }

    /**
     * 更新当前节点高度
     */
    private void updateHeight(EntryNode<K, V> entryNode) {
        int leftHeight = getHeight(entryNode.left);
        int rightHeight = getHeight(entryNode.right);

        //左右子树高度较高者 + 1
        entryNode.height = 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * 获得当前节点的高度
     */
    private int getHeight(EntryNode<K, V> entryNode) {
        if (entryNode == null) {
            return 0;
        } else {
            return entryNode.height;
        }
    }

    /**
     * 获得较高子树分支孩子节点
     */
    private EntryNode<K, V> getTallerChild(EntryNode<K, V> entryNode) {
        int leftChildHeight = getHeight(entryNode.left);
        int rightChildHeight = getHeight(entryNode.right);

        if (leftChildHeight > rightChildHeight) {
            //左子树高度 > 右子树高度
            return entryNode.left;
        } else {
            //左子树高度 <= 右子树高度
            return entryNode.right;
        }
    }

    /**
     * 是否是左孩子
     */
    private boolean isLeftChild(EntryNode<K, V> parent, EntryNode<K, V> target) {
        return getRelativeByParent(parent, target) == RelativePosition.LEFT;
    }

    /**
     * 3+4 重构
     * 重平衡之后对应位置的节点
     * 例如: 原来 currentNode，sonNode，grandSonNode 对应结构为右右左，重平衡后对应leftNode,middleNode,rightNode 节点分别为 currentNode,grandSonNode,sonNode
     * llChild : currentNode.left ,lrChild : grandSonNode.left ,rlChild : grandSonNode.right ,rrChild : sonNode.right
     * 将原来currentNode，sonNode，grandSonNode 节点位置调整后，要将除这3个节点外的子节点正确的放在调整后的 左右节点的两边，
     * 除了currentNode，sonNode，grandSonNode 3个节点外和他们相关联的还有4个节点，将这4个节点放在调整后的 左右节点的两边，
     * 只需要将4个节点放置正确就可以就能保证二叉树的有序，不需要调整以4个节点为根节点的子树内部的节点。
     *
     * @param leftNode   左节点
     * @param middleNode 父节点
     * @param rightNode  右节点
     * @param llChild    左节点的左节点
     * @param lrChild    左节点的右节点
     * @param rlChild    右节点的左节点
     * @param rrChild    右节点的右节点
     */
    private void refactor34(
            EntryNode<K, V> leftNode, EntryNode<K, V> middleNode, EntryNode<K, V> rightNode,
            EntryNode<K, V> llChild, EntryNode<K, V> lrChild,
            EntryNode<K, V> rlChild, EntryNode<K, V> rrChild) {

        //调整 左节点和对应子树的拓扑结构
        leftNode.left = llChild;
        if (llChild != null) {
            llChild.parent = leftNode;
        }

        leftNode.right = lrChild;
        if (lrChild != null) {
            lrChild.parent = leftNode;
        }
        //更新高度
        updateHeight(leftNode);

        //调整 右节点和对应子树的拓扑结构
        rightNode.left = rlChild;
        if (rlChild != null) {
            rlChild.parent = rightNode;
        }

        rightNode.right = rrChild;
        if (rrChild != null) {
            rrChild.parent = rightNode;
        }
        //更新高度
        updateHeight(rightNode);

        //调整 中间节点 和左、右节点的拓扑结构
        middleNode.left = leftNode;
        middleNode.right = rightNode;

        leftNode.parent = middleNode;
        rightNode.parent = middleNode;
        //更新高度
        updateHeight(middleNode);
    }


    /**
     * 进行旋转,使用3+4重构完成重平衡
     *
     * @param currentNode  当前节点
     * @param sonNode      子节点
     * @param grandSonNode 孙子节点
     * @return 重构之后子树的树根节点
     */
    private EntryNode<K, V> rotateAt(EntryNode<K, V> currentNode, EntryNode<K, V> sonNode, EntryNode<K, V> grandSonNode) {
        if (isLeftChild(currentNode, sonNode)) {
            //左 zig
            if (isLeftChild(sonNode, grandSonNode)) {
                //左-左   zig-zig旋转
                refactor34(grandSonNode, sonNode, currentNode,
                        grandSonNode.left, grandSonNode.right, sonNode.right, currentNode.right);

                return sonNode;
            } else {
                //左-右   zig-zag旋转
                refactor34(sonNode, grandSonNode, currentNode,
                        sonNode.left, grandSonNode.left, grandSonNode.right, currentNode.right);

                return grandSonNode;
            }
        } else {
            //右 zag
            if (isLeftChild(sonNode, grandSonNode)) {
                //右-左   zag-zig旋转
                refactor34(currentNode, grandSonNode, sonNode,
                        currentNode.left, grandSonNode.left, grandSonNode.right, sonNode.right);

                return grandSonNode;
            } else {
                //右-右   zag-zag旋转
                refactor34(currentNode, sonNode, grandSonNode,
                        currentNode.left, sonNode.left, grandSonNode.left, grandSonNode.right);

                return sonNode;
            }
        }
    }

    /**
     * 插入后 重平衡操作
     * 参数为新插入的节点。从下至上，遍历检查新插入节点的历代祖先，判断其是否失衡。一旦发现当前迭代的祖先节点失衡，则调用rotateAt方法，使其恢复平衡，全树重新接入子树；
     * 插入节点时，导致的失衡不会向上传播，所属子树的高度能够复原，在恢复平衡之后，直接结束方法的执行，不再继续向上检查。另外，对于未失衡的祖先节点，其子树插入新节点时可能会导致高度上升，因此需要更新其高度。
     *
     * @param newEntryNode 新插入的节点
     */
    private void afterNewNodeInsert(EntryNode<K, V> newEntryNode) {
        EntryNode<K, V> currentAncestorNode = newEntryNode.parent;

        //遍历新插入节点的祖先节点,逐层向上
        while (currentAncestorNode != null) {
            //判断当前祖先节点是否失去平衡
            if (!isAVLBalanced(currentAncestorNode)) {
                //不平衡

                //获得重构之前 失衡节点的父节点及其相对位置，用于之后重新连接重平衡后的子树
                EntryNode<K, V> parent = currentAncestorNode.parent;

                //获得更高子树分支对应的孙辈节点，决定AVL树重平衡的策略
                EntryNode<K, V> tallerSonNode = getTallerChild(currentAncestorNode);
                EntryNode<K, V> tallerGrandSonNode = getTallerChild(tallerSonNode);
                //以孙辈节点为基准，进行旋转，重平衡
                EntryNode<K, V> newSubTreeRoot = rotateAt(currentAncestorNode, tallerSonNode, tallerGrandSonNode);

                //重构之后的子树 重新和全树对接
                newSubTreeRoot.parent = parent;
                //可能currentAncestorNode是根节点，不存在双亲节点
                if (parent != null) {
                    //原子树根节点的双亲节点 和新的子树进行连接
                    if (isLeftChild(parent, currentAncestorNode)) {
                        parent.left = newSubTreeRoot;
                    } else {
                        parent.right = newSubTreeRoot;
                    }
                } else {
                    this.root = newSubTreeRoot;
                }
                //插入时，最低失衡节点重平衡后，全树即恢复平衡，因此结束循环
                return;
            } else {
                //平衡

                //更新当前祖先节点的高度
                updateHeight(currentAncestorNode);
            }

            //指向上一层祖先节点
            currentAncestorNode = currentAncestorNode.parent;
        }
    }

    public static void main(String[] args) {
        AvlTreeMap avlTreeMap = null;
        System.out.println(avlTreeMap.toString());
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
     * 插入后 重平衡操作
     * 参数为之前被删除节点的双亲节点。从下至上，遍历检查被删除节点双亲的历代祖先，判断其是否失衡。一旦发现当前迭代的祖先节点失衡，则调用rotateAt方法，使其恢复平衡，全树重新接入子树。
     * 删除节点时，失衡现象会向上传播，因此必须一直向上遍历至根节点。另外，对于未失衡的祖先节点，子树删除老节点时可能会导致高度降低，因此需要更新其高度。
     *
     * @param deletedNode 被删除的节点
     */
    private void afterNodeRemove(EntryNode<K, V> deletedNode) {
        EntryNode<K, V> currentAncestorNode = deletedNode;

        //遍历新插入节点的祖先节点,逐层向上
        while (currentAncestorNode != null) {
            //判断当前祖先节点是否失去平衡
            if (!isAVLBalanced(currentAncestorNode)) {
                //不平衡

                //获得重构之前 失衡节点的父节点及其相对位置
                EntryNode<K, V> parent = currentAncestorNode.parent;
                //获得更高子树分支对应的孙辈节点，决定AVL树重平衡的策略
                EntryNode<K, V> tallerSonNode = getTallerChild(currentAncestorNode);
                EntryNode<K, V> tallerGrandSonNode = getTallerChild(tallerSonNode);
                //以孙辈节点为基准，进行旋转，重平衡
                EntryNode<K, V> newSubTreeRoot = rotateAt(currentAncestorNode, tallerSonNode, tallerGrandSonNode);

                //重构之后的子树 重新和全树对接
                newSubTreeRoot.parent = parent;
                //可能currentAncestorNode是根节点，不存在双亲节点
                if (parent != null) {
                    //原子树根节点的双亲节点 和新的子树进行连接
                    if (isLeftChild(parent, currentAncestorNode)) {
                        parent.left = newSubTreeRoot;
                    } else {
                        parent.right = newSubTreeRoot;
                    }
                } else {
                    this.root = newSubTreeRoot;
                }
            } else {
                //平衡

                //更新当前祖先节点的高度
                updateHeight(currentAncestorNode);
            }

            //指向上一层祖先节点
            currentAncestorNode = currentAncestorNode.parent;
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
     * 二叉搜索树 内部节点
     *
     * @param <K>
     * @param <V>
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
         * 高度值
         */
        int height;
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
            this.height = 1;
        }

        EntryNode(K key, V value, EntryNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.height = 1;
        }

        @Override
        public K getKey() {
            return null;
        }

        @Override
        public V getValue() {
            return null;
        }

        @Override
        public void setValue(V value) {

        }

        @Override
        public String toString() {
            return "EntryNode{" +
                    "key=" + key +
                    ", value=" + value +
                    ", height=" + height +
                    ", left=" + left +
                    ", right=" + right +
                    ", parent=" + parent +
                    '}';
        }
    }
}
