package competition.algorithms.vtsp.insertion.storingtrajectories;

import competition.algorithms.Point;

import java.util.List;

public class Insertion {

    private final int cityIndex;
    private final int insertIndex;
    private final List<Point> trajectory;

    public Insertion() {
        this.cityIndex = -1;
        this.insertIndex = -1;
        this.trajectory = null;
    }

    public Insertion(int cityIndex, int insertIndex, List<Point> trajectory) {
        this.cityIndex = cityIndex;
        this.insertIndex = insertIndex;
        this.trajectory = trajectory;
    }

    public int getCityIndex() {
        return cityIndex;
    }

    public int getInsertIndex() {
        return insertIndex;
    }

    public List<Point> getTrajectory() {
        return trajectory;
    }

    public int getCost() {
        if (trajectory == null)
            return -1;
        return trajectory.size() - 1;
    }

    @Override
    public String toString() {
        return "Insertion{" +
                "cityIndex=" + cityIndex +
                ", insertIndex=" + insertIndex +
                ", trajectory=" + trajectory.toString() +
                '}';
    }
}
