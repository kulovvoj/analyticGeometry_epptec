package eu.epptec.analyticGeometry.application;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.Set;

public class Application {
    Space space = new Space();

    public void addShape(String name, Shape shape) {
        space.addShape(name, shape);
    }

    public void removeShape(String name) {
        space.removeShape(name);
    }

    public void moveShape(String name, double x, double y) {
        Shape movedShape = space.getShape(name).move(x, y);
        space.addShape(name, movedShape);
    }

    public void rotateShape(String name, double angle) {
        Shape rotatedShape = space.getShape(name).rotate(angle);
        space.addShape(name, rotatedShape);
    }

    public void rotateShape(String name, double angle, Point pointOfRotation) {
        Shape rotatedShape = space.getShape(name).rotate(angle, pointOfRotation);
        space.addShape(name, rotatedShape);
    }

    public Set<Point> getIntersectingPoints(String name1, String name2) {
        return space.getShape(name1).getIntersectingPoints(space.getShape(name2));
    }

    @Override
    public String toString() {
        return space.toString();
    }
}
