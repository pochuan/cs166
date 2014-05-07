import java.util.Arrays;
/**
 * An implementation of a static BST backed by a weight-balanced tree.
 */
public class WeightBalancedTree implements BST {

    WBTNode root;
	/**
	 * Constructs a new tree from the specified array of weights. The array entry
	 * at position 0 specifies the weight of 0, the entry at position 1 specifies
	 * the weight of 1, etc.
	 *
	 * @param The weights on the elements.
	 */
	public WeightBalancedTree(double[] elements) {
		// TODO: Implement this!
        //System.out.println("Construct WBT for " + Arrays.toString(elements));
        root = BalanceTree(elements, 0, elements.length);

        //double[] test = {1,2,3,4,5,6,7,8};
        //System.out.println("Found cut at: " + findCut(test, 0, test.length));
	}

    public WBTNode BalanceTree (double[] elems, int start, int end) {
        //System.out.println("BalanceTree for start=" + start + " end=" + end);
        if (start == end) return null;
        if ((end - start) == 1) {
            //System.out.println("Inserting key " + start);
            return new WBTNode(elems[start], start);
        }
        
        int cut = findCut(elems, start, end);
        //System.out.println("Inserting key " + cut);
        WBTNode currRoot = new WBTNode(elems[cut], cut);
        currRoot.leftChild = BalanceTree(elems, start, cut);
        currRoot.rightChild = BalanceTree(elems, cut+1, end);
        return currRoot;
    }

    public int findCut(double[] elems, int start, int end) {
        if ((end - start) == 1) return start;

        double total = 0;
        for (int i = start; i < end; ++i) {
            total += elems[i];
        }

        double leftSum = 0;
        double rightSum = total - elems[start];
        double diff = Math.abs(leftSum - rightSum);
        int cut = start;
        for (int i = start+1; i < end; ++i) {
            leftSum += elems[i-1];
            rightSum -= elems[i];
            double currDiff = Math.abs(leftSum - rightSum);
            if (currDiff < diff) {
                diff = currDiff;
                cut = i;
            }
            else break;
        }
        //System.out.println("cut at " + cut + " for start=" + start + " end=" + end);

        return cut;
    }
	
	/**
	 * Returns whether the specified key is in the BST.
	 *
	 * @param key The key to test.
	 * @return Whether it's in the BST.
	 */
	public boolean contains(int key) {
		// TODO: Implement this!
		return DFS(root, key);
	}
    
    public boolean DFS(WBTNode node, int key) {
        if (node == null) return false;
        else if (node.payload == key) return true;
        else {
            if (DFS(node.leftChild, key)) {
                return true;
            }
            return DFS(node.rightChild, key);
        }
    }
}
