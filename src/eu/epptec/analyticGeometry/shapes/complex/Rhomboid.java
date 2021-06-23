package eu.epptec.analyticGeometry.shapes.complex;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.elementary.BasicShape;
import eu.epptec.analyticGeometry.shapes.elementary.Line;
import eu.epptec.analyticGeometry.shapes.elementary.Point;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Rhomboid extends ComplexShape {

    // Points of the rhomboid
    //  a ------ b
    //  |        |
    //  d ------ c
    // Point d can be calculated when all the other points are known

    public Rhomboid(Point a, Point b, Point c) {
        Point d = getD(a, b, c);
        components.add(new Line(a, b));
        components.add(new Line(b, c));
        components.add(new Line(c, d));
        components.add(new Line(d, a));
        center = new Line(a, c).getCenter();
    }

    public Rhomboid(Point a, Point b, double length) {
        Point vec = new Line(a, b).getUnitVector();
        vec = vec.rotate(Math.PI / 2, new Point(0, 0));
        Point c = new Point(b.getX() + vec.getX() * length, b.getY() + vec.getY() * length);
        Point d = getD(a, b, c);
        components.add(new Line(a, b));
        components.add(new Line(b, c));
        components.add(new Line(c, d));
        components.add(new Line(d, a));
    }

    private Point getD(Point a, Point b, Point c) {
        return c.move(a.getX() - b.getX(), a.getY() - b.getY());
    }
}
