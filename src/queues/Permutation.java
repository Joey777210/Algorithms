package queues;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < num; i++) {
            System.out.println(rq.dequeue());
        }
    }
}