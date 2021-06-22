package eu.epptec.analyticGeometry.shapes.complex;

import eu.epptec.analyticGeometry.shapes.elementary.BasicShape;
import eu.epptec.analyticGeometry.shapes.elementary.Circle;
import eu.epptec.analyticGeometry.shapes.elementary.Line;
import eu.epptec.analyticGeometry.shapes.elementary.Point;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

class RhomboidTest {

    @Test
    void getCenter() {
        Rhomboid rhomboid1 = new Rhomboid(new Point(3, 8), new Point(5, 7), new Point(0, -2));
        Rhomboid rhomboid2 = new Rhomboid(new Point(0, 0), new Point(0, 0), new Point(0, 0));
        assertEquals(rhomboid1.getCenter(), new Point(1.5, 3));
        assertEquals(rhomboid2.getCenter(), new Point(0, 0));
    }

    @Test
    void move() {
        Rhomboid rhomboid1 = new Rhomboid(new Point(3, 8), new Point(5, 7), new Point(0, -2));
        Rhomboid rhomboid2 = new Rhomboid(new Point(6, 10), new Point(8, 9), new Point(3, 0));
        assertEquals(rhomboid1.move(3, 2), rhomboid2);
    }

    @Test
    void rotate() {
        Rhomboid rhomboid1 = new Rhomboid(new Point(3, 8), new Point(5, 7), new Point(0, -2));
        Rhomboid rhomboid2 = new Rhomboid(new Point(-3, -8), new Point(-5, -7), new Point(0, 2));
        assertEquals(rhomboid1.rotate(PI), rhomboid1);
        assertEquals(rhomboid1.rotate(PI, new Point(0, 0)), rhomboid2);
    }

    @Test
    void getIntersections() {
        Set<BasicShape> testList = new TreeSet<>();
        Rhomboid rhomboid1 = new Rhomboid(new Point(3, 8), new Point(5, 7), new Point(0, -2));
        Rhomboid rhomboid2 = new Rhomboid(new Point(0, 0), new Point(0, 4), new Point(-4, 4));
        Rhomboid rhomboid3 = new Rhomboid(new Point(2, 1), new Point(4, 1), new Point(4, 3));
        Rhomboid rhomboid4 = new Rhomboid(new Point(2, 1), new Point(4, 1), new Point(6, 5));
        Rhomboid rhomboid5 = new Rhomboid(new Point(2, 1), new Point(4, 1), new Point(5, 3));

        Line line1 = new Line(new Point(3, 8), new Point(5, 7));
        Line line2 = new Line(new Point(0, 0), new Point(0, 0));
        Line line3 = new Line(new Point(0, 0), new Point(0, 4));
        Line line4 = new Line(new Point(1, 1), new Point(-1, -1));
        Line line5 = new Line(new Point(1, 1), new Point(-1, 1));
        Line line6 = new Line(new Point(10, 1), new Point(-10, 1));
        Line line7 = new Line(new Point(-2, -1), new Point(1, 2));

        Circle circle1 = new Circle(new Point(0, 4), 3);
        Circle circle2 = new Circle(new Point(-2, 9), 5);
        Circle circle3 = new Circle(new Point(-2, 10), 5);

        // Line intersections
        testList.clear();
        testList.add(line1);
        assertEquals(rhomboid1.getIntersections(line1), testList);

        testList.clear();
        testList.add(line1);
        assertEquals(rhomboid1.getIntersections(line1), testList);

        testList.clear();
        testList.add(new Point(0, 0));
        assertEquals(rhomboid2.getIntersections(line4), testList);

        testList.clear();
        testList.add(new Point(0, 0));
        assertEquals(rhomboid2.getIntersections(line2), testList);

        testList.clear();
        testList.add(line3);
        assertEquals(rhomboid2.getIntersections(line3), testList);

        testList.clear();
        testList.add(new Point(0, 1));
        assertEquals(rhomboid2.getIntersections(line5), testList);

        testList.clear();
        testList.add(new Point(-4, 1));
        testList.add(new Point(0, 1));
        assertEquals(rhomboid2.getIntersections(line6), testList);

        testList.clear();
        testList.add(new Point(0, 1));
        testList.add(new Point(-1, 0));
        assertEquals(rhomboid2.getIntersections(line7), testList);

        // Circle intersections
        testList.clear();
        testList.add(new Point(0, 1));
        testList.add(new Point(-3, 4));
        assertEquals(rhomboid2.getIntersections(circle1), testList);

        testList.clear();
        testList.add(new Point(-2, 4));
        assertEquals(new Line(new Point(0, 4), new Point(-4, 4)).getIntersections(circle2), testList);
        assertEquals(rhomboid2.getIntersections(circle2), testList);
        assertEquals(new Line(new Point(0, 4), new Point(-4, 4)).getIntersections(circle2), testList);

        testList.clear();
        assertEquals(rhomboid2.getIntersections(circle3), testList);

        // Other rhomboid intersection
        assertEquals(rhomboid1.getIntersections(rhomboid1), rhomboid1.getLines());

        testList.clear();
        testList.add(new Line(new Point(2, 1), new Point(4, 1)));
        testList.add(new Point(3, 3));
        assertEquals(rhomboid3.getIntersections(rhomboid4), testList);
        assertEquals(rhomboid4.getIntersections(rhomboid3), testList);

        testList.clear();
        testList.add(new Line(new Point(2, 1), new Point(4, 1)));
        testList.add(new Line(new Point(3, 3), new Point(4, 3)));
        assertEquals(rhomboid5.getIntersections(rhomboid3), testList);
        assertEquals(rhomboid3.getIntersections(rhomboid5), testList);
    }
}