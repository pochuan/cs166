public class LCRSNode {
    public LCRSNode leftChild = null;
    public LCRSNode rightSibling = null;
    public int payload;

    public LCRSNode(int newPayload) {
        payload = newPayload;
    }
}