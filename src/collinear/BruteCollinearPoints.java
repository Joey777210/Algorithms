package collinear;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validate(points);
        final int numOfPoints = points.length;

        // iterate through every four elements
        for (int i = 0; i < numOfPoints - 3; i++) {
            for (int j = i + 1; j < numOfPoints - 2; j++) {
                for (int k = j + 1; k < numOfPoints - 1; k++) {
                    for (int l = k + 1; l < numOfPoints; l++) {
                        // the fourth points are collinear
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
                            Point[] segment = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(segment);
                            lineSegments.add(new LineSegment(segment[0], segment[3]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[numberOfSegments()]);
    }

    private static void validate(Point[] points) {
        if (points == null) { throw new IllegalArgumentException(); }

        for (Point point : points) {
            if (point == null) { throw new IllegalArgumentException(); }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if ((points[j].equals(points[i]))) { throw new IllegalArgumentException(); }
            }
        }
    }

    public static void main(String[] args) {
        Point[] points = new Point[] {
                new Point(1,5),
                new Point(43,25),
                new Point(2,5),
                new Point(10000, 0),
                new Point(7000, 3000),
                new Point(7000, 3000),
                new Point(7000, 3000),
                new Point(3000,4000),
                new Point(6000,7000),
                new Point(14000,15000),
                new Point(20000,21000),

        };
        BruteCollinearPoints b = new BruteCollinearPoints(points);
        System.out.println(b.numberOfSegments());
        System.out.println(Arrays.toString(b.segments()));
    }
}
