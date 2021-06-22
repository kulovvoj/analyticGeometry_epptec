package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.Shape;

import java.util.*;

import static java.lang.Math.*;

public class Line implements BasicShape {
    private final Point a, b;

    public Line(Point a, Point b) {
        if (a.compareTo(b) <= 0) {
            this.a = a;
            this.b = b;
        } else {
            this.a = b;
            this.b = a;
        }
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public double getLength() {
        return Math.sqrt(pow(a.getX() - b.getX(), 2) + pow(a.getY() - b.getY(), 2));
    }

    // Calculates the unit vector of the line facing from A to B
    public Point getUnitVector() {
        if (this.getLength() < EPS)
            return new Point(0, 0);
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

    // Tests whether number x is in between numbers y and z
    // Whether y > z or z > y doesn't matter
    private static boolean isInbetween (double x, double y, double z) {
        return (y + EPS > x && x + EPS > z) || (z + EPS > x && x + EPS > y);
    }

    // Tests whether point a is in the boundary box defined by the line's points
    public boolean isInBoundary(Point point) {
        return isInbetween(point.getX(), a.getX(), b.getX()) && isInbetween(point.getY(), b.getY(), a.getY());
    }

    @Override
    public Set<BasicShape> getIntersections(Shape other) {
        Set<BasicShape> lst = new TreeSet<>();
        if (other instanceof Line)
            lst.addAll(getIntersectionsLine((Line)other));
        else if (other instanceof Circle)
            lst.addAll(getIntersectionsCircle((Circle)other));
        else
            lst.addAll(other.getIntersections(this));
        return lst;
    }

    // Calculates the intersections between a line and a line
    private Set<BasicShape> getIntersectionsLine(Line other) {
        Set<BasicShape> intersections = new TreeSet<>();

        // If any of the two lines is of length 0, we convert it to a point and check if that lies on the other line
        if (this.getA().equals(this.getB())) {
            intersections.addAll(this.getA().getIntersections(other));
        }
        else if (other.getA().equals(other.getB()))
            intersections.addAll(other.getA().getIntersections(this));
        // If the two lines are the same, we can just add it in the intersections
        else if (this.equals(other))
            intersections.add(this);
        // Otherwise we calculate
        else {
            double denom = (a.getX() - b.getX()) * (other.getA().getY() - other.getB().getY())
                    - (a.getY() - b.getY()) * (other.getA().getX() - other.getB().getX());

            // Testing whether the lines are parallel
            if (abs(denom) < EPS) {
                List<Double> genEqThis = this.getGeneralEquation();
                List<Double> genEqOther = other.getGeneralEquation();
                // And whether they are coincidental
                /*if ((genEqThis.get(0) - genEqOther.get(0)) < EPS &&
                        (genEqThis.get(1) - genEqOther.get(1)) < EPS &&
                        (genEqThis.get(2) - genEqOther.get(2)) < EPS) {*/
                if (this.getA().getIntersections(other).isEmpty()) {
                    // There are 6 cases of coincidental line segments, that intersect each other
                    if (this.isInBoundary(other.getA()) && this.isInBoundary(other.getB()))
                        intersections.add(other);
                    else if (this.isInBoundary(other.getA()) && other.isInBoundary(this.getA()))
                        intersections.add(new Line(other.getA(), this.getA()));
                    else if (this.isInBoundary(other.getA()) && other.isInBoundary(this.getB()))
                        intersections.add(new Line(other.getA(), this.getB()));
                    else if (this.isInBoundary(other.getB()) && other.isInBoundary(this.getA()))
                        intersections.add(new Line(other.getB(), this.getA()));
                    else if (this.isInBoundary(other.getB()) && other.isInBoundary(this.getB()))
                        intersections.add(new Line(other.getB(), this.getB()));
                    else if (other.isInBoundary(this.getA()) && other.isInBoundary(this.getB()))
                        intersections.add(this);
                }

                // If the lines are not parallel, we can calculate their intersection point
            } else {
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

                // We add the point only if it lies on both of the line segments
                if (this.isInBoundary(intersectionPoint) && other.isInBoundary(intersectionPoint)) {
                    intersections.add(intersectionPoint);
                }
            }
        }
        return intersections;
    }

    // Calculates the intersections between this line and a circle
    // We move the circle's center to origin point (0, 0) for easier calculation. After we're
    // finished with the calculations, we compensate for the movement
    /*private Set<BasicShape> getIntersectionsCircle(Circle other) {
        Set<BasicShape> intersections = new TreeSet<>();

        double r = other.getRadius();
        double genEqA, genEqB, genEqC;

        // Get the general equation of the moved line
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
            System.out.println("HI");
            System.out.println(this);
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
    }*/

    private int sgnStar (double x) {
        return x < -EPS ? -1 : 1;
    }

    // Line - circle intersections according to https://mathworld.wolfram.com/Circle-LineIntersection.html
    private Set<BasicShape> getIntersectionsCircle(Circle other) {
        // If the line is of length 0
        if (a.equals(b))
            return a.getIntersections(other);

        Set<BasicShape> intersections = new TreeSet<>();
        // We will calculate the intersection with the circle's center moved to origin point (0, 0),
        // thus we move the line segment as well
        Line movedLine = this.move(-other.center.getX(), -other.center.getY());

        double dx = movedLine.getB().getX() - movedLine.getA().getX();
        double dy = movedLine.getB().getY() - movedLine.getA().getY();
        double dr = movedLine.getLength();
        double D = movedLine.getA().getX() * movedLine.getB().getY() - movedLine.getA().getY() * movedLine.getB().getX();
        double disc = pow(other.radius, 2) * pow(dr, 2) - pow(D, 2);
        if (disc > -EPS) {
            double x = (D * dy + sgnStar(dy) * dx * sqrt(disc)) / pow(dr, 2) + other.center.getX();
            double y = (D * dx + abs(dy) * sqrt(disc)) / pow(dr, 2) + other.center.getY();
            Point pointTmp = new Point(x, y);
            if (this.isInBoundary(pointTmp))
                intersections.add(pointTmp);
        }
        if (disc > EPS) {
            double x = (D * dy - sgnStar(dy) * dx * sqrt(disc)) / pow(dr, 2) + other.center.getX();
            double y = (D * dx - abs(dy) * sqrt(disc)) / pow(dr, 2) + other.center.getY();
            Point pointTmp = new Point(x, y);
            if (this.isInBoundary(pointTmp))
                intersections.add(pointTmp);
        }
        
        return intersections;
    }

    // Calculates the line equation "a * x + b * y + c = 0"
    public List<Double> getGeneralEquation() {
        double genEqA, genEqB, genEqC, m;

        List<Double> equationVars = new ArrayList<>();

        // If the line is vertical
        if (this.a.getX() == this.b.getX()) {
            equationVars.add(1d);
            equationVars.add(0d);
            equationVars.add(-this.a.getX());
        // Else we can calculate the general equation the normal way
        } else {
            m = (this.b.getY() - this.a.getY()) / (this.b.getX() - this.a.getX());
            genEqA = m;
            genEqB = -1;
            genEqC = -m * (this.a.getX()) + this.a.getY();
            equationVars.add(genEqA);
            equationVars.add(genEqB);
            equationVars.add(genEqC);
        }
        return equationVars;
    }

    @Override
    public String toString() {
        return "Line - [" + a.toString() + ", " + b.toString() + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Line))
            return false;
        Line otherLine = (Line)other;
        return (a.equals(otherLine.getA()) && b.equals(otherLine.getB()));
    }

    @Override
    public int compareTo(BasicShape other) {
        if (other instanceof Point)
            return 1;
        else if (other instanceof Circle)
            return -1;

        Line otherLine = (Line)other;
        long pointACmp = a.compareTo(otherLine.getA());
        long pointBCmp = b.compareTo(otherLine.getB());

        if (pointACmp < 0 || (pointACmp == 0 && pointBCmp < 0))
            return -1;
        else if (pointACmp > 0 || (pointACmp == 0 && pointBCmp > 0))
            return 1;
        return 0;
    }
}
