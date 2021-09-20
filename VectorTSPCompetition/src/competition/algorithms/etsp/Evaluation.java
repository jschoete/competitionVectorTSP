package competition.algorithms.etsp;

import competition.algorithms.City;

import java.util.List;

public class Evaluation {

    public static double cost(List<City> cities){
        double result = 0;
        for (int i=0; i<cities.size()-1; i++)
            result += cities.get(i).distance(cities.get(i+1));
        result += cities.get(cities.size()-1).distance(cities.get(0));
        return result;
    }

    public static double cost(List<City> cities, double bound){
        double result = 0;
        for (int i=0; i<cities.size()-1; i++){
            result += cities.get(i).distance(cities.get(i+1));
            if (result + cities.get(i+1).distance(cities.get(0)) >= bound)
                return -(i+1);
        }
        result += cities.get(cities.size()-1).distance(cities.get(0));
        return result;
    }
}
