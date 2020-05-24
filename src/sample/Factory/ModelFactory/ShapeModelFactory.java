package sample.Factory.ModelFactory;

import sample.Model.*;

public class ShapeModelFactory {

    /**
     * @param factoryName Factory name to use
     * @param arg1 Width for Rectangle, nbEdges for Polygon
     * @param arg2 Height for Rectangle, length for Polygon
     * @param pos Position (x,y) of the shape
     * @param rgb Color RGB
     * @return
     */
    public static IShapeInter createShape(String factoryName, double arg1, double arg2, Point pos, RGB rgb){

        if(factoryName.equals("Rectangle"))
            return RectangleJavaFXFactory.createShape(arg1, arg2, pos, rgb);

        if(factoryName.equals("Polygon"))
            return PolygonJavaFXFactory.createShape((int) arg1,arg2,pos,rgb);

        return null;
    }
}
