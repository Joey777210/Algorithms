package queues;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        String input = StdIn.readString();
        String[] inputs = input.split(" ");
        int n = Integer.parseInt(inputs[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 1; i < inputs.length; i++) {
            rq.enqueue(inputs[i]);
        }

        for (int i = 1; i < n; i++) {
            System.out.println(rq.dequeue());
        }
    }
}