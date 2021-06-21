package eu.epptec.analyticGeometry.shapes;

import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.List;

public interface Shape {
    final static double EPS = 1E-6;
    public Point getCenter();
    public Shape move(double x, double y);
    public Shape rotate(double angle);
    public Shape rotate(double angle, Point pivot);
    public List<Point> getIntersectingPoints(Shape other);
}
