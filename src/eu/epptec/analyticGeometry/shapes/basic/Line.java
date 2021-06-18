package eu.epptec.analyticGeometry.shapes.basic;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.Shape2D;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.LinkedList;

public class Line implements Shape2D {
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

    // Tests whether number a is in between numbers b and c
    private boolean isInbetween (double a, double b, double c) {
        return (b + EPS >= a && a + EPS >= c) || (c + EPS >= a && a + EPS >= b);
    }

    // Tests whether point a is in the boundary defined by points b and c
    private boolean isInbetween (Point a, Point b, Point c) {
        return isInbetween(a.getX(), b.getX(), c.getX()) || isInbetween(a.getY(), c.getY(), b.getY());
    }

    public LinkedList<Point> getIntersectingPoints(Line other) {
        LinkedList<Point> intersections = new LinkedList<>();

        double denom = (a.getX() - b.getX()) * (other.getA().getY() - other.getB().getY())
                - (a.getY() - b.getY()) * (other.getA().getX() - other.getB().getX());

        double tmp1 = a.getX() * b.getY() - a.getY() * b.getX();

        double tmp2 = other.getA().getX() * other.getB().getY()
                - other.getA().getY() * other.getB().getX();

        double intersectionX =
                (tmp1 * (other.getA().getX() - other.getB().getX()) - (a.getX() - b.getX()) * tmp2)
                        / denom;

        double intersectionY =
                (tmp1 * (other.getA().getY() - other.getB().getY()) - (a.getY() - b.getY()) * tmp2)
                        / denom;

        Point intersectionPoint = new Point(intersectionX, intersectionY);
        if (isInbetween(intersectionPoint, a, b) && isInbetween(intersectionPoint, other.getA(), other.getB()))
            intersections.add(intersectionPoint);

        return intersections;
    }

    // Calculates the intersections between this line and a circle
    public LinkedList<Point> getIntersectingPoints(Circle other) {
        LinkedList<Point> intersections = new LinkedList<>();

        double r = other.getRadius();
        double constA, constB, constC, m;

        // Calculate the constants of a * x + b * y + c = 0
        m = (this.b.getY()- this.a.getY()) / (this.b.getX() - this.a.getX());
        constA = m;
        constB = -1;
        constC = -m * (this.a.getX() - other.getCenter().getX()) + this.a.getY() - other.getCenter().getY();

        double x0 = -constA * constC / (constA * constA + constB * constB);
        double y0 = -constB * constC / (constA * constA + constB * constB);

        // One intersection
        if (Math.abs(constC * constC - r * r * (constA * constA + constB * constB)) < EPS) {
            Point pointTmp = new Point(x0 + other.getCenter().getX(), y0 + other.getCenter().getY());
            if (isInbetween(pointTmp, this.a, this.b))
                intersections.add(pointTmp);
        }
        // Two intersections
        else if (constC * constC < r * r * (constA * constA + constB * constB) + EPS) {
            double d = r  * r - constC * constC / (constA * constA + constB * constB);
            double mult = Math.sqrt(d / (constA * constA + constB * constB));
            double ax, ay, bx, by;
            ax = x0 + constB * mult + other.getCenter().getX();
            bx = x0 - constB * mult + other.getCenter().getY();
            ay = y0 - constA * mult + other.getCenter().getX();
            by = y0 + constA * mult + other.getCenter().getY();

            Point pointTmp = new Point(ax, ay);
            if (isInbetween(pointTmp, this.a, this.b))
                intersections.add(pointTmp);
            pointTmp = new Point(bx, by);
            if (isInbetween(pointTmp, this.a, this.b))
                intersections.add(pointTmp);
        }
        return intersections;
    }

    //TODO
    @Override
    public LinkedList<Point> getIntersectingPoints(Shape2D other) {
        LinkedList<Point> lst = new LinkedList<Point>();
        if (other.getClass() == Line.class)
            lst.addAll(getIntersectingPoints((Line)other));
        else if (other.getClass() == Circle.class)
            lst.addAll(getIntersectingPoints((Circle)other));
        else
            lst.addAll(other.getIntersectingPoints(this));
        return null;
    }

    @Override
    public double getIntersectingArea(Shape2D other) {
        return 0;
    }
}
