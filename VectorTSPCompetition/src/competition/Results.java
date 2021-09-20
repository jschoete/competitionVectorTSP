package competition;

import competition.algorithms.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Results {
	
	public static String folder = "results/";
	
	public static String get(String algo, int n, int d, int i) throws FileNotFoundException {
		String file = folder + algo + "/" + n + '_' + d + ".txt";
		Scanner reader = new Scanner(new File(file));
		while (i > 0) {
			reader.nextLine();
			reader.nextLine();
			i--;
		}
		reader.nextLine();
		return reader.nextLine();
	}
	
	public static int getCost(String algo, int n, int d, int i) throws FileNotFoundException {
		return getCost(get(algo, n, d, i));
	}
	
	public static int getCost(String result) {
		String[] split = result.split("\t");
		return Integer.parseInt(split[0]);
	}
	
	public static float getTime(String algo, int n, int d, int i) throws FileNotFoundException {
		return getTime(get(algo, n, d, i));
	}
	
	public static float getTime(String result) {
		String[] split = result.split("\t");
		return Float.parseFloat(split[1]);
	}
	
	public static List<Point> getTrajectory(String algo, int n, int d, int i) throws FileNotFoundException {
		return getTrajectory(get(algo, n, d, i));
	}
	
	public static List<Point> getTrajectory(String line) {
		List<Point> result = new ArrayList<>();
		String[] split = line.split("\t");
		for (int j = 2; j < split.length; j += 2) {
			int x = Integer.parseInt(split[j]);
			int y = Integer.parseInt(split[j + 1]);
			result.add(new Point(x, y));
		}
		return result;
	}
	
	public static boolean isValid(String algo, int n, int d, int i) throws FileNotFoundException {
		return isValid(get(algo, n, d, i));
	}
	
	public static boolean isValid(String result) {
		String[] split = result.split("\t");
		return split.length == getCost(result) + 3 && verifiesConstraints(getTrajectory(result));
	}
	
	private static boolean verifiesConstraints(List<Point> trajectory) {
		int dx = 0;
		int dy = 0;
		int diffx, diffy;
		Point p1, p2;
		int j = 0;
		while (j < trajectory.size() - 1) {
			p1 = trajectory.get(j);
			p2 = trajectory.get(j + 1);
			diffx = p2.getX() - p1.getX();
			diffy = p2.getY() - p1.getY();
			if (diffx > dx + 1 || diffx < dx - 1 || diffy > dy + 1 || diffy < dy - 1) {
				return false;
			}
			dx = diffx;
			dy = diffy;
			j++;
		}
		return trajectory.get(j - 1).equals(trajectory.get(j));
	}
	
}
