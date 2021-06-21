package eu.epptec.analyticGeometry.shapes.basic;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;

public class Circle implements Shape {
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
    public Shape move(double x, double y) {
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

    public List<Point> getIntersectingPoints(Line other) {
        return other.getIntersectingPoints(this);
    }

    @Override
    public List<Point> getIntersectingPoints(Shape other) {
        List<Point> lst = new LinkedList<>();
        if (other instanceof Circle)
            lst.addAll(getIntersectingPointsCircle((Circle)other));
        else
            lst.addAll(other.getIntersectingPoints(this));
        return lst;
    }

    private List<Point> getIntersectingPointsCircle(Circle other) {
        // Make sure that this circle is larger than the one passed as argument
        if (other.getRadius() > radius)
            other.getIntersectingPoints(this);

        List<Point> intersections = new LinkedList<>();
        Line centersLine = new Line(center, other.getCenter());
        double centersDistance = centersLine.getLength();

        // If the two circles simply touch, there's only one intersection at the line joining the centers
        if (abs(centersDistance - radius - other.getRadius()) < EPS ||
                abs(radius - other.getRadius() - centersDistance) < EPS) {
            intersections.addAll(centersLine.getIntersectingPoints(this));
        // There's two intersections if the distance between the centers is less than the sum of radii and
        // one circle is not inside the other
        } else if (centersDistance < radius + other.getRadius() + EPS &&
                !(centersDistance + other.getRadius() < radius + EPS)) {
            // We calculate the distance from this circle to the line of the line defined by the two intersections (dist1)
            // and then calculate the positions of the intersections from the radius and this distance
            double dist1 = (pow(radius, 2) - pow(other.getRadius(), 2) + pow(centersDistance , 2)) / (2 * centersDistance);
            double h = sqrt(pow(radius, 2) - pow(dist1, 2));
            Point unitVec = centersLine.getUnitVector();
            intersections.add(new Point(center.getX() + unitVec.getX() * dist1 - unitVec.getY() * h,
                    center.getY() + unitVec.getY() * dist1 + unitVec.getX() * h));
            intersections.add(new Point(center.getX() + unitVec.getX() * dist1 + unitVec.getY() * h,
                    center.getY() + unitVec.getY() * dist1 - unitVec.getX() * h));
        }
        return intersections;
    }
}
