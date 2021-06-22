package eu.epptec.analyticGeometry.shapes.elementary;

import eu.epptec.analyticGeometry.shapes.Shape;

public interface BasicShape extends Shape, Comparable<BasicShape> {
    int compPrec = 1000000;

    boolean equals(Object other);

    @Override
    int compareTo(BasicShape other);
}
