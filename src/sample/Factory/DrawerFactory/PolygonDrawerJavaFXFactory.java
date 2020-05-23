package sample.Factory.DrawerFactory;

import sample.View.Drawer.IShapeDrawer;
import sample.View.IView;
import sample.View.Drawer.PolygoneDrawerJavaFX;

public class PolygonDrawerJavaFXFactory implements IPolygonDrawerFactory{

    @Override
    public IShapeDrawer createPolygonDrawer(Double[] points, IView view, int[] rgb, double rotation) {
        return new PolygoneDrawerJavaFX(points, view, rgb, rotation);
    }
}
