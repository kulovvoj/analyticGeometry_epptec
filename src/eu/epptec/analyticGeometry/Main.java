package eu.epptec.analyticGeometry;

import eu.epptec.analyticGeometry.application.Application;
import eu.epptec.analyticGeometry.shapes.elementary.*;
import eu.epptec.analyticGeometry.shapes.complex.*;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        /*app.addShape("circle", new Circle(new Point(0, 0), 3));
        app.addShape("point", new Point(3, 0));
        app.addShape("square1", new Square(new Point(0, 0), new Point(0, 3)));
        app.addShape("square2", new Square(new Point(1, 1), new Point(1, 4)));
        System.out.println(app.getIntersectingPoints("circle", "point"));
        System.out.println(app.getIntersectingPoints("square1", "square2"));*/
        app.addShape("line1", new Line(new Point(-1, 1), new Point(1, -1)));
        app.addShape("line2", new Line(new Point(-1, -1), new Point(-2, -2)));
        app.addShape("square1", new Square(new Point(0, 0), new Point(0, 3)));
        app.addShape("square2", new Square(new Point(1, 1), new Point(1, 4)));
        System.out.println(app);
        System.out.println(app.getIntersectingPoints("line1", "line2"));
        System.out.println(app.getIntersectingPoints("square1", "square2"));
        app.moveShape("line1", 2, 2);

    }
}
