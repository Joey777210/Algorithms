package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setScale(0, 1);
        for (Point2D p : pointSet) {
            StdDraw.point(p.x(), p.y());
        }
        StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> points = new ArrayList<>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }
        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Point2D nearestPoint = pointSet.iterator().next();
        double minDistance = distance(nearestPoint, p);
        for (Point2D point : pointSet) {
            double dis = distance(point, p);
            if (minDistance > dis) {
                minDistance = dis;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    private double distance(Point2D p1, Point2D p2) {
        return Math.sqrt(Math.pow(p1.x()-p2.x(), 2) + Math.pow(p1.y()-p2.y(), 2));
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}