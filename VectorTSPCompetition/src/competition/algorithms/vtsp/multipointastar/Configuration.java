package competition.algorithms.vtsp.multipointastar;

public class Configuration implements Comparable {

    private final short x;
    private final short y;
    private final byte dx;
    private final byte dy;
    private final byte layer; // amount of parents
    private byte visited; // number of cities visited
    private byte estimation;
    private final Configuration parent;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Configuration){
            Configuration c = (Configuration) obj;
            return c.x == this.x && c.y == this.y && c.dx == this.dx && c.dy == this.dy && c.visited == this.visited;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ((int)x) + 100*((int)y) + 10000*((int)dx) + 1000000*((int)dy) + 100000000*((int)visited);
    }

    public Configuration(int x, int y, int dx, int dy) {
        this.x = (short)x;
        this.y = (short)y;
        this.dx = (byte)dx;
        this.dy = (byte)dy;
        this.parent = null;
        this.layer = 0;
        this.visited = 1; // supposing (x, y) is a city
    }

    public Configuration(int x, int y, int dx, int dy, Configuration parent) {
        this.x = (short)x;
        this.y = (short)y;
        this.dx = (byte)dx;
        this.dy = (byte)dy;
        this.parent = parent;
        this.layer = (byte) (parent.getLayer() + 1);
        this.visited = parent.visited;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public byte getDx() {
        return dx;
    }

    public byte getDy() {
        return dy;
    }

    public int getLayer() {
        return signedByteToInt(layer);
    }

    public Configuration getParent() {
        return parent;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = (byte) visited;
    }

    public int getEstimation() {
        return signedByteToInt(estimation);
    }

    public void setEstimation(int estimation) {
        this.estimation = (byte)estimation;
    }

    public Configuration[] getChildren(){
        Configuration[] result = new Configuration[9];
        int k = 0;
        for (int xx=-1; xx<=1; xx++){
            for (int yy=-1; yy<=1; yy++){
                result[k] = new Configuration(
                        (this.x + this.dx + xx),
                        (this.y + this.dy + yy),
                        (this.dx + xx),
                        (this.dy + yy),
                        this);
                k++;
            }
        }
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Configuration){
            Configuration c = (Configuration)o;
            return this.getEstimation() - c.getEstimation();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + dx + ", " + dy + ")";
    }

    public static int signedByteToInt(byte b){
        return b & 0xFF;
    }
}
