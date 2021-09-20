package competition.algorithms.vtsp.insertion.recomputetrajectory;

import competition.algorithms.City;
import competition.algorithms.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static competition.algorithms.vtsp.multipointastar.hashset.MultiPointAStar.multiPointAStar;
import static competition.algorithms.vtsp.multipointastar.hashset.MultiPointAStarBound.multiPointAStarBound;

public class CheapestInsertion {

    public static List<Point> cheapestInsertion(List<City> cities){
        List<City> citiesCopy = new ArrayList<>(cities);
        List<City> visitOrder = new LinkedList<>(); //LinkedLists allow for easier insertion/deletion of elements
        visitOrder.add(citiesCopy.remove(0));
        Insertion bestI = new Insertion();
        Insertion i;
        City c;
        while (!citiesCopy.isEmpty()){
            c = citiesCopy.get(0);
            bestI = evaluateInsertion(c, 0, visitOrder);
            for (int j=1; j<citiesCopy.size(); j++){
                c = citiesCopy.get(j);
                i = evaluateInsertionBound(c, j, visitOrder, bestI.getCost() - 1);
                if (i.getCost() != -1){
                    bestI = i;
                }
            }
            visitOrder.add(bestI.getInsertIndex(), citiesCopy.remove(bestI.getCityIndex()));
//            System.out.println("added city, " + citiesCopy.size() + " remaining");
        }
        return multiPointAStarBound(new ArrayList<>(visitOrder), bestI.getCost());
    }

    public static Insertion evaluateInsertion(City c, int cityIndex, List<City> visitOrder){
        int n = visitOrder.size();
        visitOrder.add(1, c);
        int bestCost = multiPointAStar(visitOrder).size() - 1;
        int bestIndex = 1;
        visitOrder.remove(1);
        int cost;
        for (int i=2; i<=n; i++){
            visitOrder.add(i, c);
            cost = multiPointAStarBound(visitOrder, bestCost - 1).size() - 1;
            if (cost != -1){
                bestCost = cost;
                bestIndex = i;
            }
            visitOrder.remove(i);
        }
        return new Insertion(cityIndex, bestIndex, bestCost);
    }

    public static Insertion evaluateInsertionBound(City c, int cityIndex, List<City> visitOrder, int bound){
        int n = visitOrder.size();
        visitOrder.add(1, c);
        int bestCost = multiPointAStarBound(visitOrder, bound).size() - 1;
        int bestIndex = 1;
        visitOrder.remove(1);
        int cost;
        for (int i=2; i<=n; i++){
            visitOrder.add(i, c);
            cost = multiPointAStarBound(visitOrder, bestCost - 1).size() - 1;
            if (cost != -1){
                bestCost = cost;
                bestIndex = i;
            }
            visitOrder.remove(i);
        }
        return new Insertion(cityIndex, bestIndex, bestCost);
    }

}
