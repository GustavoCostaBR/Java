import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//import java.util.Scanner;

//import Percolation;
public class PercolationStats {
    private Percolation a[];
    private double mean;
    private double stddev;
    private int n;
    private double tries[];

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("Invalid size");
        }
        
        this.n = n;
        this.tries = new double[trials];
        this.stddev = 0;
        int count_ = 0;
        this.mean = 0;
        int row;
        int col;
        boolean percolates_ = false;
        this.a = new Percolation[trials];
        while (count_ < trials) {
            this.a[count_] = new Percolation(n);
            while (percolates_ == false) {
                row = (StdRandom.uniformInt(n) + 1);
                col = (StdRandom.uniformInt(n) + 1);
                this.a[count_].open(row, col);
                percolates_ = this.a[count_].percolates();
            }
            percolates_ = false;
            count_++;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        long count_ = 0;
        double totalOpen = 0;
        for (Percolation component : this.a) {
            this.tries[(int) count_] = ((double) component.numberOfOpenSites()) / ((double) (this.n * this.n));
            totalOpen = totalOpen + this.tries[(int) count_];
            count_++;
        }
        this.mean = (totalOpen / (double) count_);
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        mean();
        this.stddev = StdStats.stddev(this.tries);
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        mean();
        stddev();
        return (this.mean - (1.96 * this.stddev) / Math.sqrt((double) this.tries.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        mean();
        stddev();
        return (this.mean + (1.96 * this.stddev) / Math.sqrt((double) this.tries.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int try_ = Integer.parseInt(args[1]);
        PercolationStats b = new PercolationStats(n, try_);
        double mean = b.mean();
        double devpad = b.stddev();
        System.out.println("mean = " + mean);
        System.out.println("stddev = " + devpad);
        System.out.println("95% confidence interval = [" + b.confidenceLo() + ", " + b.confidenceHi() + "]");
    }
}