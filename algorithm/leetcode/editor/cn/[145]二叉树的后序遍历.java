//给定一个二叉树，返回它的 后序 遍历。 
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
//输出: [3,2,1] 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
// Related Topics 栈 树


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

/**
 * 后序遍历：左右中，将root左侧链全部入栈，peek栈顶，判断有没有右节点，如果有，再后序遍历右节点
 * 也就是将右节点当作新的root，将root左侧链全部入栈，peek栈顶，判断有没有右节点，依次类推。
 */
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List list = new LinkedList<Integer>();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        //将root的左侧链入栈
        visitLeftBranch(root, stack);
        //用作弹出节点的记录
        TreeNode flag = null;
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            //当你的right是弹出节点时，说明你之前已经入过一次栈了
            if (node.right != flag && node.right != null) {
                // 调用完这个方法后，栈顶节点必然是一个没有左节点的节点，这时如果它没有右节点就可以被输出，
                // 因为它相当与根节点了，而这时以它为根的节点没有左右节点，左右中，就是输出中
                // 如果它有右节点，又需要对右节点后序遍历，依次类推，直到没有左右节点，输出栈顶。
                visitLeftBranch(node.right, stack);
            }
            //当你的node.right是弹出节点时，说明你之前已经入过一次栈了，需要把node弹出
            if (node.right == null || node.right == flag) {
                //将flagf赋值最新弹出节点
                flag = stack.pop();
                list.add(flag.val);
            }
        }
        return list;
    }

    /**
     * 将root的左侧链入栈
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
