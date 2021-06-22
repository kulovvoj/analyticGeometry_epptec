package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.complex.Square;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    void getLength() {
        Line line1 = new Line(new Point(4, 3), new Point(6, 7));
        Line line2 = new Line(new Point(0, 0), new Point(0, 0));
        assertEquals(line1.getLength(), sqrt(2 * 2 + 4 * 4));
        assertEquals(line2.getLength(), 0);
    }

    @Test
    void getUnitVector() {
        Line line1 = new Line(new Point(4, 3), new Point(6, 7));
        Line line2 = new Line(new Point(0, 0), new Point(0, 0));
        assertEquals(line1.getUnitVector(), new Point(2.0 / sqrt(2 * 2 + 4 * 4), 4.0 / sqrt(2 * 2 + 4 * 4)));
        assertEquals(line2.getUnitVector(), new Point(0, 0));
    }

    @Test
    void getCenter() {
        Line line1 = new Line(new Point(4, 3), new Point(6, 7));
        Line line2 = new Line(new Point(0, 0), new Point(0, 0));
        assertEquals(line1.getCenter(), new Point(5, 5));
        assertEquals(line2.getCenter(), new Point(0, 0));
    }

    @Test
    void move() {
        Line line1 = new Line(new Point(4, 3), new Point(6, 7));
        Line line1moved = new Line(new Point(6, 5), new Point(8, 9));
        Line line2 = new Line(new Point(0, 0), new Point(0, 0));
        assertEquals(line1.move(2, 2), line1moved);
        assertEquals(line2.move(0, 0), line2);
    }

    @Test
    void rotate() {
        Line line1 = new Line(new Point(4, 3), new Point(6, 7));
        Line line2 = new Line(new Point(0, 0), new Point(0, 0));
        Line line3 = new Line(new Point(0, 0), new Point(2, 4));
        assertEquals(line1.rotate(PI), new Line(new Point(6, 7), new Point(4, 3)));
        assertEquals(line2.rotate(PI), line2);
        assertEquals(line3.rotate(PI / 2, new Point(0, 0)), new Line(new Point(0, 0), new Point(-4, 2)));
        assertEquals(line1.rotate(PI / 2, new Point(4, 3)), new Line(new Point(4, 3), new Point(0, 5)));
    }

    @Test
    void getIntersections() {
        Set<BasicShape> testList = new TreeSet<>();
        Line line1 = new Line(new Point(4, 3), new Point(6, 7));
        Line line2 = new Line(new Point(6, 6), new Point(4, 4));
        Line line3 = new Line(new Point(0, 0), new Point(0, 0));
        Line line4 = new Line(new Point(0, 0), new Point(0,4));
        Line line5 = new Line(new Point(0, -4), new Point(0,4));
        Line line6 = new Line(new Point(Math.sqrt(9.0 / 2.0), -6), new Point(Math.sqrt(9.0 / 2.0),6));
        Line line7 = new Line(new Point(3, 3), new Point(3, -3));

        Circle circle = new Circle(new Point(0, 0), 3);
        Square square = new Square(new Point(0, 0), new Point(0, 4));
        Point point1 = new Point(0, 0);
        Point point2 = new Point(1, 2);
        Point point3 = new Point(2, 5);
        Point point4 = new Point(-2, 6);
        Point point5 = new Point(0, 3);
        Point point6 = new Point(Math.sqrt(9.0 / 2.0), Math.sqrt(9.0 / 2.0));

        // Test intersections between lines
        testList.add(new Point(5, 5));
        //assertTrue(line1.getIntersections(line2).stream().map(elem -> testList.contains(elem)).reduce(true, (acc, elem) -> acc && elem));
        //assertTrue(line2.getIntersections(line1).stream().map(elem -> testList.contains(elem)).reduce(true, (acc, elem) -> acc && elem));
        assertEquals(line1.getIntersections(line2), testList);
        assertEquals(line2.getIntersections(line1), testList);
        testList.clear();
        testList.add(line1);
        //assertTrue(line1.getIntersections(line1).stream().map(elem -> testList.contains(elem)).reduce(true, (acc, elem) -> acc && elem));
        assertEquals(line1.getIntersections(line1), testList);

        // Test intersections with circles
        testList.clear();
        testList.add(new Point(0, 3));
        //assertTrue(line4.getIntersections(circle).stream().map(elem -> testList.contains(elem)).reduce(true, (acc, elem) -> acc && elem));
        assertEquals(line4.getIntersections(circle), testList);

        testList.clear();
        //assertTrue(line1.getIntersections(circle).stream().map(elem -> testList.contains(elem)).reduce(true, (acc, elem) -> acc && elem));
        assertEquals(line1.getIntersections(circle), testList);

        testList.clear();
        testList.add(new Point(0, 3));
        testList.add(new Point(0, -3));
        //assertTrue(line5.getIntersections(circle).stream().map(elem -> testList.contains(elem)).reduce(true, (acc, elem) -> acc && elem));
        assertEquals(line5.getIntersections(circle), testList);

        testList.clear();
        testList.add(new Point(Math.sqrt(9.0 / 2.0), -Math.sqrt(9.0 / 2.0)));
        testList.add(new Point(Math.sqrt(9.0 / 2.0), Math.sqrt(9.0 / 2.0)));
        System.out.println(line6.getIntersections(circle));
        //assertTrue(line6.getIntersections(circle).stream().map(elem -> testList.contains(elem)).reduce(true, (acc, elem) -> acc && elem));
        assertEquals(line6.getIntersections(circle), testList);

        testList.clear();
        testList.add(new Point(Math.sqrt(9.0 / 2.0), -Math.sqrt(9.0 / 2.0)));
        testList.add(new Point(Math.sqrt(9.0 / 2.0), Math.sqrt(9.0 / 2.0)));
        //assertTrue(line6.getIntersections(circle).stream().map(elem -> testList.contains(elem)).reduce(true, (acc, elem) -> acc && elem));
        assertEquals(line6.getIntersections(circle), testList);

        testList.clear();
        testList.add(new Point(3, 0));
        //assertTrue(line6.getIntersections(circle).stream().map(elem -> testList.contains(elem)).reduce(true, (acc, elem) -> acc && elem));
        assertEquals(line6.getIntersections(circle), testList);

        // Test intersections with a square
        testList.clear();
        testList.add(new Point(3, 0));
        //assertTrue(line6.getIntersections(square).stream().map(elem -> testList.contains(elem)).reduce(true, (acc, elem) -> acc && elem));
        assertEquals(line6.getIntersections(square), testList);


    }

    @Test
    void getGeneralEquation() {
    }
}