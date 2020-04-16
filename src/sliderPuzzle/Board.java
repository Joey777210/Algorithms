package sliderPuzzle;

import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {
    private static int EMPTY = 0;
    private int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles.length < 2 || tiles.length > 128) {
            throw new IllegalArgumentException();
        }
        board = tiles;
    }

    // string representation of this board
    public String toString() {
        int len = dimension();
        StringBuilder str = new StringBuilder(len + "\n");
        for (int i = 0; i < len; i++) {
            str.append("\t");
            for (int j = 0; j < len; j++) {
                str.append(board[i][j]);
                str.append("\t");
            }
            str.append("\n");
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int len = dimension();
        int count = 0;
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= len; j++) {
                int target = board[i - 1][j - 1];
                if (target == EMPTY) {
                    continue;
                }
                if ((i - 1) * len + j != target) {
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distances = 0;
        int len = dimension();
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= len; j++) {
                int target = board[i - 1][j - 1];
                if (target == 0) {
                    continue;
                }
                int i1 = target / len + 1;
                int j1 = target % len;
                if (j1 == 0) {
                    i1 -= 1;
                    j1 = len;
                }
                int distance = Math.abs(i1 - i) + Math.abs(j1 - j);
                distances += distance;
            }
        }
        return distances;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            throw new IllegalArgumentException();
        }
        Board yb = (Board) y;
        int len = dimension();
        if (yb.dimension() != len || yb.hamming() != this.hamming() || yb.manhattan() != this.manhattan()) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (board[i][j] != yb.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int len = board.length;
        int empi = 0;
        int empj = 0;
        here:
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (board[i][j] == 0) {
                    empi = i;
                    empj = j;
                    break here;
                }
            }
        }
        List<Board> arrList = new ArrayList<>();
        if (empi - 1 >= 0) {
            int[][] b1 = swap(board, empi - 1, empj, empi, empj);
            arrList.add(new Board(b1));
        }
        if (empi + 1 < len) {
            int[][] b2 = swap(board, empi + 1, empj, empi, empj);
            arrList.add(new Board(b2));
        }
        if (empj - 1 >= 0) {
            int[][] b3 = swap(board, empi, empj - 1, empi, empj);
            arrList.add(new Board(b3));
        }
        if (empj + 1 < len) {

            int[][] b4 = swap(board, empi, empj + 1, empi, empj);
            arrList.add(new Board(b4));
        }
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    int count = 0;

                    @Override
                    public boolean hasNext() {
                        return count < arrList.size();
                    }

                    @Override
                    public Board next() {
                        return arrList.get(count++);
                    }
                };
            }
        };
    }

    /**
     * 交换两个数字
     *
     * @param board 传入刚刚
     * @param newi
     * @param newj
     * @param oldi
     * @param oldj
     * @return
     */
    private int[][] swap(int[][] board, int newi, int newj, int oldi, int oldj) {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int i = 0; i < newBoard.length; i++) {
            newBoard[i] = board[i].clone();
        }
        int temp = newBoard[newi][newj];
        newBoard[newi][newj] = newBoard[oldi][oldj];
        newBoard[oldi][oldj] = temp;
        return newBoard;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int i = 0; i < 2; i++) {
            if (board[i][0] != 0 && board[i][1] != 0) {
                return new Board(swap(board, i, 0, i, 1));
            }
        }
        throw new IllegalArgumentException();
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] board = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Board b = new Board(board);
        System.out.println(b.twin().toString());
    }

}
