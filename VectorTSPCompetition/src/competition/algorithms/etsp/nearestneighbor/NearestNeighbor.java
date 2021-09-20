package competition.algorithms.etsp.nearestneighbor;

import competition.algorithms.City;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighbor {

    public static List<City> nearestNeighbor(List<City> cities){
        List<City> citiesCopy = new ArrayList<>(cities);
        List<City> result = new ArrayList<>();
        result.add(citiesCopy.remove(0));
        City last, bestC, c;
        double bestDist, dist;
        int bestI, i;
        while (!citiesCopy.isEmpty()){
            last = result.get(result.size()-1);
            c = citiesCopy.get(0);
            bestI = 0;
            bestDist = last.distance(c);
            for (i=1; i<citiesCopy.size(); i++){
                c = citiesCopy.get(i);
                dist = last.distance(c);
                if (dist < bestDist){
                    bestDist = dist;
                    bestI = i;
                }
            }
            result.add(citiesCopy.remove(bestI));
        }
        return result;
    }

}
