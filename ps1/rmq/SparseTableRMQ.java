package rmq;
/**
 * An &lt;O(n log n), O(1)&gt; implementation of RMQ that uses a sparse table
 * to do lookups efficiently.
 *
 * You will implement this class for problem 3.ii of Problem Set One.
 */
public class SparseTableRMQ implements RMQ {
    int[][] sparseTable;
    int[] fastLogs;
    float[] origElems;
    
    /**
     * Creates a new SparseTableRMQ structure to answer queries about the
     * array given by elems.
     *
     * @elems The array over which RMQ should be computed.
     */
    public SparseTableRMQ(float[] elems) {
        origElems = elems;
        
        int length = elems.length;
        populateFastLogs(length);
        
        // Based on pseudocode in the topcoder tutorial at
        // http://www.topcoder.com/tc?d1=tutorials&d2=lowestCommonAncestor&module=Static
        sparseTable = new int[length][fastLogs[length] + 1];
        // Initialize for the intervals with length 1
        for (int i = 0; i < length; i++) {
            sparseTable[i][0] = i;
        }
        // Compute values from smaller to bigger intervals
        for (int j = 1; (1 << j) <= length; j++) {
            for (int i = 0; (i + (1 << j) - 1) < length; i++) {
                int sparse1 = sparseTable[i][j - 1];
                int sparse2 = sparseTable[i + (1 << (j - 1))][j - 1];
                if (elems[sparse1] < elems[sparse2]) {
                    sparseTable[i][j] = sparse1;
                }
                else {
                    sparseTable[i][j] = sparse2;
                }
            }
        }        
    }
    
    private void populateFastLogs(int length) {
        fastLogs = new int[length + 1];
        int counter = 0;
        int log = 0;
        int num = 1;
        
        fastLogs[0] = 0;
        
        for (int i = 1; i < fastLogs.length; i++) {
            counter++;
            fastLogs[i] = log;
            if (counter == num) {
                log++;
                num *= 2;
                counter = 0;
            }
        }
    }

    /**
     * Evaluates RMQ(i, j) over the array stored by the constructor, returning
     * the index of the minimum value in that range.
     */
    @Override
    public int rmq(int i, int j) {
        int log = fastLogs[j - i + 1];
        int sparse1 = sparseTable[i][log];
        int sparse2 = sparseTable[j - (int)Math.pow(2, log) + 1][log];
        if (origElems[sparse1] < origElems[sparse2]) {
            return sparse1;
        }
        return sparse2;
    }
}
