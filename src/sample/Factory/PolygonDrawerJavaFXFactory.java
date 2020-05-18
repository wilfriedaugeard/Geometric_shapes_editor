package sample.Factory;

import sample.View.IShapeDrawer;
import sample.View.IView;
import sample.View.PolygoneDrawerJavaFX;

public class PolygonDrawerJavaFXFactory implements IPolygonDrawerFactory{

    @Override
    public IShapeDrawer createPolygonDrawer(Double[] points, IView view, Double[] rgb) {
        return new PolygoneDrawerJavaFX(points, view, rgb);
    }
}
