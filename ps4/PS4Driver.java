public class PS4Driver {
    
    public static void main(String[] args) {
        linkedListTest();
    }

    public static void linkedListTest() {
        LinkedListNode firstNode = new LinkedListNode(1);
        LinkedListNode secondNode = new LinkedListNode(2);
        LinkedListNode thirdNode = new LinkedListNode(3);

        //System.out.println(firstNode.toString());
        //System.out.println(secondNode.toString());
        //System.out.println(thirdNode.toString());

        MeldableLinkedList meldable = new MeldableLinkedList();
        System.out.println(meldable.toString());

        meldable.append(firstNode);
        System.out.println(meldable.toString());

        System.out.println("Second append:");
        meldable.append(secondNode);
        System.out.println(meldable.toString());

        meldable.append(thirdNode);
        System.out.println(meldable.toString());

        MeldableLinkedList meldable2 = new MeldableLinkedList();
        meldable2.append(new LinkedListNode(4));
        meldable2.append(new LinkedListNode(5));
        meldable2.append(new LinkedListNode(6));

        System.out.println("Second list looks like: \n" + meldable2.toString());

        meldable.meld(meldable2);

        System.out.println("Melded list looks like: \n" + meldable.toString());

        MeldableLinkedList emptyList = new MeldableLinkedList();
        meldable.meld(emptyList);

        System.out.println("Melded list looks like: \n" + meldable.toString());

        System.out.println("Attempting meld with empty list first");
        emptyList.meld(meldable2);

        System.out.println("Melded list with empty list first looks like: \n" + meldable2.toString());

        MeldableLinkedList shortList1 = new MeldableLinkedList();
        shortList1.append(new LinkedListNode(200));

        MeldableLinkedList shortList2 = new MeldableLinkedList();
        shortList2.append(new LinkedListNode(400));

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

}