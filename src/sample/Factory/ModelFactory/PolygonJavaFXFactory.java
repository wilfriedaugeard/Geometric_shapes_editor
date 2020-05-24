package sample.Factory.ModelFactory;

import sample.Model.IShapeInter;
import sample.Model.Point;
import sample.Model.PolygonJavaFX;
import sample.Model.RGB;

public class PolygonJavaFXFactory{

    /**
     * @param nbEdges The number of edges of the polygon
     * @param length The length of the polygon
     * @param pos The position (x,y) given
     * @param rgb The color RGB
     * @return An object PolygonJavaFX
     */
    public static IShapeInter createShape(int nbEdges, double length, Point pos, RGB rgb) {
        return new PolygonJavaFX(nbEdges,length,pos,rgb);
    }
}
