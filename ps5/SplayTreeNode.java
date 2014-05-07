/**
 * An implementation of a Splay Tree Node
 */

public class SplayTreeNode {
    double key;
    SplayTreeNode left;
    SplayTreeNode right;
    SplayTreeNode parent;

    public SplayTreeNode(double newKey) {
        key = newKey;
        left = null;
        right = null;
        parent = null;
    }
}