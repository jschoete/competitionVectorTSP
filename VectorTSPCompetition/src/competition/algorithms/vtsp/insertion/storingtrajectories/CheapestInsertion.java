package competition.algorithms.vtsp.insertion.storingtrajectories;

import competition.algorithms.City;
import competition.algorithms.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static competition.algorithms.etsp.insertion.Insertion.bestInsertion;
import static competition.algorithms.vtsp.multipointastar.hashset.MultiPointAStarBound.multiPointAStarBound;
import static competition.algorithms.vtsp.multipointastar.hashset.limitedview.LimitedViewMultiPointAStar.limitedViewMultiPointAStar;

public class CheapestInsertion {

    public static List<Point> cheapestInsertion(List<City> cities){
        List<City> citiesCopy = new ArrayList<>(cities);
        List<City> visitOrder = new LinkedList<>(); //LinkedLists allow for easier insertion/deletion of elements
        visitOrder.add(citiesCopy.remove(0));
        Insertion bestInsertion = new Insertion();
        Insertion insertion;
        City c;
        while (!citiesCopy.isEmpty()){
            c = citiesCopy.get(0);
            bestInsertion = evaluateInsertion(c, 0, visitOrder);
            for (int j=1; j<citiesCopy.size(); j++){
                c = citiesCopy.get(j);
                insertion = evaluateInsertionBound(c, j, visitOrder, bestInsertion.getCost() - 1);
                if (insertion.getCost() != -1){
                    bestInsertion = insertion;
                }
            }
            visitOrder.add(bestInsertion.getInsertIndex(), citiesCopy.remove(bestInsertion.getCityIndex()));
//            System.out.println("added city, " + citiesCopy.size() + " remaining");
        }
        return bestInsertion.getTrajectory();
    }

    public static Insertion evaluateInsertion(City c, int cityIndex, List<City> visitOrder){
        int n = visitOrder.size();
        int bestIndex = bestInsertion(c, visitOrder); //ETSP guidance, to avoid outOfMemory.
        visitOrder.add(bestIndex, c);
        List<Point> trajectory = limitedViewMultiPointAStar(visitOrder);
        List<Point> bestTrajectory = multiPointAStarBound(visitOrder, trajectory.size()-2);
        if (bestTrajectory.isEmpty()){
            bestTrajectory = trajectory;
        }
        int bestCost = bestTrajectory.size() - 1;
        visitOrder.remove(bestIndex);
        int cost;
        for (int i=1; i<=n; i++){
            if (i == bestIndex)
                continue;
            visitOrder.add(i, c);
            trajectory = multiPointAStarBound(visitOrder, bestCost - 1);
            cost = trajectory.size() - 1;
            if (cost != -1){
                bestTrajectory = trajectory;
                bestCost = cost;
                bestIndex = i;
            }
            visitOrder.remove(i);
        }
        return new Insertion(cityIndex, bestIndex, bestTrajectory);
    }

    public static Insertion evaluateInsertionBound(City c, int cityIndex, List<City> visitOrder, int bound){
        int n = visitOrder.size();
        visitOrder.add(1, c);
        List<Point> bestTrajectory = multiPointAStarBound(visitOrder, bound);
        int bestCost = bestTrajectory.size() - 1;
        int bestIndex = 1;
        visitOrder.remove(1);
        List<Point> trajectory;
        int cost;
        for (int i=2; i<=n; i++){
            visitOrder.add(i, c);
            trajectory = multiPointAStarBound(visitOrder, bestCost - 1);
            cost = trajectory.size() - 1;
            if (cost != -1){
                bestTrajectory = trajectory;
                bestCost = cost;
                bestIndex = i;
            }
            visitOrder.remove(i);
        }
        return new Insertion(cityIndex, bestIndex, bestTrajectory);
    }

}
