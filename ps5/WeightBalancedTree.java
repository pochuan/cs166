import java.util.Arrays;
import java.util.ArrayList;

/**
 * An implementation of a static BST backed by a weight-balanced tree.
 */
public class WeightBalancedTree implements BST {

    WBTNode root;
    ArrayList <Double> sumArray = new ArrayList<Double>();

	/**
	 * Constructs a new tree from the specified array of weights. The array entry
	 * at position 0 specifies the weight of 0, the entry at position 1 specifies
	 * the weight of 1, etc.
	 *
	 * @param The weights on the elements.
	 */
	public WeightBalancedTree(double[] elements) {
        double sum = 0;
        for (int i = 0; i < elements.length; ++i) {
            sumArray.add(sum);
            sum += elements[i];
        }
        sumArray.add(sum);

        root = BalanceTree(elements, 0, elements.length);
	}

    public WBTNode BalanceTree (double[] elems, int start, int end) {
        if (start == end) return null;
        if ((end - start) == 1) {
            return new WBTNode(elems[start], start);
        }
        
        int cut = findCut(elems, start, end);
        WBTNode currRoot = new WBTNode(elems[cut], cut);
        currRoot.leftChild = BalanceTree(elems, start, cut);
        currRoot.rightChild = BalanceTree(elems, cut+1, end);
        return currRoot;
    }

    public int findCut(double[] elems, int start, int end) {
        if ((end - start) == 1) return start;

        //double total = 0;
        //for (int i = start; i < end; ++i) {
        //    total += elems[i];
        //}
        double total = sumArray.get(end) - sumArray.get(start);
        //assert (total == fastTotal) : "sum not equal, total="+total+" fastTotal="+fastTotal ;

        double leftSum = 0;
        double rightSum = total - elems[start];
        double diff = Math.abs(leftSum - rightSum);
        int cut = start;
        for (int i = start+1; i < end; ++i) {
            leftSum += elems[i-1];
            rightSum -= elems[i];
            double currDiff = Math.abs(leftSum - rightSum);
            if (currDiff <= diff) {
                diff = currDiff;
                cut = i;
            }
            else {
                return cut;
            }
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
        WBTNode cur = root;
        while (cur != null) {
            if (cur.payload == key) {
                return true;
            }
            if (cur.payload > key) {
                cur = cur.leftChild;
            }
            else {
                cur = cur.rightChild;
            }
        }
        return false;
	}
}
