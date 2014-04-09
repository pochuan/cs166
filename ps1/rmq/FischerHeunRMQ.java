package rmq;
import java.util.Stack;
import java.util.Arrays;

/**
 * An &lt;O(n), O(1)&gt; implementation of the Fischer-Heun RMQ data structure.
 *
 * You will implement this class for problem 3.iv of Problem Set One.
 */
public class FischerHeunRMQ implements RMQ {

    SparseTableRMQ upperLevel;
    int origIdx[];
    int[] blockRMQIdx;
    PrecomputedRMQ[] blockRMQs;
    float[] origElems;
    int blockSize;
    /**
     * Creates a new FischerHeunRMQ structure to answer queries about the
     * array given by elems.
     *
     * @elems The array over which RMQ should be computed.
     */
    public FischerHeunRMQ(float[] elems) {
        origElems = elems;
        /*for (int i = 0; i < elems.length; i++) {
            System.out.print(elems[i] + " ");
        }
        System.out.println();
        */
        // TODO: Implement this!
        int n = elems.length;
        if (n == 0) {
            return;
        }
        int b = (int) Math.floor( (0.25) * ( Math.log(n) / Math.log(2) ) );
        if (b == 0) {
            b = 1;
        }
        
        int arrLength = (int)Math.pow(4,b);
        blockRMQIdx = new int[n/b+1];
        blockRMQs = new PrecomputedRMQ[arrLength];
        float[] blockMins = new float [n/b+1];
        origIdx = new int[n / b + 1];
        
        for (int i = 0; i < n; i+=b) {
            int currBlockSize = Math.min(b, n-i);
            float[] subArray = Arrays.copyOfRange(elems, i, i + currBlockSize);
            int cartNum = CartesianNumber(subArray);
            blockRMQIdx[i/b] = cartNum;
            
            if (blockRMQs[cartNum] == null) {
                blockRMQs[cartNum] = new PrecomputedRMQ(subArray);
            }
            
            int minIdx = blockRMQs[cartNum].rmq(0,currBlockSize-1);
            blockMins[i/b] = subArray[minIdx];
            origIdx[i/b] = i + minIdx;
        }
        
        upperLevel = new SparseTableRMQ(blockMins);
        blockSize = b;
        
    }

    /* Returning an int because Java does not allow arrays larger than the ones that we 
     * can index into with an int.  This can deal with n up to 2^64 when b = (1/4)log(n).
     */
    private int CartesianNumber(float[] elems) {
        int b = elems.length;
        int[] cartesianNum = new int[2*b];
        int idx = 0;
        Stack <Float> rightSpine = new Stack <Float>();
        
        float topNode = 0;
        for (int i = 0; i < b; ++i) {
            float curVal = elems[i];
            if (!rightSpine.empty()) topNode = rightSpine.peek();
                
            while (topNode > curVal && !rightSpine.empty() ) {
                rightSpine.pop();
                if (!rightSpine.empty()) topNode = rightSpine.peek();
                //System.out.println("Pop " + topNode);
                cartesianNum[idx] = 0; idx++;
            }
            rightSpine.push(curVal);
            //System.out.println("Push " + curVal);
            cartesianNum[idx] = 1; idx++;
        }
        while (idx < 2*b) {
            cartesianNum[idx] = 0; idx++;
        }
        int CN = 0;
        int one = 1;
        for (int i = 0; i < 2*b; ++i) {
            CN = CN << 1;
            if (cartesianNum[i] == 1) {
                CN = CN | one;
            } 
        }

        return CN;
    }

    private int getPrecomputedRMQ(int block, int i, int j) {
        int start = i - (block * blockSize);
        int end = j - (block * blockSize);
        return blockRMQs[blockRMQIdx[block]].rmq(start, end);
    }
    /**
     * Evaluates RMQ(i, j) over the array stored by the constructor, returning
     * the index of the minimum value in that range.
     */
    @Override
    public int rmq(int i, int j) {
       if (i == j) {
            //System.out.println("i = j, returning i");
            return i;
        }
        
        // Use integer division to round down
        int iBlock = i / blockSize;
        int jBlock = j / blockSize;
        int iBlockEnd = ((iBlock + 1) * blockSize) - 1;
        int jBlockStart = jBlock * blockSize;
        
        // If i and j are in the same block
        if (jBlock == iBlock) {
            //System.out.println("In same block");
            //System.out.println("i: " + 1 + ", j: " + j);
            return (iBlock * blockSize) + getPrecomputedRMQ(iBlock, i, j);
        }
        else if ((jBlock - iBlock) == 1) {  // Adjacent blocks
            //System.out.println("In adjacent blocks");
            int iInd = getPrecomputedRMQ(iBlock, i, iBlockEnd);
            int jInd = getPrecomputedRMQ(jBlock, jBlockStart, j);
            //System.out.println("iInd: " + iInd);
            //System.out.println("jInd: " + jInd);
            if (origElems[iBlock * blockSize + iInd] < origElems[jBlock * blockSize + jInd]) {
                return iBlock * blockSize + iInd;
            }
            else {
                return jBlock * blockSize + jInd;
            }
        }
        else {
            //System.out.println("Blocks separated");
            int iInd = getPrecomputedRMQ(iBlock, i, iBlockEnd);
            int jInd = getPrecomputedRMQ(jBlock, jBlockStart, j);
            
            float min;
            int minIndex;
            if (origElems[iBlock * blockSize + iInd] < origElems[jBlock * blockSize + jInd]) {
                min = origElems[iBlock * blockSize + iInd];
                minIndex = iBlock * blockSize + iInd;
            }
            else {
                min = origElems[jBlock * blockSize + jInd];
                minIndex = jBlock * blockSize + jInd;
            }
            
            int midInd = origIdx[upperLevel.rmq(iBlock + 1, jBlock - 1)];
            if (origElems[midInd] < min) {
                return midInd;
            }
            else {
                return minIndex;
            }
        }
    }
}
