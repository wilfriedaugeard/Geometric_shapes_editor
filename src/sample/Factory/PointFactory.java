package sample.Factory;

import sample.Model.Point;

public class PointFactory {
    public static Point getPoint(double x, double y){
        return new Point(x,y);
    }
}
