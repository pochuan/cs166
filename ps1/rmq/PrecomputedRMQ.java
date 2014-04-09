package rmq;
/**
 * An &lt;O(n<sup>2</sup>), O(1)&gt; implementation of RMQ that precomputes the
 * value of RMQ_A(i, j) for all possible i and j.
 *
 * You will implement this class for problem 3.i of Problem Set One.
 */
public class PrecomputedRMQ implements RMQ {
	int[][] precomputed;
    /**
     * Creates a new PrecomputedRMQ structure to answer queries about the
     * array given by elems.
     *
     * @elems The array over which RMQ should be computed.
     */
    public PrecomputedRMQ(float[] elems) {
    	precomputed = new int[elems.length][elems.length];
        
        for (int i = 0; i < elems.length; i++) {
            precomputed[i][i] = i;
        }
        
        for (int i = 0; i < elems.length; i++) {
            for (int j = i + 1; j < elems.length; j++) {
                if (elems[precomputed[i][j - 1]] < elems[j]) {
                    precomputed[i][j] = precomputed[i][j - 1];
                }
                else {
                    precomputed[i][j] = j;
                }
            }
        }
    }

    /**
     * Evaluates RMQ(i, j) over the array stored by the constructor, returning
     * the index of the minimum value in that range.
     */
    @Override
    public int rmq(int i, int j) {
    	return precomputed[i][j];
    }
}
