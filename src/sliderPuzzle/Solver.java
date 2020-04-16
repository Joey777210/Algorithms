package sliderPuzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Node goalNode;
    private int moves;

    public Solver(Board initial) {
        // Required by current API to detect infeasibility
        // using two synchronized A* searches
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> qp = new MinPQ<>();
        pq.insert(new Node(initial, 0, null));
        qp.insert(new Node(initial.twin(), 0, null));
        Node cur, kur;
        while (!pq.min().board.isGoal() && !qp.min().board.isGoal()) {
            cur = pq.delMin();
            kur = qp.delMin();
            for (Board nb : cur.board.neighbors()) {
                if (cur.prev == null || !nb.equals(cur.prev.board)) {
                    pq.insert(new Node(nb, cur.moves + 1, cur));
                }
            }
            for (Board nb : kur.board.neighbors()) {
                if (kur.prev == null || !nb.equals(kur.prev.board)) {
                    qp.insert(new Node(nb, kur.moves + 1, kur));
                }
            }
        }
        if (pq.min().board.isGoal()) {
            moves = pq.min().moves;
            goalNode = pq.min();
        } else {
            moves = -1;
        }
    }

    public boolean isSolvable() {
        return moves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> solution = new Stack<>();
        Node node = goalNode;
        while (node != null) {
            solution.push(node.board);
            node = node.prev;
        }
        return solution;
    }

    private class Node implements Comparable<Node> {

        private final Board board;
        private final int moves;
        private final Node prev;
        private int manhattanPriority = -1;

        Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        private int getManhattanPriority() {
            if (manhattanPriority == -1) {
                manhattanPriority = board.manhattan() + moves;
            }
            return manhattanPriority;
        }

        @Override
        public int compareTo(Node node) {
            return Integer.compare(this.getManhattanPriority(), node.getManhattanPriority());
        }
    }
    public static void main(String[] args) {
        int[][] board = {{1,2,3},{4,5,6},{7,8,0}};
        Board b = new Board(board);
        Solver s = new Solver(b);
//        for (Board value : s.solution()) {
//            System.out.println(value.toString());
//        }
        System.out.println(s.isSolvable());
    }

}