package competition.algorithms.vtsp.insertion.recomputetrajectory;

public class Insertion {

    private final int cityIndex;
    private final int insertIndex;
    private final int cost;

    public Insertion() {
        this.cityIndex = -1;
        this.insertIndex = -1;
        this.cost = -1;
    }

    public Insertion(int cityIndex, int insertIndex, int cost) {
        this.cityIndex = cityIndex;
        this.insertIndex = insertIndex;
        this.cost = cost;
    }

    public int getCityIndex() {
        return cityIndex;
    }

    public int getInsertIndex() {
        return insertIndex;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Insertion{" +
                "city=" + cityIndex +
                ", index=" + insertIndex +
                ", cost=" + cost +
                '}';
    }
}
