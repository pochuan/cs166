/**
 * A doubly-linked list that supports O(1) concatenation. You are not required
 * to implement this class, but we strongly recommend doing so as a stepping
 * stone toward building the LazyBinomialHeap.
 */

public class MeldableLinkedList<T>{
	// TODO: Implement this class as you see fit!
    LinkedListNode<T> root;
    LinkedListNode<T> end;

    public MeldableLinkedList() {
        root = null;
        end = null;
    }

    public void append(LinkedListNode<T> newNode) {
        if (end == null) {
            root = newNode;
            end = newNode;
            //System.out.println("Append: end was null");
        }
        else {
            //System.out.println("Append: end was not null");
            end.next = newNode;
            newNode.prev = end;
            end = newNode;

            //System.out.println("Payload is " + end.payload);
            //System.out.println("Prev is " + end.prev);
        }
    }
    
    public boolean remove(T removeVal) {
        LinkedListNode<T> pointer = root;
        while (pointer != null) {
            if (pointer.payload == removeVal) {
                if (pointer.prev != null) {
                    pointer.prev.next = pointer.next;
                }
                else {
                    root = pointer.next;
                }
                if (pointer.next != null) {
                    pointer.next.prev = pointer.prev;
                }
                else {
                    end = pointer.prev;
                }
                return true;
            }
            pointer = pointer.next;
        }
        return false;
    }

    /* Undefined behavior if you give me a node that isn't actually
     * in this list! */
    public void removeNode(LinkedListNode<T> toRemove) {
        if (toRemove == null) {
            return;
        }
        if (toRemove == root) {
            root = toRemove.next;
        }
        else {
            toRemove.prev.next = toRemove.next;
        }

        if (toRemove == end) {
            end = toRemove.prev;
        }
        else {
            toRemove.next.prev = toRemove.prev;
        }
    }

   public void meld(MeldableLinkedList<T> list) {

        if (root == null) {
            root = list.root;
            end = list.end;
            return;
        }
        if (list.root != null) {
            end.next = list.root;
            list.root.prev = end;
        }
    }

    public LinkedListNode<T> find(LinkedListNode<T> target) {
        LinkedListNode<T> pointer = root;
        while (pointer != null) {
            if (pointer.payload == target.payload) {
                return pointer;
            }
            pointer = pointer.next;
        }
        return null;
    }

    public String toString() {
        LinkedListNode<T> pointer = root;
        String returnVal = "";
        while (pointer != null) {
            //System.out.println(pointer.toString());
            returnVal += " " + pointer.payload;
            returnVal += " [prev = " + pointer.prev;
            returnVal += ", next = " + pointer.next + "]\n";
            pointer = pointer.next;
        }
        return returnVal;
    }
}
