package eu.epptec.analyticGeometry.shapes;

import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.LinkedList;

public interface Shape2D extends Shape{
    public LinkedList<Point> getIntersectingPoints(Shape2D other);
    public double getIntersectingArea(Shape2D intersectingShape);
}
