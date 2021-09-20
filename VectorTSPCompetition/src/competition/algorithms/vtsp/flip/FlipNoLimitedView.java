package competition.algorithms.vtsp.flip;

import competition.algorithms.City;
import competition.algorithms.Point;
import competition.algorithms.vtsp.multipointastar.hashset.MultiPointAStar;
import competition.algorithms.vtsp.multipointastar.hashset.MultiPointAStarBound;

import java.util.ArrayList;
import java.util.List;

public class FlipNoLimitedView {

    public static List<Point> flip(List<City> cities){
        int n = cities.size();
        List<City> bestVisitOrder = List.copyOf(cities);
        List<City> visitOrder;
        List<Point> bestTrajectory = MultiPointAStar.multiPointAStar(bestVisitOrder);
        List<Point> trajectory;
        int bestCost = bestTrajectory.size() - 1;
        int cost;
        boolean improved = true;
        while (improved){
            improved = false;
            for (int i=0; i<n-1; i++){
                for (int j=i+2; j<n; j++){
                    visitOrder = flipVisitOrder(bestVisitOrder, i, j);
                    trajectory = MultiPointAStarBound.multiPointAStarBound(visitOrder, bestCost - 1);
                    cost = trajectory.size() - 1;
                    if (cost != - 1 && cost < bestCost){
                        bestCost = cost;
                        bestTrajectory = trajectory;
                        bestVisitOrder = visitOrder;
                        improved = true;
                        break;
                    }
                }
                if (improved){
                    break;
                }
            }
        }
        return bestTrajectory;
    }

    public static List<City> flipVisitOrder(List<City> visitOrder, int i, int j) {
        List<City> result = new ArrayList<>();
        for (int k=0; k<=i; k++)
            result.add(visitOrder.get(k));
        for (int k=j; k>=i+1; k--)
            result.add(visitOrder.get(k));
        for (int k=j+1; k<visitOrder.size(); k++)
            result.add(visitOrder.get(k));
        return result;
    }

}
