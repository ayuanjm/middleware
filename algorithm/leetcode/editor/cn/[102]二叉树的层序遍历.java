//给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。 
//
// 
//
// 示例： 
//二叉树：[3,9,20,null,null,15,7], 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回其层次遍历结果： 
//
// [
//  [3],
//  [9,20],
//  [15,7]
//]
// 
// Related Topics 树 广度优先搜索


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
 * 层序遍历：从上到下，每一层从左到右，逐层遍历。
 * 使用队列将根节点加入队列，取出根节点，将它的左右节点分别加入队列，由于是先进先出，第二层先取队列头节点也就是左节点，
 * 将左节点的左右节点加入队列，再取队列头节点，也就是右节点，将右节点的左右节点加入队列。
 * 这样第一层输出完，第二层也是从左到右输出完，队列里剩余节点是第三层的，从左到右有序排列。
 * 然后从第三层开始，又取出队列头节点，将头节点也就是第三层的最左节点的左右节点加入队列尾部，依次类推。
 * 当第三层遍历完，第四层也加入队列了，这样依次类推，整颗树遍历完成。
 */
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        //外部链表保存用来保存每一层的链表引用
        List<List<Integer>> resultList = new LinkedList<>();
        if (root == null) {
            return resultList;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.push(root);
        while (!queue.isEmpty()) {
            //每一层对应一个链表，用来保存每一层的节点。
            LinkedList<Integer> levelList = new LinkedList<>();
            int size = queue.size();
            //每一层开始遍历时，取出队列节点个数，列如第一层时size为1，当第一层遍历完，队列里的节点个数为第二层的size，
            //使用第二层的size遍历时，遍历完了第二层，会把第三层加入队列，队列里的节点就是第三层的节点，依次类推遍历。
            for (int i = 0; i < size; i++) {
                //取出每一层的节点，将它的左右节点加入队列
                TreeNode node = queue.pop();
                levelList.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            resultList.add(levelList);
        }
        return resultList;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
