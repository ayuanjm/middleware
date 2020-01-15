package com.yuan.middleware.structure.treemap.redblacktree;

import com.yuan.middleware.structure.treemap.binarytree.Comparator;
import com.yuan.middleware.structure.treemap.binarytree.Map;

import java.util.Iterator;

/**
 * 红黑树
 * https://www.jianshu.com/p/f4639d0cc887
 *
 * @author yuan
 * @date 2020/01/03
 */
public class RedBlackTree<K, V> implements Map<K, V> {
    /**
     * 根节点
     */
    private RedBlackTree.TreeNode<K, V> root;

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
    public RedBlackTree() {
        this.comparator = null;
    }

    /**
     * 对当前节点左旋，左旋就是当前节点原来是父节点变成了原来右节点的左节点
     * 节点关系图 \
     * https://upload-images.jianshu.io/upload_images/1630488-9bcc87f96afe09be.png?imageMogr2/auto-orient/strip|imageView2/2/w/550
     *
     * @param currentNode 当前节点
     */
    public void singleRotateWithLeft(TreeNode<K, V> currentNode) {
        if (currentNode != null) {
            // 当前节点的右节点 为旋转后的父节点
            TreeNode<K, V> r = currentNode.right;
            // 当前节点的右节点的左节点 为旋转后当前节点的右节点
            currentNode.right = r.left;
            // 如果当前节点的右节点的左节点不为null 旋转后需要改变它的父节点引用指向
            if (r.left != null) {
                // 当前节点的右节点的左节点旋转后父节点为当前节点
                r.left.parent = currentNode;
            }
            // 当前节点的右节点 旋转后需要改变它的父节点引用指向 指向当前节点的父节点
            r.parent = currentNode.parent;
            // 如果当前节点的父节点为null说明当前节点是根节点 当前节点的右节点旋转后也是根节点
            if (currentNode.parent == null) {
                this.root = r;
            } else if (currentNode.parent.left == currentNode) {
                // 当前节点是父节点的左节点 当前节点的右节点旋转后也是父节点的左节点
                currentNode.parent.left = r;
            } else {
                // 当前节点是父节点的右节点 当前节点的右节点旋转后也是父节点的右节点
                currentNode.parent.right = r;
            }
            // 当前节点的右节点 旋转后的左节点为当前节点
            r.left = currentNode;
            // 旋转后当前节点的父节点 为旋转前当前节点的右节点
            currentNode.parent = r;
        }
    }

    /**
     * 对当前节点右旋，右旋就是当前节点原来是父节点变成了原来左节点的右节点
     * 节点关系图 /
     * https://upload-images.jianshu.io/upload_images/1630488-3b9d8c6d24c439a2.png?imageMogr2/auto-orient/strip|imageView2/2/w/550
     *
     * @param currentNode 当前节点
     */
    public void singleRotateWithRight(TreeNode<K, V> currentNode) {
        if (currentNode != null) {
            TreeNode<K, V> l = currentNode.left;
            currentNode.left = l.right;
            if (l.right != null) {
                l.right.parent = currentNode;
            }
            l.parent = currentNode.parent;
            if (currentNode.parent == null) {
                this.root = l;
            } else if (currentNode.parent.left == currentNode) {
                currentNode.parent.left = l;
            } else {
                currentNode.parent.right = l;
            }
            l.right = currentNode;
            currentNode.parent = l;
        }
    }

    /**
     * 1. 每个节点具有颜色属性，要么为红色，要么为黑色
     * 2. 根节点是黑色的
     * 3. 每个叶子节点 (null) 是黑色的 (这里叶子节点，指为空的叶子节点)
     * 4. 如果一个节点是红色的，则其子节点必须是黑色的
     * 5. 从一个节点到该节点的叶节点 (null) 所有路径包含相同数目的黑节点
     *
     */

    /**
     * 一个节点要插入到红黑树中，需要的步骤：
     *
     * 将红黑树当作一棵二叉查找树，将节点插入
     * 将该节点着色为红色
     * 通过旋转和重新着色等方法修正该树，使之重新成为一棵红黑树
     * 第一步：将红黑树当作一棵二叉查找树，将节点插入
     * 红黑树本身也是二叉查找树，将节点插入后，该树仍是二叉查找树。
     *
     * 第二步：将该节点着色为红色
     * 将插入的节点着色为红色，不会违背特性(5):从一个节点到该节点的叶节点 (null) 所有路径包含相同数目的黑节点
     * 若插入的节点为黑色，那么该路径的节点就多了一个黑节点，这显然与特性(5) 相违背。
     *
     * 第三步：通过旋转和重新着色等方法修正该树，使之重新成为一棵红黑树
     * 第二步中，将插入的节点着色为 "红色" 之后，不会违背特性 (5)，那么它还会违背其他特性吗？
     * 对于特性(1) (2) (3) 显然都不会违背，请自行想象
     *
     * 而对于特性 (4)，是有可能违背的
     * 因为插入节点的父节点也可能为红色，那么显然与一个红色节点的子节点必须为黑节点相违背。
     * 那么，既然有可能违背特性(4) 那么我们可以通过旋转或者重新着色来使其满足特性(4)，再次成为红黑树。
     * 无论旋转还是重新着色，其核心思路都是：将红色的节点移到根节点；然后，将根节点设为黑色。
     *
     */


    /**
     * 情况1: 被插入的节点是根节点
     * 后面的情况方法都是在前面情况不成立的基础上进行调用
     *
     * @param currentNode 被插入的节点
     */
    public void insertCase1(TreeNode<K, V> currentNode) {
        if (currentNode.parent == null) {
            //父节点为null,说明是根节点,将根节点赋值黑色
            currentNode.red = false;
        } else {
            insertCase2(currentNode);
        }
    }

    /**
     * 情况2: 被插入的节点的父节点是黑色
     *
     * @param currentNode 被插入的节点
     */
    public void insertCase2(TreeNode<K, V> currentNode) {
        if (currentNode.parent.red == false) {
            //父节点为黑色，将插入的节点赋值为红色，又由于在插入操作时统一将节点颜色赋值为红色了，所以这里就不需要再次赋值
        } else {
            insertCase3(currentNode);
        }
    }

    /**
     * 情况3、4、5 都是建立在插入节点的父节点为红色的情况下，此时会违背特性(4)，所以我们需要通过旋转和重新着色来修复红黑树
     * 情况3: 叔叔节点是红色
     * <p>
     * 将 "父节点" 设为黑色
     * 将 "叔叔节点" 设为黑色
     * 将 "祖父节点" 设为红色
     * 将 "祖父节点" 设为 "当前节点"(红色节点)；之后继续对 "当前" 进行操作
     *
     * @param currentNode 被插入的节点
     */
    public void insertCase3(TreeNode<K, V> currentNode) {
        //叔叔节点
        TreeNode<K, V> uncle;
        if (currentNode.parent.parent.left == currentNode.parent) {
            uncle = currentNode.parent.parent.right;
        } else {
            uncle = currentNode.parent.parent.left;
        }

        if (uncle != null && uncle.red == true) {
            //将 "父节点" 设为黑色
            currentNode.parent.red = false;
            //将 "叔叔节点" 设为黑色
            uncle.red = false;
            //将 "祖父节点" 设为红色
            currentNode.parent.parent.red = true;
            //将 "祖父节点" 设为 "当前节点"(红色节点)；之后继续对 "当前" 进行操作
            insertCase1(currentNode.parent.parent);
        } else {
            insertCase4(currentNode);
        }
    }

    /**
     * 情况 4：叔叔节点为黑色或缺失，且 当前节点 父节点 祖父节点 3者为左右形或右左形 (父节点相对祖父节点,当前节点相对父节点))
     * <p>
     * 左右形：/   右左形：\
     * 左右形：\   右左形：/
     * 将 "父节点" 作为 "新的当前节点"
     * 以 "新的当前节点" 为支点进行左旋
     * 以新的当前节点(即原本的父节点)再进行操作
     *
     * @param currentNode 被插入的节点
     */
    public void insertCase4(TreeNode<K, V> currentNode) {
        // 父节点是祖父节点的左节点，当前节点是父节点的右节点
        if (currentNode.parent.parent.left == currentNode.parent && currentNode.parent.right == currentNode) {
            //将 "父节点" 作为 "新的当前节点"，以 "新的当前节点" 为支点进行左旋  \
            singleRotateWithLeft(currentNode.parent);
            //以新的当前节点(即原本的父节点)再进行操作 旋转后当前节点的父节点变成了它的左节点
            currentNode = currentNode.left;
        } else if (currentNode.parent.parent.right == currentNode.parent && currentNode.parent.left == currentNode) {
            //将 "父节点" 作为 "新的当前节点"，以 "新的当前节点" 为支点进行右旋  /
            singleRotateWithRight(currentNode.parent);
            //以新的当前节点(即原本的父节点)再进行操作 旋转后当前节点的父节点变成了它的右节点
            currentNode = currentNode.right;
        }
        insertCase5(currentNode);
    }

    /**
     * 情况 5: 叔叔节点为黑色或缺失，且 当前节点 父节点 祖父节点 3者为左左形或右右形
     * <p>
     * 将 "父节点" 设为黑色
     * 将 "祖父节点" 设为红色
     * 以 "祖父节点" 为支点进行旋转
     *
     * @param currentNode 被插入的节点
     */
    public void insertCase5(TreeNode<K, V> currentNode) {
        //将 "父节点" 设为黑色
        currentNode.parent.red = false;
        //将 "祖父节点" 设为红色
        currentNode.parent.parent.red = true;
        if (currentNode.parent.parent.left == currentNode.parent && currentNode.parent.left == currentNode) {
            //左左形   以 "祖父节点" 为支点进行左旋
            singleRotateWithLeft(currentNode.parent.parent);
        } else {
            //右右形   以 "祖父节点" 为支点进行右旋
            singleRotateWithRight(currentNode.parent.parent);
        }
    }


    @Override
    public Object put(Object key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
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
    public Iterator<EntryNode<K, V>> iterator() {
        return null;
    }


    static class TreeNode<K, V> implements Map.EntryNode {
        /**
         * key值
         */
        K key;

        /**
         * value值
         */
        V value;

        TreeNode<K, V> parent;  // red-black tree links
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        boolean red;

        public TreeNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public TreeNode(K key, V value, TreeNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        @Override
        public Object getKey() {
            return null;
        }

        @Override
        public Object getValue() {
            return null;
        }

        @Override
        public void setValue(Object value) {

        }
    }
}
