package me.nether.polinvaders.utils;

import java.awt.geom.Point2D;

public class MathUtils {

    public static double range(double start, double finish) {
        if (start > finish) {
            double temp = start;
            start = finish;
            finish = temp;
        }

        double difference = finish - start;
        return start + Math.random() * difference;
    }

    public static int[] rotate(double theta, double xIn, double yIn, double xC, double yC) {
        double newX = xC + (Math.cos(theta) * (xIn - xC) - Math.sin(theta) * (yIn - yC));
        double newY = yC + (Math.sin(theta) * (xIn - xC) + Math.cos(theta) * (yIn - yC));

        return new int[]{(int) newX, (int) newY};
    }

    public static Point2D rotatePoint(double theta, Point2D point2D, double xC, double yC) {
        double newX = xC + (Math.cos(theta) * (point2D.getX() - xC) - Math.sin(theta) * (point2D.getY() - yC));
        double newY = yC + (Math.sin(theta) * (point2D.getX() - xC) + Math.cos(theta) * (point2D.getY() - yC));

        return new Point2D.Double(newX, newY);
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double map(double x, double oldStart, double oldFinish, double newStart, double newFinish) {
        return (x - oldStart) / (oldFinish - oldStart) * (newFinish - newStart) + newStart;
    }

}
