package competition.algorithms.vtsp.insertion.storingtrajectories;

import competition.algorithms.City;
import competition.algorithms.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static competition.algorithms.etsp.insertion.Insertion.bestInsertion;
import static competition.algorithms.vtsp.multipointastar.hashset.MultiPointAStarBound.multiPointAStarBound;
import static competition.algorithms.vtsp.multipointastar.hashset.limitedview.LimitedViewMultiPointAStar.limitedViewMultiPointAStar;

public class RandomInsertion {

    public static List<Point> randomInsertion(List<City> cities){
        return randomInsertion(cities, new Random());
    }

    public static List<Point> randomInsertion(List<City> cities, Random r){
        List<City> citiesCopy = new ArrayList<>(cities);
        List<City> visitOrder = new LinkedList<>(); // LinkedLists allow for easier insertion/deletion of elements
        visitOrder.add(citiesCopy.remove(0));
        int i;
        City c;
        List<Point> trajectory = new ArrayList<>();
        while (!citiesCopy.isEmpty()){
            i = r.nextInt(citiesCopy.size());
            c = citiesCopy.remove(i);
            if (citiesCopy.isEmpty()) {
                trajectory = insertCity(c, visitOrder);
            } else {
                insertCity(c, visitOrder);
            }
        }
        return trajectory;
    }

    public static List<Point> insertCity(City c, List<City> visitOrder){
        int n = visitOrder.size();
        int bestIndex = bestInsertion(c, visitOrder); //ETSP guidance, to avoid outOfMemory.
        visitOrder.add(bestIndex, c);
        List<Point> trajectory = limitedViewMultiPointAStar(visitOrder);
        List<Point> bestTrajectory = multiPointAStarBound(visitOrder, trajectory.size()-2);
        if (bestTrajectory.isEmpty())
            bestTrajectory = trajectory;
        int bestCost = bestTrajectory.size() - 1;
        visitOrder.remove(bestIndex);
        int cost;
        for (int i=1; i<=n; i++){
            if (i == bestIndex)
                continue;
            visitOrder.add(i, c);
            trajectory = multiPointAStarBound(visitOrder, bestCost - 1);
            cost = trajectory.size()-1;
            if (cost != -1 && cost < bestCost){
                bestCost = cost;
                bestIndex = i;
                bestTrajectory = trajectory;
            }
            visitOrder.remove(i);
        }
        visitOrder.add(bestIndex, c);
        return bestTrajectory;
    }
}
