package competition.algorithms.vtsp.bruteforce;

import competition.algorithms.City;
import competition.algorithms.Point;
import competition.algorithms.vtsp.multipointastar.hashset.MultiPointAStarBound;
import competition.algorithms.vtsp.multipointastar.hashset.limitedview.LimitedViewMultiPointAStar;

import java.util.ArrayList;
import java.util.List;

public class BranchAndBound {

    public static List<Point> branchAndBound(List<City> cities){
        List<Point> trajectory = LimitedViewMultiPointAStar.limitedViewMultiPointAStar(cities);
        List<Point> bestTrajectory = MultiPointAStarBound.multiPointAStarBound(cities, trajectory.size()-2);
        if (bestTrajectory.isEmpty()){
            bestTrajectory = trajectory;
        }
        int bestCost = bestTrajectory.size() - 1;
        List<Integer> visitOrder = new ArrayList<>();
        for (int i=1; i<cities.size(); i++){
            visitOrder.add(i);
        }
        List<City> visitOrderCities;
        int cost;
        while (nextVisitOrder(visitOrder)){
            visitOrderCities = createVisitOrderCities(visitOrder, cities);
            trajectory = MultiPointAStarBound.multiPointAStarBound(visitOrderCities, bestCost - 1);
            if (trajectory.size() != 0) {
                cost = trajectory.size() - 1;
                if (cost < bestCost) {
                    bestTrajectory = List.copyOf(trajectory);
                    bestCost = cost;
                }
            }
        }
        return bestTrajectory;
    }

    public static List<Point> branchAndBound(List<City> cities, int bound){
        List<Point> bestTrajectory = MultiPointAStarBound.multiPointAStarBound(cities, bound);
        int bestCost = bestTrajectory.size() - 1;
        List<Integer> visitOrder = new ArrayList<>();
        for (int i=1; i<cities.size(); i++){
            visitOrder.add(i);
        }
        List<City> visitOrderCities;
        List<Point> trajectory;
        int cost;
        while (nextVisitOrder(visitOrder)){
            visitOrderCities = createVisitOrderCities(visitOrder, cities);
            trajectory = MultiPointAStarBound.multiPointAStarBound(visitOrderCities, bestCost - 1);
            if (trajectory.size() != 0) {
                cost = trajectory.size() - 1;
                if (cost < bestCost) {
                    bestTrajectory = List.copyOf(trajectory);
                    bestCost = cost;
                }
            }
        }
        return bestTrajectory;
    }

    // from Cyril Gavoille's course "Techniques Algorithmiques et Programmation"
    public static boolean nextVisitOrder(List<Integer> visitOrder){
        int n = visitOrder.size();
        int i = -1;
        int j;
        for (j = 0; j < n - 1; j++)
            if (visitOrder.get(j) < visitOrder.get(j + 1))
                i = j;
        if (i == -1) {
            return false;
        }
        j = i + 1;
        while (j < n && visitOrder.get(i) < visitOrder.get(j))
            j++;
        j--;
        swap(visitOrder, i, j);
        for (++i; i < n - 1; i++, n--)
            swap(visitOrder, i, n - 1);
        return true;
    }

    public static List<City> createVisitOrderCities(List<Integer> visitOrder, List<City> cities) {
        List<City> result = new ArrayList<>();
        result.add(cities.get(0));
        for (int i=0; i<visitOrder.size(); i++)
            result.add(cities.get(visitOrder.get(i)));
        return result;
    }

    public static void swap(List visitOrder, int i, int j) {
        Object aux = visitOrder.get(i);
        visitOrder.set(i, visitOrder.get(j));
        visitOrder.set(j, aux);
    }
}
