package sample.Factory.DrawerFactory;

import sample.View.Drawer.IShapeDrawer;
import sample.View.IView;

/**
 * Polygon drawer factory
 */
public interface IPolygonDrawerFactory {
    public IShapeDrawer createPolygonDrawer(Double[] points, IView view, int[] rgb, double rotation);
}
