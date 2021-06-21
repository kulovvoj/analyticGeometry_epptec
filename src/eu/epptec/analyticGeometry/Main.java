package eu.epptec.analyticGeometry;

import eu.epptec.analyticGeometry.application.Application;
import eu.epptec.analyticGeometry.shapes.basic.Circle;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        app.addShape("circle", new Circle(new Point(0, 0), 3));
        app.addShape("point", new Point(3, 0));
        System.out.println(app.toString());
    }
}
