package eu.epptec.analyticGeometry.shapes.basic;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    // Calculates the unit vector of the line facing from A to B
    public Point getUnitVector() {
        double xVec = (b.getX() - a.getX()) / this.getLength();
        double yVec = (b.getY() - a.getY()) / this.getLength();
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
    private static boolean isInbetween (double x, double y, double z) {
        return (y + EPS >= x && x + EPS >= z) || (z + EPS >= x && x + EPS >= y);
    }

    // Tests whether point a is in the boundary defined by points b and c
    public boolean isInBoundary(Point point) {
        return isInbetween(point.getX(), a.getX(), b.getX()) || isInbetween(point.getY(), b.getY(), a.getY());
    }

    //TODO
    @Override
    public List<Point> getIntersectingPoints(Shape other) {
        List<Point> lst = new LinkedList<>();
        if (other instanceof Line)
            lst.addAll(getIntersectingPointsLine((Line)other));
        else if (other instanceof Circle)
            lst.addAll(getIntersectingPointsCircle((Circle)other));
        else
            lst.addAll(other.getIntersectingPoints(this));
        return lst;
    }

    // Calculates the intersections between a line and a line
    private List<Point> getIntersectingPointsLine(Line other) {
        List<Point> intersections = new LinkedList<>();

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
        if (this.isInBoundary(intersectionPoint) && other.isInBoundary(intersectionPoint))
            intersections.add(intersectionPoint);

        return intersections;
    }

    // Calculates the intersections between this line and a circle
    // We move the circle's center to (0, 0) for easier calculation
    private List<Point> getIntersectingPointsCircle(Circle other) {
        List<Point> intersections = new LinkedList<>();

        double r = other.getRadius();
        double genEqA, genEqB, genEqC;

        // Get the general equation of moved line
        List<Double> generalEquation = this.move(-other.getCenter().getX(), -other.getCenter().getY()).getGeneralEquation();
        genEqA = generalEquation.get(0);
        genEqB = generalEquation.get(1);
        genEqC = generalEquation.get(2);

        double x0 = -genEqA * genEqC / (genEqA * genEqA + genEqB * genEqB);
        double y0 = -genEqB * genEqC / (genEqA * genEqA + genEqB * genEqB);

        // One intersection
        if (Math.abs(genEqC * genEqC - r * r * (genEqA * genEqA + genEqB * genEqB)) < EPS) {
            Point pointTmp = new Point(x0 + other.getCenter().getX(), y0 + other.getCenter().getY());
            if (this.isInBoundary(pointTmp))
                intersections.add(pointTmp);
        }
        // Two intersections
        else if (genEqC * genEqC < r * r * (genEqA * genEqA + genEqB * genEqB) + EPS) {
            double d = r  * r - genEqC * genEqC / (genEqA * genEqA + genEqB * genEqB);
            double mult = Math.sqrt(d / (genEqA * genEqA + genEqB * genEqB));
            double ax, ay, bx, by;
            ax = x0 + genEqB * mult + other.getCenter().getX();
            bx = x0 - genEqB * mult + other.getCenter().getY();
            ay = y0 - genEqA * mult + other.getCenter().getX();
            by = y0 + genEqA * mult + other.getCenter().getY();

            Point pointTmp = new Point(ax, ay);
            if (this.isInBoundary(pointTmp))
                intersections.add(pointTmp);
            pointTmp = new Point(bx, by);
            if (this.isInBoundary(pointTmp))
                intersections.add(pointTmp);
        }
        return intersections;
    }

    public List<Double> getGeneralEquation() {
        double genEqA, genEqB, genEqC, m;

        // Calculate the genEqAnts of a * x + b * y + c = 0
        m = (this.b.getY()- this.a.getY()) / (this.b.getX() - this.a.getX());
        genEqA = m;
        genEqB = -1;
        genEqC = -m * (this.a.getX()) + this.a.getY();
        List<Double> equationVars = new ArrayList<>();
        equationVars.add(genEqA);
        equationVars.add(genEqB);
        equationVars.add(genEqC);
        return equationVars;
    }

    @Override
    public String toString() {
        return "Line - [" + a.toString() + ", " + b.toString() + "]";
    }
}
