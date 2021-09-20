package competition.algorithms.vtsp.multipointastar.hashset.limitedview;

import competition.algorithms.City;
import competition.algorithms.Point;
import competition.algorithms.Geometry;
import competition.algorithms.vtsp.multipointastar.Configuration;
import competition.algorithms.vtsp.multipointastar.Estimation;

import java.util.*;

import static competition.algorithms.vtsp.multipointastar.nohashset.MultiPointAStar.rewind;

public class LimitedViewMultiPointAStarBound {
	
	private static int viewLimit = 5; //number of successive cities treated
	
	public static List<Point> limitedViewMultiPointAStarBound(List<City> cities, int bound){
		return limitedViewMultiPointAStarBound(cities, bound, viewLimit);
	}
	
	public static List<Point> limitedViewMultiPointAStarBound(List<City> cities, int bound, int viewLimit){
		int n = cities.size();
		City start = cities.get(0);
		Configuration c = new Configuration(start.getX(), start.getY(), 0, 0);
		if (Estimation.estimateRemainingTrajectory(c, cities) > bound)
			return new ArrayList<>();
		Set<Configuration> visited = new HashSet<>();
		PriorityQueue<Configuration> q = new PriorityQueue<>();
		int indexFirstCity; //of limited view
		int indexLastCity; //of limited view
		for (int i=0; i<n-viewLimit+2; i++){
//			System.out.println(i);
			indexFirstCity = i;
			indexLastCity = (i + viewLimit - 1) % n;
			visited.clear();
			q.clear();
			q.add(c);
			boolean found = false;
			while(!q.isEmpty()) {
				c = q.poll();
				if (visited.contains(c))
					continue;
				visited.add(c);
				if (isEnd(c, cities, indexLastCity)) {
					found = true;
					if (indexLastCity != 0)
						c = rewindUntilVisited(c, indexFirstCity + 2);
					break;
				}
				Configuration[] children = c.getChildren();
				Configuration child;
				for (int j = 0; j < 9; j++) {
					child = children[j];
					updateVisited(child, c, cities, indexLastCity);
					child.setEstimation((child.getLayer() + Estimation.estimateRemainingTrajectory(child, cities, indexLastCity)));
					if (child.getEstimation() <= bound)
						q.add(child);
				}
			}
			if (!found)
				return new ArrayList<>();
		}
		return rewind(c);
	}
	
	public static boolean isEnd(Configuration c, List<City> cities, int indexLastCity) {
		Point lastCity = cities.get(indexLastCity);
		if (indexLastCity == 0)
			return c.getVisited() == cities.size()
					&& c.getX() == lastCity.getX()
					&& c.getY() == lastCity.getY()
					&& c.getDx() == 0
					&& c.getDy() == 0;
		return c.getVisited() == indexLastCity
				&& c.getX() == lastCity.getX()
				&& c.getY() == lastCity.getY()
				&& c.getDx() == 0
				&& c.getDy() == 0;
	}
	
	private static Configuration rewindUntilVisited(Configuration c, int i) {
		Configuration parent = c, child = c;
		while (parent.getVisited() > i-1){
			child = parent;
			parent = child.getParent();
		}
		return child;
	}
	
	public static void updateVisited(Configuration child, Configuration parent, List<City> cities, int indexLastCity) {
		int v = child.getVisited();
		//percentages to make sure city i is not visited after city (i+1), which messes up the estimation function.
		double percentage = 0;
		double newPercentage;
		if (indexLastCity == 0)
			indexLastCity = cities.size();
		while (v < indexLastCity){
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
}
