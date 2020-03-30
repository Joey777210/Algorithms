package queues;


import java.util.Iterator;
import java.util.NoSuchElementException;

/*
    Dequeue. A double-ended queue or deque (pronounced “deck”)
    is a generalization of a stack and a queue that supports
    adding and removing items from either the front or the back of the data structure.
    Create a generic data type Deque that implements the following API:
 */

public class Deque<Item> implements Iterable<Item> {

    private  class Node {
        Item element;
        Node next = null;
        Node before = null;
    }

    private int size;
    private Node first;
    private Node last;

    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.element = item;
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            newNode.next = first;
            first.before = newNode;
            first = newNode;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.element = item;
        if (first == null) {
            last = newNode;
        } else {
            last.next = newNode;
            newNode.before = last;
            last = newNode;
            size++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node old = first;
        first = first.next;
        first.before = null;
        return old.element;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item i = last.element;
        last = last.before;
        last.next = null;
        return i;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.element;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("addFirst");
        deque.addLast("addLast");
        System.out.println(deque.size());
        System.out.println(deque.isEmpty());
        deque.removeLast();
        for (String s : deque) {
            System.out.println(s);
        }

    }
}