/**
 * An implementation of a priority queue backed by a lazy binomial heap. Each
 * binomial tree in this heap should be represented using the left-child/right-
 * sibling representation, and the binomial heaps should be stored in a doubly-
 * linked list (though not necessarily a circularly-linked list).
 *
 * For simplicity, we will not make a distinction between keys and values in
 * this priority queue.
 */
public class LazyBinomialHeap {

	MeldableLinkedList trees;
	LinkedListNode minNode;
	LCRSTree min;
	int size;

	/**
	 * Constructs a new, empty LazyBinomialHeap.
	 */
	public LazyBinomialHeap() {
		// TODO: Fill this in!
	}
	
	/**
	 * Returns whether the lazy binomial heap is empty.
	 *
	 * @return Whether this lazy binomial heap is empty.
	 */
	public boolean isEmpty() {
		// TODO: Fill this in!
		return (size == 0);
	}
	
	/**
	 * Adds the specified key to the priority queue. Duplicate values are
	 * allowed.
	 *
	 * @param key The key to add.
	 */
	public void enqueue(int key) {
		// TODO: Fill this in!
		LCRSTree newTree = new LCRSTree(key);
		LinkedListNode newNode = new LinkedListNode(newTree);
		trees.append(newNode);
		size++;
		if (min == null || newTree.getMinValue() < min.getMinValue()) {
			min = newTree;
			minNode = newNode;
		}
	}
	
	/**
	 * Returns the minimum key in the priority queue. This method can assume
	 * that the priority queue is not empty.
	 *
	 * @return The minimum key in the priority queue.
	 */
	public int min() {
		/* To enable assertions during testing, run java with the -ea flag:
		 *
		 *    java -ea NameOfMainClass
		 */
		assert !isEmpty() : "Priority queue is empty!";
		
		// TODO: Implement this!

		return min.getMinValue();
	}
	
	/**
	 * Removes and returns the minimum element of the priority queue. This
	 * method can assume that the priority queue is nonempty.
	 *
	 * @return The formed minimum element of the priority queue.
	 */
	public int extractMin() {
		assert !isEmpty() : "Priority queue is empty!";
		// Take the min val out of its binomial tree and meld its children
		// back into the list of trees
		int minVal = min.getMinValue();
		trees.remove(min);
		trees.meld(min.removeMin());
		size--;

		if (size == 0) {
			trees = new MeldableLinkedList();
			return minVal;
		}

		// Now coalesce the remaining trees
		double logBase2 = Math.log(size) / Math.log(2);
		LCRSTree[] wonky = new LCRSTree[(int)Math.ceil(logBase2)];

		LinkedListNode currNode = trees.root;
		while(currNode != null) {
			LCRSTree curr = (LCRSTree)currNode.payload;
			while (wonky[curr.order] != null) {
				// mergeEqual increments curr's order
				curr.mergeEqual(wonky[curr.order]);
				wonky[curr.order-1] = null;
			}
			wonky[curr.order] = curr;
			currNode = currNode.next;
		}

		// newMinVal will always be changed before we start using it for comparisons
		// This was necessary to satisfy the compiler
		int newMinVal = -1;
		boolean newMinValSet = false;
		MeldableLinkedList wonkyList = new MeldableLinkedList();
		for (int i = 0; i < wonky.length; ++i) {
			if (wonky[i] != null) {
				LinkedListNode newNode = new LinkedListNode(wonky[i]);
				if (!newMinValSet) {
					newMinValSet = true;
					newMinVal = wonky[i].getMinValue();
					min = wonky[i];
					minNode = newNode;
				}
				else {
					if (wonky[i].getMinValue() < newMinVal) {
						newMinVal = wonky[i].getMinValue();
						min = wonky[i];
						minNode = newNode;
					}
				}
				wonkyList.append(newNode);
			}
		}
		trees = wonkyList;
		// TODO: Implement this!
		return minVal;
	}
	
	/**
	 * Melds together the two input priority queues into a single priority
	 * queue. After this method is called on two priority queues, both of the
	 * input queues should not be used again in the future and any operations
	 * performed on them will have unspecified behavior.
	 *
	 * @param one The first queue to meld.
	 * @param two The second queue to meld.
	 * @return A queue consisting of all the keys in both input queues.
	 */
	public static LazyBinomialHeap meld(LazyBinomialHeap one,
	                                    LazyBinomialHeap two) {
	  // TODO: Implement this!
		LazyBinomialHeap newHeap = new LazyBinomialHeap();
		newHeap.size = one.size + two.size;
		if (one.min() <= two.min()) {
			newHeap.min = one.min;
			newHeap.minNode = one.minNode;
		}
		else {
			newHeap.min = two.min;
			newHeap.minNode = two.minNode;
		}

		one.trees.meld(two.trees);
		newHeap.trees = one.trees;

	  return newHeap;
  }
}
