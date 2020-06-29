//给定一个二叉树，返回它的 前序 遍历。 
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
//输出: [1,2,3]
// 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
// Related Topics 栈 树

/**
 * 先序遍历递归: 首先访问根，再先序遍历左子树，最后先序遍历右子树;中左右
 * 后序遍历递归: 首先后序遍历左子树，再后序遍历右子树，最后访问根;左右中
 * 中序遍历递归: 首先中序遍历左子树，再访问根，最后中序遍历右子树;左中右
 * 3种遍历的递归代码相似，就是什么时候输出root节点，先输出root为先序，后输出root为后序，左右节点判断非null中间输出root为中序
 * 其中 中序遍历是有序的，采用迭代时，可以先查找最左节点，然后查找后继节点。
 */

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
    public List<Integer> preorderTraversal(TreeNode root) {
        List list = new LinkedList<Integer>();
        if (root == null) {
            return list;
        }
        //使用栈的先进后出策略保存遍历记录
        Stack<TreeNode> stack = new Stack<>();
        //由于先序遍历访问顺序为：中左右，先输出中节点，再先序遍历左节点，再先序号遍历右节点
        //先左后右，因此加入栈的顺序为，先push右，后push左，弹栈时才会是先左后右。
        stack.push(root);
        while (!stack.isEmpty()) {
            // 第一次循环弹出中节点，判断中节点是否有左右节点
            // 下一次循环就是先序遍历左节点
            // 后面就是先序遍历右节点
            TreeNode node = stack.pop();
            if (node != null) {
                list.add(node.val);
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
        }
        return list;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

//class Solution {
//    public List<Integer> preorderTraversal(TreeNode root) {
//        List list = new ArrayList<Integer>();
//        return getIntegerList(root, list);
//    }
//
//    private List<Integer> getIntegerList(TreeNode root, List<Integer> list) {
//        if (root == null) {
//            return list;
//        }
//        list.add(root.val);
//        if (root.left != null) {
//            getIntegerList(root.left, list);
//        }
//        if (root.right != null) {
//            getIntegerList(root.right, list);
//        }
//        return list;
//    }
//}