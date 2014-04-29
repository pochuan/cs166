public class LinkedListNode<T> {
    LinkedListNode<T> prev;
    LinkedListNode<T> next;
    T payload;

    public LinkedListNode(T newPayload) {
        payload = newPayload;
        prev = null;
        next = null;
    }
}
