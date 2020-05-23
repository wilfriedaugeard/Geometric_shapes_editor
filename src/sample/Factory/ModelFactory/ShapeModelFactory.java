package sample.Factory.ModelFactory;

import sample.Model.*;

public class ShapeModelFactory {

    public static IShapeInter createShape(String factoryName, double arg1, double arg2, Point pos, RGB rgb){
        if(factoryName.equals("Rectangle"))
            return RectangleJavaFXFactory.createShape(arg1, arg2, pos, rgb);
        if(factoryName.equals("Polygon"))
            return PolygonJavaFXFactory.createShape((int) arg1,arg2,pos,rgb);
        return null;
    }
}
