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
        Line line4 = new Line(new Point(0, 0), new Point(0, 4));
        Line line5 = new Line(new Point(0, -4), new Point(0, 4));
        Line line6 = new Line(new Point(Math.sqrt(9.0 / 2.0), -6), new Point(Math.sqrt(9.0 / 2.0), 6));
        Line line7 = new Line(new Point(3, 3), new Point(3, -3));
        Line line8 = new Line(new Point(1, 1), new Point(-1, -1));
        Line line9 = new Line(new Point(1, 1), new Point(-1, 1));
        Line line10 = new Line(new Point(10, 1), new Point(-10, 1));
        Line line11 = new Line(new Point(-2, -1), new Point(1, 2));
        Line line12 = new Line(new Point(0, 4), new Point(2, 4));

        Circle circle1 = new Circle(new Point(0, 0), 3);
        Circle circle2 = new Circle(new Point(1, 1), 3);
        Square square = new Square(new Point(0, 0), new Point(0, 4));

        // Test intersections between lines
        testList.add(new Point(5, 5));
        assertEquals(line1.getIntersections(line2), testList);
        assertEquals(line2.getIntersections(line1), testList);

        testList.clear();
        testList.add(line1);
        assertEquals(line1.getIntersections(line1), testList);

        testList.clear();
        testList.add(new Point(0, 0));
        assertEquals(line3.getIntersections(line4), testList);
        assertEquals(line4.getIntersections(line3), testList);
        assertEquals(line3.getIntersections(line3), testList);

        // Test intersections with circles
        testList.clear();
        testList.add(new Point(0, 3));
        assertEquals(line4.getIntersections(circle1), testList);

        testList.clear();
        assertEquals(line1.getIntersections(circle1), testList);

        testList.clear();
        testList.add(new Point(0, 3));
        testList.add(new Point(0, -3));
        assertEquals(line5.getIntersections(circle1), testList);

        testList.clear();
        testList.add(new Point(Math.sqrt(9.0 / 2.0), -Math.sqrt(9.0 / 2.0)));
        testList.add(new Point(Math.sqrt(9.0 / 2.0), Math.sqrt(9.0 / 2.0)));
        assertEquals(line6.getIntersections(circle1), testList);

        testList.clear();
        testList.add(new Point(3, 0));
        assertEquals(line7.getIntersections(circle1), testList);

        testList.clear();
        testList.add(new Point(1, 4));
        assertEquals(line12.getIntersections(circle2), testList);

        // Test intersections with a square
        testList.clear();
        testList.add(new Point(0, 0));
        assertEquals(line8.getIntersections(square), testList);

        testList.clear();
        testList.add(new Point(0, 0));
        assertEquals(line3.getIntersections(square), testList);

        testList.clear();
        testList.add(line4);
        assertEquals(line4.getIntersections(square), testList);

        testList.clear();
        testList.add(new Point(0, 1));
        assertEquals(line9.getIntersections(square), testList);

        testList.clear();
        testList.add(new Point(-4, 1));
        testList.add(new Point(0, 1));
        assertEquals(line10.getIntersections(square), testList);

        testList.clear();
        testList.add(new Point(0, 1));
        testList.add(new Point(-1, 0));
        assertEquals(line11.getIntersections(square), testList);
    }

    @Test
    void getGeneralEquation() {
    }
}