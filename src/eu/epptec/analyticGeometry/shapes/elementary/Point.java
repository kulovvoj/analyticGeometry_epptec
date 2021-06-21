package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.basic.Circle;
import eu.epptec.analyticGeometry.shapes.basic.Line;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Point implements Shape {
    private double x, y;

    public Point(double x, double y) {
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

        return new Point(xOut, yOut);
    }

    @Override
    public Point getCenter() {
        return this;
    }

    @Override
    public List<Point> getIntersectingPoints(Shape other) {
        List<Point> intersections = new LinkedList<>();
        if (other instanceof Point)
            intersections.addAll(getIntersectingPointsPoint((Point)other));
        else if (other instanceof Line)
            intersections.addAll(getIntersectingPointsLine((Line)other));
        else if (other instanceof Circle)
            intersections.addAll(getIntersectingPointsCircle((Circle)other));
        else
            intersections.addAll(other.getIntersectingPoints(this));
        return intersections;
    }

    // If the points have the same coordinates, they intersect
    private List<Point> getIntersectingPointsPoint(Point other) {
        List<Point> intersections = new LinkedList<>();
        if (abs(x - other.getX()) < EPS && abs(y - other.getY()) < EPS)
            intersections.add(this);
        return intersections;
    }

    // If the point lies on the line, they intersect
    private List<Point> getIntersectingPointsLine(Line other) {
        List<Point> intersections = new LinkedList<>();
        List<Double> genEq = other.getGeneralEquation();
        if (abs(genEq.get(0) * x + genEq.get(1) * y + genEq.get(2)) < EPS && other.isInBoundary(this))
            intersections.add(this);
        return intersections;
    }

    // If the point lies on the circle, they intersect
    private List<Point> getIntersectingPointsCircle(Circle other) {
        List<Point> intersections = new LinkedList<>();
        if (abs(pow(x - other.getCenter().getX(), 2) + pow(y - other.getCenter().getY(), 2) - pow(other.getRadius(), 2)) < EPS)
            intersections.add(this);
        return intersections;
    }

    @Override
    public String toString() {
        return "Point - [" + x + ", " + y + "]";
    }
}
