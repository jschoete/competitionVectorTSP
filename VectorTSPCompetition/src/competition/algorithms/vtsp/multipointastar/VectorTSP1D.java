package competition.algorithms.vtsp.multipointastar;

import java.util.List;

public class VectorTSP1D {

    public static int costVectorTSP1D(int x, int dx, List<Short> cities){
        int i = 0;
        int result = 0;
        while (i < cities.size()-1) {
            if (x < cities.get(i) && cities.get(i) > cities.get(i + 1)) { // turn left
                result += costVisitAndStopLeftMost(x, dx, cities.get(i));
                x = Math.max(x + brakingDistance(dx), cities.get(i));
                dx = 0;
            } else if (x > cities.get(i) && cities.get(i) < cities.get(i + 1)) { // turn right
                result += costVisitAndStopLeftMost(-x, -dx, -cities.get(i));
                x = Math.min(x + brakingDistance(dx), cities.get(i));
                dx = 0;
            }
            i++;
        }
        if (x < cities.get(i))
            result += costStop(x, dx, cities.get(i)); // stop at first city
        else
            result += costStop(-x, -dx, -cities.get(i));
        return result;
    }

    public static int costVisitAndStopLeftMost(int x, int dx, int city) {
        if (x + brakingDistance(dx) > city)
            return dx;
        return costStop(x, dx, city);
    }

    public static int costStop(int x, int dx, int city) {
        if (dx == 0)
            return A027434(city - x);
        if (dx < 0)
            return -dx + A027434(city - (x + brakingDistance(dx)));
        if (x + brakingDistance(dx) > city)
            return dx + A027434(x + brakingDistance(dx) - city);
        return -dx + A027434(city - (x - accelerationDistance(dx)));
    }

    public static int brakingDistance(int dx){
        if (dx < 0)
            return -brakingDistance(-dx);
        return dx * (dx - 1) / 2;
    }

    public static int accelerationDistance(int dx){
        return dx + brakingDistance(dx);
    }

    public static int A027434(int d){ // https://oeis.org/A027434
        return (int) Math.ceil(2 * Math.sqrt(Math.abs(d)));
    }
}
