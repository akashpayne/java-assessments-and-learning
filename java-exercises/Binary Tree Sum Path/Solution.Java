public class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> rst = new ArrayList<List<Integer>>();
        if (root == null) {
            return rst;
        }    
        dfs(rst, new ArrayList<Integer>(), root, 0, sum);
        
        return rst;
    }
    
    public void dfs(List<List<Integer>> rst, ArrayList<Integer> list, TreeNode node, int add, int sum) {
        list.add(node.val);
        if (node.left == null && node.right == null) {
            if (add + node.val == sum) {
                rst.add(new ArrayList<Integer>(list));                   
            }
            return;
        }
        if (node.left != null) {
            dfs(rst, list, node.left, add + node.val, sum);
            list.remove(list.size() - 1);
        }
        if (node.right != null) {
            dfs(rst, list, node.right, add + node.val, sum);
            list.remove(list.size() - 1);
        }
    }
}

public class Solution {
    public List<List<Integer>> binaryTreePathSum(TreeNode root, int target) {
    	List<List<Integer>> rst = new ArrayList<List<Integer>>();
    	if (root == null) {
    		return rst;
    	}
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	list.add(root.val);
    	traversal(rst, list, root, root.val, target);
    	return rst;
    }


    public void traversal(List<List<Integer>> rst, ArrayList<Integer> list, TreeNode node, int sum, int target) {
    	if (node.left == null && node.right == null) {
    		if (sum == target) {
    			rst.add(new ArrayList<Integer>(list));
    		}
    		return;
    	}
    	if (node.left != null) {
    	    list.add(node.left.val);
    		traversal(rst, list, node.left, sum + node.left.val, target);
    		list.remove(list.size() - 1);
    	}
    	if (node.right != null) {
    	    list.add(node.right.val);
    		traversal(rst, list, node.right, sum + node.right.val, target);
    		list.remove(list.size() - 1);
    	}
    }
}
