package competition.algorithms.vtsp.multipointastar.hashset;

import competition.algorithms.City;
import competition.algorithms.Point;
import competition.algorithms.vtsp.multipointastar.Configuration;
import competition.algorithms.vtsp.multipointastar.Estimation;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import static competition.algorithms.vtsp.multipointastar.nohashset.MultiPointAStar.*;

public class MultiPointAStar {

    //TODO Java's PriorityQueue uses a binary heap.
    // Implementing our own PrioQueue using a binomial or Fibonacci heap may result in better running time,
    // even though our graph is sparse (see https://stackoverflow.com/questions/15815744/dijkstra-on-java-getting-interesting-results-using-a-fibonacci-heap-vs-priorit)

    public static List<Point> multiPointAStar(List<City> cities){
        int n = cities.size();
        Point start = cities.get(0);
        Configuration c = new Configuration(start.getX(), start.getY(), 0, 0);
        Set<Configuration> visited = new HashSet<>();
        PriorityQueue<Configuration> q = new PriorityQueue<>();
        q.add(c);
        while(!q.isEmpty()){
            c = q.poll();
            if (visited.contains(c))
                continue;
            visited.add(c);
            if (isEnd(c, start, n))
                break;
            Configuration[] children = c.getChildren();
            for (int i=0; i<9; i++){
                Configuration child = children[i];
                updateVisited(child, c, cities, n);
                child.setEstimation((byte)(child.getLayer() + Estimation.estimateRemainingTrajectory(child, cities)));
                q.add(children[i]);
            }
        }
        return rewind(c);
    }

}
