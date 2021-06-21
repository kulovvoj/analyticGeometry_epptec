package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.complex.Square;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void move() {
        assertEquals(new Point(2, 8).move(3, -8), new Point(5, 0));
    }

    @Test
    void rotate() {
        assertEquals(new Point(2, 8).rotate(Math.PI), new Point(2, 8));
        assertEquals(new Point(2, 8).rotate(Math.PI), new Point(2, 8));
        assertEquals(new Point(2, 8).rotate(Math.PI, new Point(0,0)), new Point(-2, -8));
        assertEquals(new Point(2, 8).rotate(Math.PI / 2, new Point(0,0))
                .rotate(- Math.PI / 2, new Point(0, 0)), new Point(2, 8));
        assertEquals(new Point(2, 8).rotate(Math.PI / 2, new Point(0,0)), new Point(-8, 2));
        assertEquals(new Point(2, 4).rotate(Math.PI / 2, new Point(4, 0)), new Point(0, -2));
    }

    @Test
    void getCenter() {
        assertEquals(new Point(2, 8).getCenter(), new Point(2, 8));
    }

    @Test
    void getIntersectingPoints() {
        Set<Point> testSet = new HashSet<>();
        Line line = new Line(new Point(0, 0), new Point(2,4));
        Circle circle = new Circle(new Point(0, 0), 3);
        Square square = new Square(new Point(0, 0), new Point(2, 4));
        Point point1 = new Point(0, 0);
        Point point2 = new Point(1, 2);
        Point point3 = new Point(2, 5);
        Point point4 = new Point(-2, 6);
        Point point5 = new Point(0, 3);
        Point point6 = new Point(Math.sqrt(9.0 / 2.0), Math.sqrt(9.0 / 2.0));

        testSet.add(point1);
        assertEquals(point1.getIntersectingPoints(line), testSet);

        testSet.clear();
        testSet.add(point2);
        assertEquals(point2.getIntersectingPoints(line), testSet);

        testSet.clear();
        assertEquals(point3.getIntersectingPoints(line), testSet);

        testSet.clear();
        testSet.add(point1);
        assertEquals(point1.getIntersectingPoints(square), testSet);

        testSet.clear();
        testSet.add(point2);
        assertEquals(point2.getIntersectingPoints(square), testSet);

        testSet.clear();
        assertEquals(point3.getIntersectingPoints(square), testSet);

        testSet.clear();
        testSet.add(point4);
        assertEquals(point4.getIntersectingPoints(square), testSet);

        testSet.clear();
        assertEquals(point1.getIntersectingPoints(circle), testSet);

        testSet.clear();
        testSet.add(point5);
        assertEquals(point5.getIntersectingPoints(circle), testSet);

        testSet.clear();
        testSet.add(point6);
        assertEquals(point6.getIntersectingPoints(circle), testSet);
    }
}