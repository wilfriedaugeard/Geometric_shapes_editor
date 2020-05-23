package sample.Factory.ModelFactory;

import sample.Model.IShapeInter;
import sample.Model.Point;
import sample.Model.PolygonJavaFX;
import sample.Model.RGB;

public class PolygonJavaFXFactory{

    public static IShapeInter createShape(int nbEdges, double length, Point pos, RGB rgb) {
        return new PolygonJavaFX(nbEdges,length,pos,rgb);
    }
}
