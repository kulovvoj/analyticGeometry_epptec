package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.Shape;

import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.*;

public class Circle implements BasicShape {
    Point center;
    double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Circle move(double x, double y) {
        return new Circle(center.move(x, y), radius);
    }

    @Override
    public Shape rotate(double angle) {
        return this;
    }

    @Override
    public Shape rotate(double angle, Point pivot) {
        return new Circle(center.rotate(angle, pivot), radius);
    }

    @Override
    public Set<BasicShape> getIntersections(Shape other) {
        Set<BasicShape> lst = new TreeSet<BasicShape>();
        if (other instanceof Circle)
            lst.addAll(getIntersectionsCircle((Circle)other));
        else
            lst.addAll(other.getIntersections(this));
        return lst;
    }

    // Circle-circle intersection according to https://mathworld.wolfram.com/Circle-CircleIntersection.html
    private Set<BasicShape> getIntersectionsCircle(Circle other) {
        // Make sure that this circle is larger than the one passed as argument for easier computation
        if (other.getRadius() > radius)
            other.getIntersections(this);

        Set<BasicShape> intersections = new TreeSet<>();

        Line centersLine = new Line(getCenter(), other.getCenter());
        double centersDistance = centersLine.getLength();

        // If the circles are the same, the whole circle intersects
        if (this.equals(other)) {
            intersections.add(this);
        // If the two circles simply touch, there's only one intersection at the line joining the centers
        } else if (abs(centersDistance - radius - other.getRadius()) < EPS ||
                abs(radius - other.getRadius() - centersDistance) < EPS) {
            intersections.addAll(centersLine.getIntersections(this));
        // There's two intersections if the distance between the centers is less than the sum of radii and
        // one circle is not inside the other
        } else if (centersDistance < radius + other.getRadius() + EPS &&
                !(centersDistance + other.getRadius() < radius + EPS)) {
            // We calculate the distance from this circle to the line defined by the two intersections (dist1)
            // and then calculate the positions of the intersections from the radius and this distance
            double dist1 = (radius - other.getRadius() + centersDistance) / 2;
            //double dist1 = (pow(radius, 2) - pow(other.getRadius(), 2) + pow(centersDistance, 2)) / (2 * centersDistance);
            double h = sqrt(pow(radius, 2) - pow(dist1, 2));
            Point unitVec = centersLine.getUnitVector();
            intersections.add(new Point(center.getX() + unitVec.getX() * dist1 - unitVec.getY() * h,
                    center.getY() + unitVec.getY() * dist1 + unitVec.getX() * h));
            intersections.add(new Point(center.getX() + unitVec.getX() * dist1 + unitVec.getY() * h,
                    center.getY() + unitVec.getY() * dist1 - unitVec.getX() * h));
        }
        return intersections;
    }

    @Override
    public String toString() {
        return "Circle - [" + center.toString() + ", " + radius + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Circle))
            return false;
        Circle otherCircle = (Circle)other;
        long pointCmp = center.compareTo(otherCircle.getCenter());
        long thisRadiusInt = round(radius * compPrec);
        long otherRadiusInt = round(otherCircle.getRadius() * compPrec);
        return pointCmp == 0 && thisRadiusInt == otherRadiusInt;
    }

    @Override
    public int compareTo(BasicShape other) {
        if (other instanceof Point || other instanceof Line)
            return 1;
        Circle otherCircle = (Circle)other;
        long pointCmp = center.compareTo(otherCircle.getCenter());

        long thisRadiusInt = round(radius * compPrec);
        long otherRadiusInt = round(otherCircle.getRadius() * compPrec);
        if (pointCmp < 0 || (pointCmp == 0 && thisRadiusInt < otherRadiusInt))
            return -1;
        else if (pointCmp > 0 || (pointCmp == 0 && thisRadiusInt > otherRadiusInt))
            return 1;
        return 0;
    }
}
