public class Solution {
    public int longestConsecutive(TreeNode root) {
        return recursiveHelper(root, null, 0);
    }

    public int recursiveHelper(TreeNode curr, TreeNode parent, int depth) {
    	if (curr == null) {
    		return 0;
    	} 
    	int currDepth = 0;
    	if (parent != null && parent.val + 1 == curr.val) {
    		currDepth = depth + 1;
    	} else {
    		currDepth = 1;
    	}
    	return Math.max(currDepth, Math.max(recursiveHelper(curr.left, curr, currDepth), recursiveHelper(curr.right, curr, currDepth)));
    }
}
