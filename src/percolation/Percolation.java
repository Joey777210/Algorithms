package percolation;

import java.util.Arrays;
import java.util.logging.Logger;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/*
Performance requirements.
The constructor should take time proportional to n2;
all methods should take constant time plus a constant number of calls to the union–find methods
union(), find(), connected(), and count().
 */
public class Percolation {

    private enum status{
        BLOCKED, OPEN, FULL;
    }

    private grid[] percolation;
    private int openedNum = 0;
    private int edge;

    private static class grid{
        private status status = Percolation.status.BLOCKED;
        private int sz = 0;
        private int father;
        //tb = 2  top; tb = 1  bottom
        private int tb= 0;
    }


    // creates n-by-n grid, with all sites initially blocked
    // n begins with 1
    // 创建一个n乘n的网格，所有的方块都被初始化为阻塞的
    public Percolation(int n){
        if (n <= 0){
            throw new IllegalArgumentException("n is lower than 0");
        }
        //init grid
        edge = n;
        percolation = new grid[n*n];
        for (int i = 0; i < edge*edge; i++){
            percolation[i] = new grid();
            percolation[i].father = i;
        }
        System.out.printf("New %d*%d percolation\n", n, n);

        //init top and bottom
        for (int i = 0; i < edge; i++) {
//            union(i, i+1);
            percolation[i].tb = 2;
        }

        for (int i = edge*(edge-1); i < edge*edge; i++){
//            union(i, i+1);
            percolation[i].tb = 1;
        }
//        System.out.println("Init percolation top and bottom");
    }

    // opens the site (row, col) if it is not open already
    // row and col begin with 1
    //打开(行，列)位置的方格，如果它之前未被打开的话
    public void open(int row, int col){
//        System.out.printf("(%d,%d) opening\n", row, col);
        check(row, col);
        if (isOpen(row, col)) {
//            System.out.printf("(%d,%d) was opened\n", row, col);
            return;
        }
        int locate = locate(row, col);
        percolation[locate].status = status.OPEN;
        openedNum++;
        if (row-1 > 0){
            if (isOpen(row-1, col)){
                union(locate, locate(row-1, col));
            }
        }
        if (row + 1 <= edge){
            if (isOpen(row+1, col)){
                union(locate, locate(row+1, col));
            }
        }
        if (col -1 > 0){
            if (isOpen(row, col-1)){
                union(locate, locate(row, col-1));
            }
        }
        if (col +1 <= edge){
            if (isOpen(row, col+1)){
                union(locate, locate(row, col+1));
            }
        }
//        System.out.printf("(%d,%d) is open now\n", row, col);
    }

    // is the site (row, col) open?
    //(行，列)处的方格是否开着
    public boolean isOpen(int row, int col){
        check(row, col);
        status s = percolation[locate(row, col)].status;
        if(s.equals(status.OPEN) || s.equals(status.FULL)){
            return true;
        }else {
            return false;
        }
    }

    // is the site (row, col) full?
    //(行，列)处的方格是否已经被渗透填满了
    public boolean isFull(int row, int col){
        check(row, col);
        status s = percolation[locate(row, col)].status;
        if(s.equals(status.FULL)){
            return true;
        }else {
            return false;
        }
    }

    // returns the number of open sites
    //已经打开的方格数量
    public int numberOfOpenSites(){
        return openedNum;
    }

    // does the system percolate?
    // 系统是否已经渗透
    public boolean percolates(){
        for (int i = 1; i <= edge; i++){
            if (isFull(edge, i)){
                return true;
            }
        }
        return false;
    }

    //locate grid in array
    private int locate(int row, int col){
        return (row-1)*edge + col-1;
    }

    //check corn case
    private void check(int row, int col)throws IllegalArgumentException{
        if (row > edge || row <= 0 || col > edge || col <= 0) {
            System.out.println(row + ":"+ col);
            throw new IllegalArgumentException();
        }
    }

    private int root(int p) {
        while (percolation[p].father != p){
            p = percolation[p].father;
        }
        return p;
    }

    private void union(int p, int q) {
        int r1 = root(p);
        int r2 = root(q);

        if (connected(p, q)){
            // already union, do nothing
        } else if (percolation[r1].sz > percolation[r2].sz){
            linkTB(r1, r2);
            percolation[r2].father = r1;
            percolation[r1].sz += percolation[r2].sz;
        } else {
            linkTB(r1, r2);
            percolation[r1].father = r2;
            percolation[r2].sz += percolation[r1].sz;
        }

    }

    private void linkTB(int r1, int r2){
        if (percolation[r1].tb > percolation[r2].tb){
            percolation[r2].tb = percolation[r1].tb;
            if (percolation[r1].tb == 2 || percolation[r2].tb == 2){
                percolation[r1].status = status.FULL;
                percolation[r2].status = status.FULL;
            }
        }else {
            percolation[r1].tb = percolation[r2].tb;
            if (percolation[r1].tb == 2 || percolation[r2].tb == 2){
                percolation[r1].status = status.FULL;
                percolation[r2].status = status.FULL;
            }

        }
    }

    private boolean connected(int p, int q){
        return root(p) == root (q);
    }

    // test client (optional)
    public static void main(String[] args){
        int n = 20;
        int i = 50;
        while(i > 0) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open((StdRandom.uniform(n)+1), (StdRandom.uniform(n)+1));
            }
            System.out.printf("Percolates when %d grids is open\n", p.numberOfOpenSites());
            i--;
        }
    }
}

