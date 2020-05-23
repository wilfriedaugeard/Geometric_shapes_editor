package sample.Factory.ModelFactory;

import sample.Model.IShapeInter;
import sample.Model.Point;
import sample.Model.RGB;
import sample.Model.RectangleJavaFX;

public class RectangleJavaFXFactory{

    public static IShapeInter createShape(double width, double height, Point pos, RGB rgb) {
        return new RectangleJavaFX(width,height, pos, rgb);
    }
}
