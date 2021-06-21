package eu.epptec.analyticGeometry.shapes.complex;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.basic.Line;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.LinkedList;
import java.util.List;

public class Rhomboid implements Shape {

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

    public List<Line> getLines() {
        List<Line> lines = new LinkedList<>();
        lines.add(new Line(a, b));
        lines.add(new Line(b, c));
        lines.add(new Line(c, getD()));
        lines.add(new Line(getD(), a));
        return lines;
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

    @Override
    public List<Point> getIntersectingPoints(Shape other) {
        List<Point> intersections = new LinkedList<>();
        getLines().forEach(line -> intersections.addAll(line.getIntersectingPoints(other)));
        return intersections;
    }
}
