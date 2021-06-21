package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.Shape;

import java.util.*;
import java.util.Set;

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

        double xTmp = x - pivot.getX();
        double yTmp = y - pivot.getY();

        double xOut = xTmp * angleCos - yTmp * angleSin;
        double yOut = xTmp * angleSin + yTmp * angleCos;

        xOut += pivot.getX();
        yOut += pivot.getY();

        return new Point(xOut, yOut);
    }

    @Override
    public Point getCenter() {
        return this;
    }

    @Override
    public Set<Point> getIntersectingPoints(Shape other) {
        Set<Point> intersections = new HashSet<>();
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
    private Set<Point> getIntersectingPointsPoint(Point other) {
        Set<Point> intersections = new HashSet<>();
        if (abs(x - other.getX()) < EPS && abs(y - other.getY()) < EPS)
            intersections.add(this);
        return intersections;
    }

    // If the point lies on the line, they intersect
    private Set<Point> getIntersectingPointsLine(Line other) {
        Set<Point> intersections = new HashSet<>();
        List<Double> genEq = other.getGeneralEquation();
        if (abs(genEq.get(0) * x + genEq.get(1) * y + genEq.get(2)) < EPS && other.isInBoundary(this))
            intersections.add(this);
        return intersections;
    }

    // If the point lies on the circle, they intersect
    private Set<Point> getIntersectingPointsCircle(Circle other) {
        Set<Point> intersections = new HashSet<>();
        if (abs(pow(x - other.getCenter().getX(), 2) + pow(y - other.getCenter().getY(), 2) - pow(other.getRadius(), 2)) < EPS)
            intersections.add(this);
        return intersections;
    }

    @Override
    public String toString() {
        return "Point - [" + x + ", " + y + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Point))
            return false;
        Point otherPoint = (Point)other;
        return abs(x - otherPoint.getX()) < EPS && abs(y - otherPoint.getY()) < EPS;
    }
}
