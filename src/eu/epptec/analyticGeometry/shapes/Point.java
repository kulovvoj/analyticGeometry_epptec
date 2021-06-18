package eu.epptec.analyticGeometry.shapes;

public class Point {
    private double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void Move(double x, double y) {
        this.x += x;
        this.y += y;
    }
}
