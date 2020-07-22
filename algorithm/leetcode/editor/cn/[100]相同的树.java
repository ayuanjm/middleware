//给定两个二叉树，编写一个函数来检验它们是否相同。 
//
// 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。 
//
// 示例 1: 
//
// 输入:       1         1
//          / \       / \
//         2   3     2   3
//
//        [1,2,3],   [1,2,3]
//
//输出: true 
//
// 示例 2: 
//
// 输入:      1          1
//          /           \
//         2             2
//
//        [1,2],     [1,null,2]
//
//输出: false
// 
//
// 示例 3: 
//
// 输入:       1         1
//          / \       / \
//         2   1     1   2
//
//        [1,2,1],   [1,1,2]
//
//输出: false
// 
// Related Topics 树 深度优先搜索


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        LinkedList<TreeNode> queue1 = new LinkedList<>();
        LinkedList<TreeNode> p1 = new LinkedList<>();
        queue1.add(p);
        addList(queue1, p1);


        LinkedList<TreeNode> queue2 = new LinkedList<>();
        LinkedList<TreeNode> q2 = new LinkedList<>();
        queue2.add(q);
        addList(queue2, q2);

        if (q2.size() != p1.size()) {
            return false;
        }
        for (int i = 0; i < p1.size(); i++) {
            if (p1.get(i) != null) {
                System.out.println(p1.get(i).val);
            } else {
                System.out.println(p1.get(i));
            }
        }
        for (int i = 0; i < q2.size(); i++) {
            if (q2.get(i) != null && p1.get(i) != null) {
                if (q2.get(i).val == p1.get(i).val) {
                    continue;
                } else {
                    return false;
                }
            }
            if (q2.get(i) == null && p1.get(i) == null) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    private void addList(LinkedList<TreeNode> queue, LinkedList<TreeNode> p1) {
        while (!queue.isEmpty()) {
            TreeNode tree = queue.remove(0);
            p1.add(tree);
            if (tree == null) {
                continue;
            }
            if (tree.left != null) {
                queue.add(tree.left);
            } else {
                queue.add(null);
            }
            if (tree.right != null) {
                queue.add(tree.right);
            } else {
                queue.add(null);
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
