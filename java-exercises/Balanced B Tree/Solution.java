public class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return helper(root) > 0;
    }
    
    public int helper(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = helper(node.left);
        int rightDepth = helper(node.right);
        
        if (leftDepth < 0 || rightDepth < 0 || Math.abs(leftDepth - rightDepth) > 1) {
            return Integer.MIN_VALUE;
        }
        
        return Math.max(leftDepth, rightDepth) + 1;
    }
}


/*
    Recursive 2:
    Calculate a node's maxDepth. Compare a parent node's sub tree for maxDepth
*/
public class Solution {
   public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        if (Math.abs(leftDepth - rightDepth) > 1) {
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }
    
    public int maxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(maxDepth(node.left), maxDepth(node.right)) + 1;
    }
}
