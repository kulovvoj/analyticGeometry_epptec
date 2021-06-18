package eu.epptec.analyticGeometry.shapes;

public interface Shape {
    public void move(double x, double y);
    public double getIntersectingArea(Shape intersectingShape);
}
