public class LinkedListNode {
    LinkedListNode prev;
    LinkedListNode next;
    int payload;

    public LinkedListNode(int newPayload) {
        payload = newPayload;
        prev = null;
        next = null;
    }

    /*public String toString() {
        String returnVal = "" + payload;
        returnVal += " [prev = " + prev;
        returnVal += ", next = " + next + "]";

        return returnVal;
    }*/
}
