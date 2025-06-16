package resources;

import java.util.LinkedList;
import java.util.Queue;

public class FriendSuggestionTree {
    public static class TreeNode {
        int userId;
        TreeNode left, right;

        public TreeNode(int userId) {
            this.userId = userId;
        }
    }

    private TreeNode root;

    public void insert(int userId) {
        root = insertRec(root, userId);
    }

    private TreeNode insertRec(TreeNode root, int userId) {
        if (root == null) return new TreeNode(userId);
        if (userId < root.userId) root.left = insertRec(root.left, userId);
        else if (userId > root.userId) root.right = insertRec(root.right, userId);
        return root;
    }

    public String levelOrderPrint() {
        if (root == null) return "(empty tree)\n";

        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            sb.append(current.userId).append(" ");
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }

        return sb.toString();
    }

    public void clear() {
        root = null; // Reset the tree by setting root to null
    }

    public TreeNode getRoot() {
        return root;
    }
}