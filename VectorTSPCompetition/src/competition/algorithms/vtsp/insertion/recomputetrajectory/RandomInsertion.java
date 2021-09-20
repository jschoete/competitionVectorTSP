package competition.algorithms.vtsp.insertion.recomputetrajectory;

import competition.algorithms.City;
import competition.algorithms.Point;
import competition.algorithms.etsp.insertion.Insertion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static competition.algorithms.vtsp.multipointastar.hashset.MultiPointAStar.multiPointAStar;
import static competition.algorithms.vtsp.multipointastar.hashset.MultiPointAStarBound.multiPointAStarBound;

public class RandomInsertion {

    public static List<Point> randomInsertion(List<City> cities){
        List<City> citiesCopy = new ArrayList<>(cities);
        List<City> visitOrder = new LinkedList<>(); //LinkedLists allow for easier insertion/deletion of elements
        visitOrder.add(citiesCopy.remove(0));
        Random r = new Random();
        int i;
        City c;
        int cost = 0;
        while (!citiesCopy.isEmpty()){
            i = r.nextInt(citiesCopy.size());
            c = citiesCopy.remove(i);
            cost = insertCity(c, visitOrder);
        }
        return multiPointAStarBound(new ArrayList<>(visitOrder), cost);
    }

    public static List<Point> randomInsertion(List<City> cities, Random r){
        List<City> citiesCopy = new ArrayList<>(cities);
        List<City> visitOrder = new LinkedList<>(); //LinkedLists allow for easier insertion/deletion of elements
        visitOrder.add(citiesCopy.remove(0));
        int i;
        City c;
        int cost = 0;
        while (!citiesCopy.isEmpty()){
            i = r.nextInt(citiesCopy.size());
            c = citiesCopy.remove(i);
            cost = insertCity(c, visitOrder);
//            System.out.println("added city " + c.toString() + ", " + citiesCopy.size() + " more cities to add");
        }
        return multiPointAStarBound(new ArrayList<>(visitOrder), cost);
    }

    public static int insertCity(City c, List<City> visitOrder){
        int n = visitOrder.size();
        int bestIndex = Insertion.bestInsertion(c, visitOrder); //ETSP guidance, to avoid outOfMemory.
        visitOrder.add(bestIndex, c);
        int bestCost = multiPointAStar(visitOrder).size() - 1;
        visitOrder.remove(bestIndex);
        int cost;
        List<Point> trajectory;
        for (int i=1; i<=n; i++){
            if (i == bestIndex)
                continue;
            visitOrder.add(i, c);
            trajectory = multiPointAStarBound(visitOrder, bestCost - 1);
            cost = trajectory.size()-1;
            if (cost != -1 && cost < bestCost){
                bestCost = cost;
                bestIndex = i;
            }
            visitOrder.remove(i);
        }
        visitOrder.add(bestIndex, c);
        return bestCost;
    }
}
