package competition.algorithms.vtsp.multipointastar.nohashset;

import competition.algorithms.City;
import competition.algorithms.Point;
import competition.algorithms.Geometry;
import competition.algorithms.vtsp.multipointastar.Configuration;
import competition.algorithms.vtsp.multipointastar.Estimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MultiPointAStar {

    //TODO Java's PriorityQueue uses a binary heap.
    // Implementing our own PrioQueue using a binomial or Fibonacci heap may result in better running time,
    // even though our graph is sparse (see https://stackoverflow.com/questions/15815744/dijkstra-on-java-getting-interesting-results-using-a-fibonacci-heap-vs-priorit)

    public static List<Point> aStar(List<City> cities){
        Point start = cities.get(0);
        int n = cities.size();
        Configuration c = new Configuration(start.getX(), start.getY(), (byte)0, (byte)0);
        PriorityQueue<Configuration> q = new PriorityQueue<>();
        q.add(c);
        int cntr = 0;
        while(!q.isEmpty()){
            cntr++;
//            System.out.println(cntr);
//            if (cntr % 1000 == 0)
//                System.out.println("counter at " + cntr + ": configuration visited " + c.getVisited() + " cities");
            c = q.poll();
            if (isEnd(c, start, n))
                break;
            Configuration[] children = c.getChildren();
            for (int i=0; i<9; i++){
                Configuration child = children[i];
                updateVisited(child, c, cities, n);
                child.setEstimation((byte) (child.getLayer() + Estimation.estimateRemainingTrajectory(child, cities)));
                q.add(children[i]);
            }
        }
        return rewind(c);
    }

    public static void updateVisited(Configuration child, Configuration parent, List<City> cities, int n) {
        int v = child.getVisited();
        //percentages to make sure city i is not visited after city (i+1), which messes up the estimation function.
        double percentage = 0;
        double newPercentage;
        while (v != n){
            Point city = cities.get(v);
            newPercentage = Geometry.percentageOnSegment(city.getX(), city.getY(), parent.getX(), parent.getY(), child.getX(), child.getY());
            if (newPercentage >= percentage) {
                percentage = newPercentage;
                v++;
            }
            else
                break;
        }
        child.setVisited(v);
    }

    public static boolean isEnd(Configuration c, Point start, int n) {
        return c.getVisited() == n
                && c.getX() == start.getX()
                && c.getY() == start.getY()
                && c.getDx() == 0
                && c.getDy() == 0;
    }

    public static List<Point> rewind(Configuration c) {
        List<Point> result = new ArrayList<>();
        while (c != null){
            Point p = new Point(c.getX(), c.getY());
            result.add(p);
            c = c.getParent();
        }
        Collections.reverse(result);
        return result;
    }
}
