package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.Shape;

import java.util.*;
import java.util.List;

import static java.lang.Math.*;

public class Point implements BasicShape {
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
    public Point move(double offListX, double offListY) {
        return new Point(x + offListX, y + offListY);
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
    public Set<BasicShape> getIntersections(Shape other) {
        Set<BasicShape> intersections = new TreeSet<>();
        if (other instanceof Point)
            intersections.addAll(getIntersectionsPoint((Point)other));
        else if (other instanceof Line)
            intersections.addAll(getIntersectionsLine((Line)other));
        else if (other instanceof Circle)
            intersections.addAll(getIntersectionsCircle((Circle)other));
        else
            intersections.addAll(other.getIntersections(this));
        return intersections;
    }

    // If the points have the same coordinates, they intersect
    private Set<BasicShape> getIntersectionsPoint(Point other) {
        Set<BasicShape> intersections = new TreeSet<>();
        if (abs(x - other.getX()) < EPS && abs(y - other.getY()) < EPS)
            intersections.add(this);
        return intersections;
    }

    // If the point lies on the line, they intersect
    private Set<BasicShape> getIntersectionsLine(Line other) {
        Set<BasicShape> intersections = new TreeSet<>();
        List<Double> genEq = other.getGeneralEquation();
        if (abs(genEq.get(0) * x + genEq.get(1) * y + genEq.get(2)) < EPS && other.isInBoundary(this))
            intersections.add(this);
        return intersections;
    }

    // If the point lies on the circle, they intersect
    private Set<BasicShape> getIntersectionsCircle(Circle other) {
        Set<BasicShape> intersections = new TreeSet<>();
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
        return round(x * compPrec) == round(otherPoint.getX() * compPrec) &&
                round(y * compPrec) == round(otherPoint.getY() * compPrec);
        //return abs(x - otherPoint.getX()) < EPS && abs(y - otherPoint.getY()) < EPS;
    }

    @Override
    public int compareTo(BasicShape other) {
        if (other instanceof Circle || other instanceof Line)
            return -1;
        Point otherPoint = (Point)other;
        long thisXInt = round(x * compPrec);
        long thisYInt = round(y * compPrec);
        long otherXInt = round(otherPoint.getX() * compPrec);
        long otherYInt = round(otherPoint.getY() * compPrec);
        if (thisXInt < otherXInt || (thisXInt == otherXInt && thisYInt < otherYInt))
            return -1;
        else if (thisXInt > otherXInt || (thisXInt == otherXInt && thisYInt > otherYInt))
            return 1;
        return 0;
    }
}
