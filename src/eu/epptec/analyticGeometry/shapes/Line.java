package eu.epptec.analyticGeometry.shapes;

import java.util.LinkedList;

public class Line implements Shape {
    private Point a, b;

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public double getLength() {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    public Point getUnitVector() {
        double xVec = (a.getX() - b.getX()) / this.getLength();
        double yVec = (a.getY() - b.getY()) / this.getLength();
        return new Point(xVec, yVec);
    }

    @Override
    public Point getCenter() {
        return new Point((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
    }

    @Override
    public Line move(double x, double y) {
        return new Line(a.move(x, y), b.move(x, y));
    }

    @Override
    public Line rotate(double angle) {
        return this.rotate(angle, this.getCenter());
    }

    @Override
    public Line rotate(double angle, Point pivot) {
        return new Line(a.rotate(angle, pivot), b.rotate(angle, pivot));
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
