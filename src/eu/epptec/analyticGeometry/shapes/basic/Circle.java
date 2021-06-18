package eu.epptec.analyticGeometry.shapes.basic;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.Shape2D;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.LinkedList;

import static java.lang.Math.abs;

public class Circle implements Shape2D {
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

    public LinkedList<Point> getIntersectingPoints(Line other) {
        return other.getIntersectingPoints(this);
    }

    public LinkedList<Point> getIntersectingPoints(Circle other) {
        LinkedList<Point> intersections = new LinkedList<>();
        Line centersLine = new Line(center, other.getCenter());
        double centersDistance = centersLine.getLength();

        if (abs(centersDistance - (radius + other.getRadius())) < EPS) {
            intersections.addAll(centersLine.getIntersectingPoints(this));
        } else if (centersDistance < radius + other.getRadius() + EPS) {
            //TODO
        }
    }

    @Override
    public LinkedList<Point> getIntersectingPoints(Shape2D other) {
        // Between line and a line
        // - check if they are parallel
        // - if not get intersection
        // - check if it lies on any of the
        return null;
    }

    @Override
    public double getIntersectingArea(Shape2D other) {
        return 0;
    }
}
