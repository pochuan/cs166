public class PS4Driver {
    
    public static void main(String[] args) {
        //linkedListTest();
        HeapTest();
    }

    public static void linkedListTest() {
        LinkedListNode<Integer> firstNode = new LinkedListNode<Integer>(1);
        LinkedListNode<Integer> secondNode = new LinkedListNode<Integer>(2);
        LinkedListNode<Integer> thirdNode = new LinkedListNode<Integer>(3);

        //System.out.println(firstNode.toString());
        //System.out.println(secondNode.toString());
        //System.out.println(thirdNode.toString());

        MeldableLinkedList<Integer> meldable = new MeldableLinkedList<Integer>();
        System.out.println(meldable.toString());

        meldable.append(firstNode);
        System.out.println(meldable.toString());

        System.out.println("Second append:");
        meldable.append(secondNode);
        System.out.println(meldable.toString());

        meldable.append(thirdNode);
        System.out.println(meldable.toString());

        MeldableLinkedList<Integer> meldable2 = new MeldableLinkedList<Integer>();
        meldable2.append(new LinkedListNode<Integer>(4));
        meldable2.append(new LinkedListNode<Integer>(5));
        meldable2.append(new LinkedListNode<Integer>(6));

        System.out.println("Second list looks like: \n" + meldable2.toString());

        meldable.meld(meldable2);

        System.out.println("Melded list looks like: \n" + meldable.toString());

        MeldableLinkedList<Integer> emptyList = new MeldableLinkedList<Integer>();
        meldable.meld(emptyList);

        System.out.println("Melded list looks like: \n" + meldable.toString());

        System.out.println("Attempting meld with empty list first");
        emptyList.meld(meldable2);

        System.out.println("Melded list with empty list first looks like: \n" + meldable2.toString());

        MeldableLinkedList<Integer> shortList1 = new MeldableLinkedList<Integer>();
        shortList1.append(new LinkedListNode<Integer>(200));

        MeldableLinkedList<Integer> shortList2 = new MeldableLinkedList<Integer>();
        shortList2.append(new LinkedListNode<Integer>(400));

        shortList1.meld(shortList2);
        System.out.println("Meld of two short lists looks like: \n" + shortList1.toString());

        System.out.println("\nTime to remove!");

        System.out.println("\nRemove from the middle");
        meldable.remove(2);
        System.out.println("List looks like: \n" + meldable.toString());

        System.out.println("\nNow remove the front");
        meldable.remove(1);
        System.out.println("List looks like: \n" + meldable.toString());

        System.out.println("\nNow remove the back");
        meldable.remove(6);
        System.out.println("List looks like: \n" + meldable.toString());
    }

    public static void HeapTest() {
        System.out.println("Starting to test the LazyBinomialHeap\n");
        LazyBinomialHeap pq1 = new LazyBinomialHeap();

        System.out.println("****The next section relies on asserts.  No text is good text\n");
        assert pq1.isEmpty() : "New lazy heap was not empty";

        pq1.enqueue(3);
        pq1.enqueue(500);
        pq1.enqueue(700);

        assert (pq1.min() == 3) : "Min was not 3";

        pq1.enqueue(2);
        assert (pq1.min() == 2) : "Min was not 2";

        pq1.enqueue(0);
        assert (pq1.min() == 0) : "Min was not 0";

        pq1.enqueue(-1);
        assert (pq1.min() == -1) : "Negative min failed";

        assert (pq1.extractMin() == -1) : "extractMin failed";

        assert (pq1.min() == 0) : "Min is wrong after extractMin";

        // Extract all of the values
        boolean extractSuccess = (pq1.extractMin() == 0) && (pq1.extractMin() == 2) &&
                (pq1.extractMin() == 3) && (pq1.extractMin() == 500) && (pq1.extractMin() == 700);

        assert extractSuccess : "Something when wrong while removing all of the mins";
        
        assert pq1.isEmpty() : "PQ was not empty after extracting all of the elements";        

        heapMeldTest();
        


        System.out.println("\nDone testing the LazyBinomialHeap");

    }

    public static void heapMeldTest() {
        LazyBinomialHeap pq1 = new LazyBinomialHeap();

        LazyBinomialHeap pq2 = new LazyBinomialHeap();

        pq1.enqueue(10);
        pq2.enqueue(11);

        LazyBinomialHeap pq3 = LazyBinomialHeap.meld(pq1, pq2);

        assert (pq3.extractMin() == 10) && (pq3.extractMin() == 11) : "Meld of two length-one heaps was successful"; 


        pq1 = new LazyBinomialHeap();
        pq2 = new LazyBinomialHeap();

        pq1.enqueue(2);
        pq1.enqueue(4);
        pq1.enqueue(6);
        pq1.enqueue(8);
        pq1.enqueue(10);
        pq1.enqueue(12);

        pq2.enqueue(13);
        pq2.enqueue(11);
        pq2.enqueue(9);
        pq2.enqueue(7);
        pq2.enqueue(5);
        pq2.enqueue(3);

        pq3 = LazyBinomialHeap.meld(pq1, pq2);

        assert (pq3.extractMin() == 2) : "Melding a larger heap worked";
        System.out.println("\n****You'll have to check the rest of the results by hand");


        System.out.println("Just make sure that the numbers below are in order, and that 5 appears twice");
        pq3.enqueue(100);
        pq3.enqueue(2);
        pq3.enqueue(15);
        pq3.enqueue(5);
        while(!pq3.isEmpty()) {
            System.out.println(pq3.extractMin());
        }

    }

}