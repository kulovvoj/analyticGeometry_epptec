package eu.epptec.analyticGeometry.shapes.complex;

import eu.epptec.analyticGeometry.shapes.elementary.Line;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

public class Square extends Rectangle {
    public Square(Point a, Point b) {
        super(a, b, new Line(a, b).getLength());
    }
}
