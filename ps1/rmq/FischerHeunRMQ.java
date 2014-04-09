package rmq;
import java.util.Stack;

/**
 * An &lt;O(n), O(1)&gt; implementation of the Fischer-Heun RMQ data structure.
 *
 * You will implement this class for problem 3.iv of Problem Set One.
 */
public class FischerHeunRMQ implements RMQ {
    /**
     * Creates a new FischerHeunRMQ structure to answer queries about the
     * array given by elems.
     *
     * @elems The array over which RMQ should be computed.
     */
    public FischerHeunRMQ(float[] elems) {
        // TODO: Implement this!
        int n = elems.length;
        int b = (int) Math.floor( (0.25) * ( Math.log(n) / Math.log(2) ) );
        
    }


    private int[] CartesianNumber(float[] elems) {
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
        return cartesianNum;
    }

    /**
     * Evaluates RMQ(i, j) over the array stored by the constructor, returning
     * the index of the minimum value in that range.
     */
    @Override
    public int rmq(int i, int j) {
        // TODO: Implement this!
        System.out.println("Running Fischer Heun!");
        /*
        float[] test = {32, 45, 16, 18, 9, 33};
        float[] test2 = {27, 18, 28, 18, 28, 45, 90, 45, 23, 53, 60, 28, 74, 71, 35};
        int [] cn = CartesianNumber(test2);
        for (int ii = 0; ii < cn.length; ++ii) {
            System.out.print(cn[ii]);
        }
        */
        return -1;
    }
}
