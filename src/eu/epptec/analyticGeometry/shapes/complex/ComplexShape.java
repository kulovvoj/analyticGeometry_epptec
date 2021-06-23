package eu.epptec.analyticGeometry.shapes.complex;

import eu.epptec.analyticGeometry.shapes.Shape;
import eu.epptec.analyticGeometry.shapes.elementary.BasicShape;
import eu.epptec.analyticGeometry.shapes.elementary.Point;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

abstract class ComplexShape implements Shape {
    Set<BasicShape> components = new TreeSet<>();
    Point center = new Point(0, 0);

    public ComplexShape() {}

    public Set<BasicShape> getComponents() {
        return components;
    }

    @Override
    public Set<BasicShape> getIntersections(Shape other) {
        return components.stream()
                .flatMap(elem -> other.getIntersections(elem).stream())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public ComplexShape move(double x, double y) {
        ComplexShape movedShape = this;
        Set<BasicShape> movedComponents = components.stream()
                .map(component -> component.move(x, y))
                .collect(Collectors.toCollection(TreeSet::new));
        Point movedCenter = center.move(x, y);

        movedShape.components = movedComponents;
        movedShape.center = movedCenter;

        return movedShape;
    }

    @Override
    public ComplexShape rotate(double angle) {
        return rotate(angle, getCenter());
    }

    @Override
    public ComplexShape rotate(double angle, Point pivot) {
        ComplexShape rotatedShape = this;
        Set<BasicShape> rotatedComponents = components.stream()
                .map(component -> component.rotate(angle, pivot))
                .collect(Collectors.toCollection(TreeSet::new));
        Point movedCenter = center.rotate(angle, pivot);

        rotatedShape.components = rotatedComponents;
        rotatedShape.center = movedCenter;
        
        return rotatedShape;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof ComplexShape otherShape))
            return false;
        return components.equals(otherShape.getComponents());
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " - " + components;
    }

    @Override
    public Point getCenter() {
        return center;
    }
}
