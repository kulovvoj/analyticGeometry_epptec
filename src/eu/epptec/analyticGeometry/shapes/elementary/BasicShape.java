package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.Shape;

public interface BasicShape extends Shape, Comparable<BasicShape> {
    int compPrec = 1000000;

    boolean equals(Object other);

    @Override
    int compareTo(BasicShape other);

    @Override
    public BasicShape move(double x, double y);

    @Override
    public BasicShape rotate(double angle);

    @Override
    public BasicShape rotate(double angle, Point pivot);
}
