package competition.algorithms.vtsp.multipointastar.hashset;

import competition.algorithms.City;
import competition.algorithms.Point;
import competition.algorithms.vtsp.multipointastar.Configuration;
import competition.algorithms.vtsp.multipointastar.Estimation;

import java.util.*;

import static competition.algorithms.vtsp.multipointastar.nohashset.MultiPointAStar.*;

public class MultiPointAStarBound {

    // returns empty list if no bounded size trajectory is found
    public static List<Point> multiPointAStarBound(List<City> cities, int bound){
        Point start = cities.get(0);
        Configuration c = new Configuration(start.getX(), start.getY(), 0, 0);
        if (Estimation.estimateRemainingTrajectory(c, cities) > bound)
            return new ArrayList<>();
        PriorityQueue<Configuration> q = new PriorityQueue<>();
        q.add(c);
        int n = cities.size();
        Set<Configuration> visited = new HashSet<>();
        boolean found = false;
        while(!q.isEmpty()){
            c = q.poll();
            if (visited.contains(c))
                continue;
            visited.add(c);
            if (isEnd(c, start, n)) {
                found = true;
                break;
            }
            Configuration[] children = c.getChildren();
            for (int i=0; i<9; i++){
                Configuration child = children[i];
                updateVisited(child, c, cities, n);
                child.setEstimation((byte)(child.getLayer() + Estimation.estimateRemainingTrajectory(child, cities)));
                if (child.getEstimation() <= bound)
                    q.add(children[i]);
            }
        }
        if (found)
            return rewind(c);
        return new ArrayList<>();
    }

}
