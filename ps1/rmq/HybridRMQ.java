package rmq;
/**
 * An &lt;O(n), O(log n)&gt; implementation of the RMQ as a hybrid between
 * the sparse table (on top) and no-precomputation structure (on bottom)
 *
 * You will implement this class for problem 3.iii of Problem Set One.
 */
public class HybridRMQ implements RMQ {
    SparseTableRMQ upperLevel;
    int blockSize;
    float[] origElems;
    int[] origIndices;
    
    private int logBase2(int num) {
        return (int)(Math.log(num) / Math.log(2));
    }
    /**
     * Creates a new HybridRMQ structure to answer queries about the
     * array given by elems.
     *
     * @elems The array over which RMQ should be computed.
     */
    public HybridRMQ(float[] elems) {
        origElems = elems;
        if (elems.length <= 1) {
            return;
        }
        
        // Store the block size because we will use it a lot
        blockSize = logBase2(elems.length);
        // Store the original array because we will need it for bottom-level scans
        float[] topElems = new float[elems.length / blockSize + 1];
        origIndices = new int[elems.length / blockSize + 1];
        int block = 0;
        float min = elems[0];
        int minIndex = 0;
        for (int i = 1; i < elems.length; i++) {
            if (i % blockSize == 0) {
                topElems[block] = min;
                origIndices[block] = minIndex;
                block++;
                min = elems[i];
                minIndex = i;
            }
            else if (elems[i] < min) {
                min = elems[i];
                minIndex = i;
            }
        }
        topElems[block] = min;
        origIndices[block] = minIndex;
        upperLevel = new SparseTableRMQ(topElems);
    }
    
    private int linearScanRMQ(int i, int j) {
        int ind = i;
        float min = origElems[i];
        for (int k = i + 1; k <= j; k++) {
            if (origElems[k] < min) {
                min = origElems[k];
                ind = k;
            }
        }
        return ind;
    }

    /**
     * Evaluates RMQ(i, j) over the array stored by the constructor, returning
     * the index of the minimum value in that range.
     */
    @Override
    public int rmq(int i, int j) {
        if (i == j) {
            return i;
        }
        
        // Use integer division to round down
        int iBlock = i / blockSize;
        int jBlock = j / blockSize;
        
        // If i and j are in the same block or adjacent blocks
        if ((jBlock - iBlock) <= 1) {
            return linearScanRMQ(i, j);
        }
        else {
            int iBlockEnd = ((iBlock + 1) * blockSize) - 1;
            int jBlockStart = jBlock * blockSize;
            int iInd = linearScanRMQ(i, iBlockEnd);
            int jInd = linearScanRMQ(jBlockStart, j);
            
            float min;
            int minIndex;
            if (origElems[iInd] < origElems[jInd]) {
                min = origElems[iInd];
                minIndex = iInd;
            }
            else {
                min = origElems[jInd];
                minIndex = jInd;
            }
            
            int midInd = origIndices[upperLevel.rmq(iBlock + 1, jBlock - 1)];
            if (origElems[midInd] < min) {
                return midInd;
            }
            else {
                return minIndex;
            }
        }
    }
}
