package competition.algorithms.vtsp.multipointastar.nohashset;

import competition.algorithms.City;
import competition.algorithms.Point;
import competition.algorithms.vtsp.multipointastar.Configuration;
import competition.algorithms.vtsp.multipointastar.Estimation;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static competition.algorithms.vtsp.multipointastar.nohashset.MultiPointAStar.*;

public class MultiPointAStarBound {

    // returns empty list if no bounded size trajectory is found
    public static List<Point> aStarBound(List<City> cities, int bound){
        Point start = cities.get(0);
        int n = cities.size();
        Configuration c = new Configuration(start.getX(), start.getY(), (byte)0, (byte)0);
        PriorityQueue<Configuration> q = new PriorityQueue<>();
        q.add(c);
        while(!q.isEmpty()){
            c = q.poll();
            if (isEnd(c, start, n))
                break;
            Configuration[] children = c.getChildren();
            for (int i=0; i<9; i++){
                Configuration child = children[i];
                updateVisited(child, c, cities, n);
                child.setEstimation((byte)(child.getLayer() + Estimation.estimateRemainingTrajectory(child, cities)));
                if (child.getEstimation() <= bound)
                    q.add(children[i]);
            }
        }
        if (q.isEmpty())
            return new ArrayList<>();
        return rewind(c);
    }

}
