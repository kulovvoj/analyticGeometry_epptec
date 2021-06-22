package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.complex.Rhomboid;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    void move() {
        Circle circle1 = new Circle(new Point(3, 8), 6);
        Circle circle2 = new Circle(new Point(6, 10), 6);
        assertEquals(circle1.move(3, 2), circle2);
    }

    @Test
    void rotate() {
        Circle circle1 = new Circle(new Point(3, 8), 6);
        Circle circle2 = new Circle(new Point(0, 5), 6);
        assertEquals(circle1.rotate(PI), circle1);
        assertEquals(circle1.rotate(PI / 2, new Point(3, 5)), circle2);
    }

    @Test
    void getIntersections() {
        Set<BasicShape> testList = new TreeSet<>();

        Circle circle1 = new Circle(new Point(0, 0), 3);
        Circle circle2 = new Circle(new Point(1, 1), 3);
        Circle circle3 = new Circle(new Point(0, 4), 3);
        Circle circle4 = new Circle(new Point(-2, 9), 5);
        Circle circle5 = new Circle(new Point(-2, 10), 5);
        Circle circle6 = new Circle(new Point(6, 0), 3);
        Circle circle7 = new Circle(new Point(3, 3), 3);

        Line line1 = new Line(new Point(4, 3), new Point(6, 7));
        Line line2 = new Line(new Point(0, 0), new Point(0, 4));
        Line line3 = new Line(new Point(0, -4), new Point(0, 4));
        Line line4 = new Line(new Point(Math.sqrt(9.0 / 2.0), -6), new Point(Math.sqrt(9.0 / 2.0), 6));
        Line line5 = new Line(new Point(3, 3), new Point(3, -3));
        Line line6 = new Line(new Point(0, 4), new Point(2, 4));

        Rhomboid rhomboid1 = new Rhomboid(new Point(0, 0), new Point(0, 4), new Point(-4, 4));

        Point point1 = new Point(0, 0);
        Point point2 = new Point(0, 3);
        Point point3 = new Point(Math.sqrt(9.0 / 2.0), Math.sqrt(9.0 / 2.0));

        // Test intersections with lines
        testList.clear();
        assertEquals(circle1.getIntersections(line1), testList);
        
        testList.clear();
        testList.add(new Point(0, 3));
        assertEquals(circle1.getIntersections(line2), testList);
        
        testList.clear();
        testList.add(new Point(0, 3));
        testList.add(new Point(0, -3));
        assertEquals(circle1.getIntersections(line3), testList);

        testList.clear();
        testList.add(new Point(Math.sqrt(9.0 / 2.0), -Math.sqrt(9.0 / 2.0)));
        testList.add(new Point(Math.sqrt(9.0 / 2.0), Math.sqrt(9.0 / 2.0)));
        assertEquals(circle1.getIntersections(line4), testList);

        testList.clear();
        testList.add(new Point(3, 0));
        assertEquals(circle1.getIntersections(line5), testList);

        testList.clear();
        testList.add(new Point(1, 4));
        assertEquals(circle2.getIntersections(line6), testList);
        
        // Rhomboid intersections
        testList.clear();
        testList.add(new Point(0, 1));
        testList.add(new Point(-3, 4));
        assertEquals(circle3.getIntersections(rhomboid1), testList);

        testList.clear();
        testList.add(new Point(-2, 4));
        assertEquals(new Line(new Point(0, 4), new Point(-4, 4)).getIntersections(circle4), testList);
        assertEquals(circle4.getIntersections(rhomboid1), testList);
        assertEquals(new Line(new Point(0, 4), new Point(-4, 4)).getIntersections(circle4), testList);

        testList.clear();
        assertEquals(circle5.getIntersections(rhomboid1), testList);

        // Intersections with a point
        testList.clear();
        assertEquals(circle1.getIntersections(point1), testList);

        testList.clear();
        testList.add(point2);
        assertEquals(circle1.getIntersections(point2), testList);

        testList.clear();
        testList.add(point3);
        assertEquals(circle1.getIntersections(point3), testList);
        
        // Intersections with another circle
        testList.clear();
        testList.add(new Point(3, 0));
        assertEquals(circle1.getIntersections(circle6), testList);
        assertEquals(circle6.getIntersections(circle1), testList);

        testList.clear();
        testList.add(new Point(3, 0));
        testList.add(new Point(0, 3));
        assertEquals(circle1.getIntersections(circle7), testList);
        assertEquals(circle7.getIntersections(circle1), testList);

        testList.clear();
        testList.add(circle4);
        assertEquals(circle4.getIntersections(circle4), testList);

        testList.clear();
        assertEquals(circle1.getIntersections(circle5), testList);
        assertEquals(circle5.getIntersections(circle1), testList);
    }
}