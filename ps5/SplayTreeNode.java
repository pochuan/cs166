/**
 * An implementation of a Splay Tree Node
 */

public class SplayTreeNode {
    double key;
    SplayTreeNode left;
    SplayTreeNode right;
    SplayTreeNode parent;

    public SplayTreeNode(double newKey, SplayTreeNode newLeft,
                         SplayTreeNode newRight, SplayTreeNode newParent) {
        key = newKey;
        left = newLeft;
        right = newRight;
        parent = newParent;
    }

    public SplayTreeNode(double newKey) {
        key = newKey;
        left = null;
        right = null;
        parent = null;
    }

    // Returns the child's old parent
    public static SplayTreeNode addLeftChild(SplayTreeNode parent, SplayTreeNode child) {
        SplayTreeNode temp = child.parent;
        parent.left = child;
        child.parent = parent;
        return temp;
    }
}