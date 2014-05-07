public class WBTNode {
    public WBTNode leftChild = null;
    public WBTNode rightChild = null;
    public int payload;
    public double weight;

    public WBTNode(double newWeight, int newPayload) {
        weight = newWeight;
        payload = newPayload;
    }

}
