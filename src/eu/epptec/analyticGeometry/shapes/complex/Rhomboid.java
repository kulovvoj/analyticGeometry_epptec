package eu.epptec.analyticGeometry.shapes.complex;

import eu.epptec.analyticGeometry.shapes.Shape2D;
import eu.epptec.analyticGeometry.shapes.basic.Line;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.LinkedList;

public class Rhomboid implements Shape2D {

    // Points of the rhomboid
    //  a ------ b
    //  |        |
    //  d ------ c
    // Point d can be calculated

    private Point a, b, c;

    public Rhomboid(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    protected Rhomboid(Point a, Point b, double length) {
        Point vec = new Line(a, b).getUnitVector();
        vec = vec.rotate(Math.PI / 2, new Point(0, 0));
        this.a = a;
        this.b = b;
        this.c = new Point((b.getX() + vec.getX()) * length, (b.getY() + vec.getY()) * length);
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Point getC() {
        return c;
    }

    public Point getD() {
        return c.move(a.getX() - b.getX(), a.getY() - b.getY());
    }

    @Override
    public Point getCenter() {
        return (new Line(a, c)).getCenter();
    }

    @Override
    public Rhomboid move(double x, double y) {
        return new Rhomboid(a.move(x, y), b.move(x, y), c.move(x, y));
    }

    @Override
    public Rhomboid rotate(double angle) {
        return this.rotate(angle, this.getCenter());
    }

    @Override
    public Rhomboid rotate(double angle, Point pivot) {
        return new Rhomboid(a.rotate(angle, pivot), b.rotate(angle, pivot), c.rotate(angle, pivot));
    }

    //TODO
    @Override
    public LinkedList<Point> getIntersectingPoints(Shape2D other) {
        return null;
    }

    //TODO
    @Override
    public double getIntersectingArea(Shape2D other) {
        return 0;
    }

}
