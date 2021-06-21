package eu.epptec.analyticGeometry.application;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

public class Application {
    Space space = new Space();

    public void addShape(String name, Shape shape) {
        space.addShape(name, shape);
    }

    public void removeShape(String name) {
        space.removeShape(name);
    }

    public void moveShape(String name, double x, double y) {
        space.getShape(name).move(x, y);
    }

    public void rotateShape(String name, double angle) {
        space.getShape(name).rotate(angle);
    }

    public void rotateShape(String name, double angle, Point pointOfRotation) {
        space.getShape(name).rotate(angle, pointOfRotation);
    }

    public void getIntersectingPoints(String name1, String name2) {
        space.getShape(name1).getIntersectingPoints(space.getShape(name2));
    }

    @Override
    public String toString() {
        return space.toString();
    }
}
