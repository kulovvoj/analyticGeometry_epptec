package eu.epptec.analyticGeometry.shapes.complex;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.elementary.BasicShape;
import eu.epptec.analyticGeometry.shapes.elementary.Line;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Rhomboid implements Shape {

    // Points of the rhomboid
    //  a ------ b
    //  |        |
    //  d ------ c
    // Point d can be calculated when all the other points are known

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
        this.c = new Point(b.getX() + vec.getX() * length, b.getY() + vec.getY() * length);
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
    public Set<BasicShape> getIntersections(Shape other) {
        Set<BasicShape> intersections = new TreeSet<>();
        getLines().forEach(line -> intersections.addAll(line.getIntersections(other)));

        // Gets rid of false intersection points, if the line is coincidental with one side of the rhomboid
        if (intersections.stream().map(basicShape -> basicShape instanceof Line).reduce(false, (acc, elem) -> acc || elem))
            return intersections.stream().filter(basicShape -> !(basicShape instanceof Point)).collect(Collectors.toSet());

        return intersections;
    }

    @Override
    public String toString() {
        return "Rhomboid - [" + a.toString() + ", " + b.toString() + ", " + c.toString() + ", " + getD().toString() + "]";
    }
}
