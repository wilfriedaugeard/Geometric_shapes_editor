package sample.Factory.ModelFactory;

import sample.Model.Point;

public class PointFactory {
    /**
     * @param x Position x
     * @param y Position y
     * @return An object Point with x and y values
     */
    public static Point getPoint(double x, double y){
        return new Point(x,y);
    }
}
