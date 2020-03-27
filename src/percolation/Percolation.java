package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {

    private final WeightedQuickUnionUF uf;

    private enum Status {
        BLOCK, OPEN, FULL
    }

    private Status[] statuses;
    private int numOpen = 0;
    private final int numEdge;
    private boolean isPercolate = false;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n is lower or equal to 0");
        }
        uf = new WeightedQuickUnionUF(n * n);
        // add 1 as a flag  isPercolate
        statuses = new Status[n * n];
        for (int i = 0; i < statuses.length; i++) {
            statuses[i] = Status.BLOCK;
        }
        int flag = locate(n, 1);
        for (int i = 2; i < n; i++) {
            uf.union(locate(n, i), flag);
        }
        numEdge = n;
    }

    // locate grid in array
    // row begin with 1, col begin with 1
    private int locate(int row, int col) {
        return (row - 1) * numEdge + col - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        check(row, col);
        if (isOpen(row, col)) {
            row = StdRandom.uniform(numEdge)+1;
            col = StdRandom.uniform(numEdge)+1;
            open(row, col);
            return;
        }
        int locate = locate(row, col);
        if (statuses[locate] != Status.BLOCK) {
            return;
        } else {
            if (row == 1) {
                statuses[locate] = Status.FULL;
            } else {
                statuses[locate] = Status.OPEN;
            }
            checkNeighbor(row, col);
        }
        numOpen++;
    }

    private void checkNeighbor(int row, int col) {
        int locate = locate(row, col);
        neighbor(row - 1, col, locate);
        neighbor(row + 1, col, locate);
        neighbor(row, col - 1, locate);
        neighbor(row, col + 1, locate);
    }

    private void neighbor(int row, int col, int locate) {
        if (row <= 0 || col <= 0 || row > numEdge || col > numEdge) {
            return;
        }
        if (isOpen(row, col) || isFull(row, col)) {
            int neighbor = locate(row, col);
            int root1 = uf.find(neighbor);
            int root2 = uf.find(locate);

            uf.union(locate, neighbor);
            if (statuses[root1] == Status.FULL || statuses[root2] == Status.FULL) {
                statuses[uf.find(root1)] = Status.FULL;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        check(row, col);
        return statuses[locate(row, col)] == Status.OPEN || isFull(row, col);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        check(row, col);
        return statuses[uf.find(locate(row, col))] == Status.FULL;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return statuses[uf.find(locate(numEdge, 1))] == Status.FULL;
    }

    // check corn case
    private void check(int row, int col) {
        if (row > numEdge || row <= 0 || col > numEdge || col <= 0) {
            throw new IllegalArgumentException();
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();
        int n = 5;
        int i = 50;
        while (i > 0) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
                System.out.printf("Percolates when %d grids is open\n", p.numberOfOpenSites());
            i--;
        }
        System.out.println(sw.elapsedTime());


//        int n = 2;
//        Percolation p = new Percolation(n);
////        for (int i = 1; i < 6; i++){
//            p.open(1, 1);
//            p.open(2, 2);
//            p.open(1, 1);
//            System.out.println(p.numberOfOpenSites());
//            System.out.println(p.percolates());

    }
}
