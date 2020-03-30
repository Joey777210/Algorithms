package queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
    }

    private int size;
    private Node first;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.item = item;
        if (first == null) {
            first = newNode;
        } else {
            newNode.next = first;
            first = newNode;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            Item item = first.item;
            first = null;
            size--;
            return item;
        }
        int random = StdRandom.uniform(size);
        Node ptr = get(random - 1);
        Item item = ptr.next.item;
        ptr.next = ptr.next.next;
        size--;
        return item;
    }

    private Node get(int n) {
        Node ptr = first;
        for (int i = 0; i < n; i++) {
            ptr = ptr.next;
        }
        return ptr;
    }
    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(size);
        Node ptr = get(random);
        return ptr.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandmonizedQueueIterator();
    }

    private class RandmonizedQueueIterator implements Iterator<Item> {
        int[] arr = new int[size()];
        int count = 0;

        @Override
        public boolean hasNext() {
            return count != size();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int random = StdRandom.uniform(size());
            while (arr[random] == 1) {
                random = StdRandom.uniform(size());
            }
            Node node = get(random);
            count++;
            arr[random] = 1;
            return node.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("First");
        rq.enqueue("Second");
        rq.enqueue("Third");
        rq.enqueue("Forth");
        rq.enqueue("Fifth");
        rq.enqueue("Sixth");
        rq.enqueue("Seventh");
        Iterator<String> iterator = rq.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}