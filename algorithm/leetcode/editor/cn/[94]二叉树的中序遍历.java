//给定一个二叉树，返回它的中序 遍历。 
//
// 示例: 
//
// 输入: [1,null,2,3]
//   1
//    \
//     2
//    /
//   3
//
//输出: [1,3,2] 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
// Related Topics 栈 树 哈希表


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
    public List<Integer> inorderTraversal(TreeNode root) {
        List list = new LinkedList<Integer>();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        visitLeftBranch(root, stack);
        while (true) {
            if (stack.isEmpty()) {
                break;
            } else {
                //取出最左节点
                TreeNode node = stack.pop();
                if (node != null) {
                    list.add(node.val);
                    //将最左节点的右节点当作根节点，再次访问根节点的左侧链，将其加入栈中
                    visitLeftBranch(node.right, stack);
                }
            }
        }
        return list;
    }

    /**
     * 将根节点的左侧链加入栈中
     *
     * @param root
     * @param stack
     */
    private void visitLeftBranch(TreeNode root, Stack<TreeNode> stack) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
