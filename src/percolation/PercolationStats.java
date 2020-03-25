package percolation;

import com.sun.xml.internal.ws.encoding.MtomCodec;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

    private int n;
    private int trials;
    private double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        this.results = new double[trials];
        for(int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open((StdRandom.uniform(n)+1), (StdRandom.uniform(n)+1));
            }
            results[i] = p.numberOfOpenSites()/Math.pow(n,2);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return (mean() - (1.96 * stddev())/Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return (mean() + (1.96 * stddev())/Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args){
//        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        PercolationStats p = new PercolationStats(10, 10);
        System.out.printf("mean\t= %s\n", p.mean());
        System.out.printf("stddev\t= %s\n", p.stddev());
        System.out.printf("95%s confidence interval\t= [%s,%s]\n", "%", p.confidenceLo(), p.confidenceHi());
    }

}
