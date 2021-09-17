public class ALinkyList {

    private Node head = null;
    private Node tail = null;

    class Node {
        private Node next = null;
        private Shipment ship;

        public Node(Shipment ship) {
            this.ship = ship;
        }

        public Shipment getShipment() {
            return ship;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setAmountInShipment(int amount) {
            ship.setAmount(amount);
        }

        @Override
        public String toString() {
            return this.ship.toString();
        }
    }

    /// Checks to see if List is empty

    public boolean isEmpty() {
        return head == null;
    }

    /// Inserts Node in rear of list, we're treating this as a Queue therefore we
    /// add
    /// to the end of the list and remove from front

    public void insertNodeAtRear(Shipment obj) {
        Node newNode = new Node(obj);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;

    }

    // removed the node off front of List by moving the head pointer

    public void removeNodeFromFront() {
        if (isEmpty()) {
            System.out.println("The List is Empty\n");
        } else if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
        }
    }

    // returns the info in the node at the head

    public Shipment peek() {
        if (isEmpty()) {
            System.out.println("The List is Empty\n");
            return null;
        }
        return head.getShipment();
    }

    // modifies the information (specifically the amount) in the head node, but does
    // not remove the node

    public void modifyHead(int amount) {
        head.setAmountInShipment(amount);
    }

}
