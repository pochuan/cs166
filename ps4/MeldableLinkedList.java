/**
 * A doubly-linked list that supports O(1) concatenation. You are not required
 * to implement this class, but we strongly recommend doing so as a stepping
 * stone toward building the LazyBinomialHeap.
 */

public class linkedListNode {
    linkedListNode prev;
    linkedListNode next;
    int payload;

    public linkedListNode(int newPayload) {
        payload = newPayload;
        prev = NULL;
        next = NULL;
    }
}

public class MeldableLinkedList {
	// TODO: Implement this class as you see fit!
    linkedListNode root;
    linkedListNode end;

    public MeldableLinkedList() {
        root = NULL;
        end = NULL;
    }

    public void append(linkedListNode newNode) {
        if (end == NULL) {
            root = newNode;
            end = newNode;
        }
        else {
            end.next = newNode;
            newNode.prev = end;
            end = newNode;
        }
    }
    
    public boolean remove(linkedListNode removeNode) {
        linkedListNode pointer = root;
        while (pointer != NULL) {
            if (pointer.payload == removeNode.payload) {
                if (pointer.prev != NULL) {
                    pointer.prev.next = pointer.next;
                }
                if (pointer.next != NULL) {
                    pointer.next.prev = pointer.prev;
                }
                return true;
            }
            pointer = pointer.next;
        }
        return false;
    }

   public void meld(MeldableLinkedList list) {
        if (root == NULL) {
            root = list.root;
            end = list.end;
        }
        if (list.root != NULL) {
            end.next = list.root;
            list.root.prev = end;
        }
    }

    public linkedListNode find(linkedListNode target) {
        linkedListNode pointer = root;
        while (pointer != NULL) {
            if (pointer.payload == target.payload) {
                return pointer;
            }
            pointer = pointer.next;
        }
        return NULL;
    }

    public string toString() {
        linkedListNode pointer = root;
        String returnVal;
        while (pointer != NULL) {
            returnVal += " " + pointer.payload;
            returnVal += " [prev = " + pointer.prev;
            returnVal += " next = " + pointer.next + "]";
            pointer = pointer.next;
        }
        return returnVal;
    }
}
