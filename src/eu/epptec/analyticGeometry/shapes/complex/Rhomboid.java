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
import java.util.stream.Stream;

import static java.lang.Math.round;

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

    public Set<Line> getLines() {
        Set<Line> lines = new TreeSet<>();
        lines.add(new Line(a, b));
        lines.add(new Line(b, c));
        lines.add(new Line(c, getD()));
        lines.add(new Line(getD(), a));
        return lines;
    }

    public Set<Point> getPoints() {
        Set<Point> points = new TreeSet<>();
        points.add(a);
        points.add(b);
        points.add(c);
        points.add(getD());
        return points;
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

        // Add all the intersections
        intersections.addAll(getLines().stream().flatMap(line -> line.getIntersections(other).stream()).collect(Collectors.toList()));

        // If the line is coincidental and intersects one side of the rhomboid,
        // gets rid of false intersection points, which would be included in one of the intersection lines
        if (intersections.stream()
                .map(basicShape -> basicShape instanceof Line)
                .reduce(false, (acc, elem) -> acc || elem)) {
            Set<BasicShape> setOfLinePoints = intersections.stream()
                    .filter(basicShape -> basicShape instanceof Line)
                    .flatMap(line -> Stream.of(((Line) line).getA(), ((Line) line).getB()))
                    .collect(Collectors.toCollection(TreeSet::new));

            intersections = intersections.stream()
                    .filter(basicShape -> !(setOfLinePoints.contains(basicShape)))
                    .collect(Collectors.toCollection(TreeSet::new));
        }

        return intersections;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Rhomboid))
            return false;
        Rhomboid otherRhomb = (Rhomboid)other;

        return this.getLines().equals(otherRhomb.getLines());
    }

    @Override
    public String toString() {
        return "Rhomboid - [" + a.toString() + ", " + b.toString() + ", " + c.toString() + ", " + getD().toString() + "]";
    }




}
