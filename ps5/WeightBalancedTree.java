/**
 * An implementation of a static BST backed by a weight-balanced tree.
 */
public class WeightBalancedTree implements BST {
	/**
	 * Constructs a new tree from the specified array of weights. The array entry
	 * at position 0 specifies the weight of 0, the entry at position 1 specifies
	 * the weight of 1, etc.
	 *
	 * @param The weights on the elements.
	 */
	public WeightBalancedTree(double[] elements) {
		// TODO: Implement this!

        double[] test = {1,3,2,5};
        System.out.println("Found cut at: " + findCut(test, 0, test.length));
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
		return false;
	}
}
