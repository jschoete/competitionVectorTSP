package competition.algorithms.etsp.insertion;

import competition.algorithms.City;
import competition.algorithms.etsp.Evaluation;

import java.util.List;

public class Insertion {

    public static int bestInsertion(City c, List<City> visitOrder){
        int n = visitOrder.size();
        int bestI = 1;
        visitOrder.add(bestI, c);
        double bestCost = Evaluation.cost(visitOrder);
        visitOrder.remove(bestI);
        double cost;
        for (int i=2; i<=n; i++){
            visitOrder.add(i, c);
            cost = Evaluation.cost(visitOrder);
            visitOrder.remove(i);
            if (cost < bestCost){
                bestCost = cost;
                bestI = i;
            }
        }
        return bestI;
    }
}
