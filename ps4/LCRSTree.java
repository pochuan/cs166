public class LCRSTree {
    /* So we can use this in a MeldableLinkedList */

    LCRSNode root;
    int order;

    public LCRSTree(int key) {
        root = new LCRSNode(key);
        order = 0;
    }

    public LCRSTree(LCRSNode newRoot, int newOrder) {
        root = newRoot;
        order = newOrder;
    }

    public void mergeEqual(LCRSTree newTree) {
        assert order == newTree.order : "Order is not equal";
        if (root.payload <= newTree.root.payload) {
            newTree.root.rightSibling = root.leftChild;
            root.leftChild = newTree.root;
        } else {
            root.rightSibling = newTree.root.leftChild;
            newTree.root.leftChild = root;
            root = newTree.root;
        }
        order++;

    }

    public int getMinValue() {
        return root.payload;
    }

    public MeldableLinkedList<LCRSTree> removeMin() {
        MeldableLinkedList<LCRSTree> siblings = new MeldableLinkedList<LCRSTree>();
        LCRSNode currNode = root.leftChild;
        int currOrder = order-1;
        while (currNode != null) {
            assert (order >= 0) : "LCRSTree removeMin: Too many siblings";
            siblings.append(new LinkedListNode<LCRSTree>(new LCRSTree(currNode, currOrder)));
            currOrder--;
            currNode = currNode.rightSibling;
        }
        return siblings;
    }

}