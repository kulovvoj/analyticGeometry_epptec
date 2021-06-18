package eu.epptec.analyticGeometry.shapes;

import java.util.LinkedList;

public interface Shape {
    public Point getCenter();
    public Shape move(double x, double y);
    public Shape rotate(double angle);
    public Shape rotate(double angle, Point pivot);
    public double getIntersectingArea(Shape intersectingShape);
    public LinkedList<Point> getIntersectingPoints(Shape intersectingShape);
}
