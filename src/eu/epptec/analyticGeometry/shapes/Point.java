package eu.epptec.analyticGeometry.shapes;

import java.util.LinkedList;

public class Point implements Shape {
    private double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public Point move(double offsetX, double offsetY) {
        return new Point(x + offsetX, y + offsetY);
    }

    @Override
    public Point rotate(double angle) {
        return this;
    }

    @Override
    public Point rotate(double angle, Point pivot) {
        double angleSin = Math.sin(angle);
        double angleCos = Math.cos(angle);

        double xOut = x - pivot.getX();
        double yOut = y - pivot.getY();

        xOut += x * angleCos - y * angleSin;
        yOut += x * angleSin - y * angleCos;

        return new Point(x, y);
    }

    @Override
    public Point getCenter() {
        return this;
    }

    @Override
    public double getIntersectingArea(Shape intersectingShape) {
        return 0;
    }

    @Override
    public LinkedList<Point> getIntersectingPoints(Shape intersectingShape) {
        return null;
    }
}
