package resources;


public class LCAUtil {
    public static Integer findLCA(FriendSuggestionTree.TreeNode root, int a, int b) {
        if (root == null) return null;

        if (root.userId > a && root.userId > b) return findLCA(root.left, a, b);
        if (root.userId < a && root.userId < b) return findLCA(root.right, a, b);

        return root.userId;
    }
}
