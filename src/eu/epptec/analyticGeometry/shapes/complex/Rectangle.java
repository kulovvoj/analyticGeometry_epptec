package eu.epptec.analyticGeometry.shapes.complex;

import eu.epptec.analyticGeometry.shapes.elementary.Point;

public class Rectangle extends Rhomboid {

    public Rectangle(Point a, Point b, double length) {
        super(a, b, length);
    }

    @Override
    public String toString() {
        return "Rectangle - [" + getA().toString() + ", " + getB().toString() + ", " + getC().toString() + ", " + getD().toString() + "]";
    }
}
