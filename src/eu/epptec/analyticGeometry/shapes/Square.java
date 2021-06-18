package eu.epptec.analyticGeometry.shapes;

public class Square extends Rectangle {
    public Square(Point a, Point b) {
        super(a, b, new Line(a, b).getLength());
    }
}
