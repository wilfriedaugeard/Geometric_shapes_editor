package sample.Factory.ModelFactory;

import sample.Model.IShapeInter;
import sample.Model.Point;
import sample.Model.RGB;
import sample.Model.RectangleJavaFX;

public class RectangleJavaFXFactory{

    /**
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     * @param pos The position (x,y) given
     * @param rgb The color RGB
     * @return An object RectangleJavaFX
     */
    public static IShapeInter createShape(double width, double height, Point pos, RGB rgb) {
        return new RectangleJavaFX(width,height, pos, rgb);
    }
}
