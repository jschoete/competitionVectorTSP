package competition.algorithms;

public class Geometry {

    //taken and modified from https://stackoverflow.com/questions/849211/shortest-distance-between-a-point-and-a-line-segment
    public static double percentageOnSegment(int x, int y, int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) { //in case of 0 length line
            if (x == x1 && y == y1)
                return 1;
            else
                return -1;
        }
        //translate (x, y) and (x2, y2) by (-x1, -y1)
        float A = x - x1;
        float B = y - y1;
        float C = x2 - x1;
        float D = y2 - y1;
        //compute percentage
        float dot = A * C + B * D;
        float len_sq = C * C + D * D;
        float percentage = dot / len_sq;
        if (percentage < 0 || percentage > 1) //not on line
            return -1;
        float xx, yy;
        //(xx, yy) is the projection of (x, y) on line (x1, y1) -- (x2, y2)
        xx = x1 + percentage * C;
        yy = y1 + percentage * D;
        if (x - xx == 0 && y - yy == 0) //on line
            return percentage;
        return -1;
    }

    public static boolean onSegment(double x, double y, double x1, double y1, double x2, double y2){
        if (x1 == x2 && y1 == y2) { //in case of 0 length line
            if (x == x1 && y == y1)
                return true;
            else
                return false;
        }
        //translate (x, y) and (x2, y2) by (-x1, -y1)
        double A = x - x1;
        double B = y - y1;
        double C = x2 - x1;
        double D = y2 - y1;
        //compute percentage
        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double percentage = dot / len_sq;
        if (percentage < -.01 || percentage > 1.01) //not on line
            return false;
        double xx, yy;
        //(xx, yy) is the projection of (x, y) on line (x1, y1) -- (x2, y2)
        xx = x1 + percentage * C;
        yy = y1 + percentage * D;
        if (x - xx < .01 && y - yy == .01) //on line
            return true;
        return false;
    }

}
