import java.util.*;

/**
 Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
 Design an algorithm to serialise and deserialise a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialised to a string and this string can be deserialised to the original tree structure.
 */
public class serialisedeserialiseBinaryTree
{
    public static class TreeNode
    {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private static final String NULL = "X";
    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        TreeNode root = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        TreeNode four = new TreeNode(4);
        TreeNode five = new TreeNode(5);
        TreeNode six = new TreeNode(6);
        TreeNode seven = new TreeNode(7);
        TreeNode eight = new TreeNode(8);
        TreeNode nine = new TreeNode(9);
        TreeNode ten = new TreeNode(10);
        root.left = null;
        root.right = two;
        two.left = three;
        three.left = four;
        four.right = five;
        five.right = six;
        six.left = seven;
        seven.left = eight;
        eight.right = nine;
        nine.right = ten;
        String serialisedStr = new serialisedeserialiseBinaryTree().serialise(root);

        TreeNode result = new serialisedeserialiseBinaryTree().deserialise(serialisedStr);
    }

    // Encodes a tree to a single string.
    public String serialise(TreeNode root)
    {
        if(root == null) return null;
        List<String> list = new ArrayList<>();
        encode(root, list);
        StringBuilder sb = new StringBuilder();
        sb.append(list.get(0));
        for(int i = 1, l = list.size(); i < l; i++)
        {
            sb.append(",").append(list.get(i));
        }
        return sb.toString();
    }

    private void encode(TreeNode root, List<String> list)
    {
        if(root == null) list.add(NULL);
        else
        {
            list.add(String.valueOf(root.val));
            encode(root.left, list);
            encode(root.right, list);
        }
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialise(String data)
    {
        if(data == null || data.isEmpty()) return null;
        StringTokenizer st = new StringTokenizer(data, ",");
        Queue<String> queue = new ArrayDeque<>();
        while(st.hasMoreTokens())
            queue.offer(st.nextToken());
        return decode(queue);
    }

    private TreeNode decode(Queue<String> queue)
    {
        String node = queue.poll();
        if(node.equals(NULL))
            return null;
        TreeNode root = new TreeNode(Integer.parseInt(node));
        root.left = decode(queue);
        root.right = decode(queue);
        return root;
    }
}