package sample.Factory;

import sample.View.IShapeDrawer;
import sample.View.IView;

public interface IPolygonDrawerFactory {
    public IShapeDrawer createPolygonDrawer(Double[] points, IView view, Double[] rgb);
}
