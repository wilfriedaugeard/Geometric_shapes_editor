package sample.Factory;

import sample.View.Drawer.IShapeDrawer;
import sample.View.IView;

public interface IRectangleDrawerFactory {
    public IShapeDrawer createRectangleDrawer(double x, double y, double width, double height, int[] rgb, IView view, double arrondi, double rotation);
}
