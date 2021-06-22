package eu.epptec.analyticGeometry;

import eu.epptec.analyticGeometry.application.Application;
import eu.epptec.analyticGeometry.shapes.elementary.*;
import eu.epptec.analyticGeometry.shapes.complex.*;

import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        app.addShape("circle", new Circle(new Point(0, 0), 3));
        app.addShape("point", new Point(3, 0));
        app.addShape("square1", new Square(new Point(0, 0), new Point(0, 3)));
        app.addShape("square2", new Square(new Point(1, 1), new Point(1, 4)));
        System.out.println(app.getIntersections("circle", "point"));
        System.out.println(app.getIntersections("square1", "square2"));
        app.addShape("line1", new Line(new Point(-1, 1), new Point(1, -1)));
        app.addShape("line2", new Line(new Point(-1, -1), new Point(-2, -2)));
        app.addShape("square1", new Square(new Point(0, 0), new Point(0, 3)));
        app.addShape("square2", new Square(new Point(1, 1), new Point(1, 4)));
        System.out.println(app.spaceToString());
        System.out.println(app.getIntersections("line1", "line2"));
        System.out.println(app.getIntersections("square1", "square2"));
        try {
            System.out.println(app.getIntersections("square1", "square3"));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(app.spaceToString());
        app.removeShape("line1");
        System.out.println(app.spaceToString());
    }
}
