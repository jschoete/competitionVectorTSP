package competition.algorithms.vtsp.multipointastar;

import competition.algorithms.City;

import java.util.ArrayList;
import java.util.List;

public class Estimation {
	
	public static int estimateRemainingTrajectory(Configuration c, List<City> cities) {
		return estimateRemainingTrajectory(c, cities, 0);
	}
	
	public static int estimateRemainingTrajectory(Configuration c, List<City> cities, int indexLastCity) {
		List<Short> citiesX = new ArrayList<>();
		List<Short> citiesY = new ArrayList<>();
		if (indexLastCity == 0) {
			for (int i = c.getVisited(); i<cities.size(); i++) {
				citiesX.add(cities.get(i).getX());
				citiesY.add(cities.get(i).getY());
			}
			citiesX.add(cities.get(0).getX());
			citiesY.add(cities.get(0).getY());
		}
		for (int i = c.getVisited(); i <= indexLastCity; i++) {
			citiesX.add(cities.get(i).getX());
			citiesY.add(cities.get(i).getY());
		}
		int costX = VectorTSP1D.costVectorTSP1D(c.getX(), c.getDx(), citiesX);
		int costY = VectorTSP1D.costVectorTSP1D(c.getY(), c.getDy(), citiesY);
		return Math.max(costX, costY);
	}
}
