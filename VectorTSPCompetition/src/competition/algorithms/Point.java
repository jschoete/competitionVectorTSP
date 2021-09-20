package competition.algorithms;

public class Point {

    private short x;
    private short y;

    public Point(short x, short y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y) {
        this.x = (short)x;
        this.y = (short)y;
    }

    public short getX() {
        return x;
    }

    public void setX(int x) {
        this.x = (short)x;
    }

    public void setX(short x) {
        this.x = x;
    }

    public short getY() {
        return y;
    }

    public void setY(int y) {
        this.y = (short)y;
    }

    public void setY(short y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double distance(Point point) {
        return Math.sqrt(Math.pow(this.x - point.x, 2) + Math.pow(this.y - point.y, 2));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point){
            Point p = (Point) obj;
            return this.x == p.x && this.y == p.y;
        }
        return false;
    }
}
