package competition.mains;

import competition.algorithms.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static competition.FileCreator.createFile;

public class Instances {
	
	public static String folder = "instances/";
	
	public static void main(String[] args) throws IOException {
		Random r = new Random(0);
		for (int n = 5; n <= 25; n++) {
			for (int d = 10; d <= 200; d += 10) {
				String file = folder + n + '_' + d + ".txt";
				createFile(file);
				FileWriter writer = new FileWriter(file);
				for (int i = 0; i < 100; i++) {
					for (int j = 0; j < n; j++) {
						writer.write(r.nextInt(d) + "\t");
						writer.write(r.nextInt(d) + "\t");
					}
					writer.write("\n");
				}
				writer.close();
			}
		}
	}
	
	public static List<City> get(int n, int d, int i) throws FileNotFoundException {
		return get(String.valueOf(n), String.valueOf(d), String.valueOf(i));
	}
	
	public static List<City> get(String n, String d, String i) throws FileNotFoundException {
		String file = folder + n + "_" + d + ".txt";
		Scanner reader = new Scanner(new File(file));
		int j = Integer.parseInt(i);
		while (j > 0){
			reader.nextLine();
			j--;
		}
		String line = reader.nextLine();
		return parse(line);
	}
	
	private static List<City> parse(String line) {
		List<City> result = new ArrayList<>();
		String[] split = line.split(":");
		line = split[split.length - 1];
		split = line.split("\t");
		int x, y;
		for (int i=0; i<split.length; i+=2){
			x = Integer.parseInt(split[i]);
			y = Integer.parseInt(split[i+1]);
			City c = new City(x, y);
			result.add(c);
		}
		return result;
	}
}
