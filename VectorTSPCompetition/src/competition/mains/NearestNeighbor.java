package competition.mains;

import competition.algorithms.City;
import competition.ClassGetter;
import competition.algorithms.Point;
import competition.algorithms.vtsp.multipointastar.hashset.MultiPointAStarBound;
import competition.algorithms.vtsp.multipointastar.hashset.limitedview.LimitedViewMultiPointAStar;
import competition.FileCreator;
import competition.Results;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class NearestNeighbor { // multiPointAStar of NearestNeighbor
	
	public static void main(String[] args) throws IOException {
		String n = args[0];
		String d = args[1];
		String i = args[2];
		String className = new ClassGetter().getClassName();
		System.out.println(className + " " + n + " " + d + " " + i);
		String outputFile = Results.folder + className + "/" + n + "_" + d + ".txt";
		FileCreator.createFile(outputFile);
		FileWriter writer = new FileWriter(outputFile, true);
		int ii = Integer.parseInt(i);
		if (ii != 0){
			writer.write("\n");
		}
		writer.write(i + ":\n");
		List<City> cities = Instances.get(n, d, i);
		long t1, t2;
		t1 = System.currentTimeMillis();
		cities = competition.algorithms.etsp.nearestneighbor.NearestNeighbor.
				nearestNeighbor(cities);
		List<Point> aux = LimitedViewMultiPointAStar.limitedViewMultiPointAStar(cities);
		List<Point> trajectory = MultiPointAStarBound.
				multiPointAStarBound(cities, aux.size()-2);
		if (trajectory.isEmpty())
			trajectory = aux;
		t2 = System.currentTimeMillis();
		float t = (t2 - t1) / 1000f;
		writer.write((trajectory.size() - 1) + "\t");
		writer.write(t + "\t");
		for (Point p : trajectory){
			writer.write(p.getX() + "\t");
			writer.write(p.getY() + "\t");
		}
		writer.close();
	}
	
}
