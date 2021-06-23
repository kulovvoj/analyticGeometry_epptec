package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.complex.Square;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    void getIntersections() {
        Set<BasicShape> testList = new TreeSet<>();
        Line line = new Line(new Point(0, 0), new Point(2,4));
        Circle circle = new Circle(new Point(0, 0), 3);
        Square square = new Square(new Point(0, 0), new Point(2, 4));
        Point point1 = new Point(0, 0);
        Point point2 = new Point(1, 2);
        Point point3 = new Point(2, 5);
        Point point4 = new Point(-2, 6);
        Point point5 = new Point(0, 3);
        Point point6 = new Point(Math.sqrt(9.0 / 2.0), Math.sqrt(9.0 / 2.0));

        // Try intersections with another point
        testList.clear();
        testList.add(point1);
        assertEquals(point1.getIntersections(point1), testList);

        testList.clear();
        assertEquals(point1.getIntersections(point2), testList);
        assertEquals(point1.getIntersections(point2), testList);

        // Try intersections with a line
        testList.clear();
        testList.add(point1);
        assertEquals(point1.getIntersections(line), testList);

        testList.clear();
        testList.add(point2);
        assertEquals(point2.getIntersections(line), testList);

        testList.clear();
        assertEquals(point3.getIntersections(line), testList);

        // Intersections with a square
        testList.clear();
        testList.add(point1);
        assertEquals(point1.getIntersections(square), testList);

        testList.clear();
        testList.add(point2);
        assertEquals(point2.getIntersections(square), testList);

        testList.clear();
        assertEquals(point3.getIntersections(square), testList);

        testList.clear();
        testList.add(point4);
        assertEquals(point4.getIntersections(square), testList);

        // Intersections with a circle
        testList.clear();
        assertEquals(point1.getIntersections(circle), testList);

        testList.clear();
        testList.add(point5);
        assertEquals(point5.getIntersections(circle), testList);

        testList.clear();
        testList.add(point6);
        assertEquals(point6.getIntersections(circle), testList);
    }
}