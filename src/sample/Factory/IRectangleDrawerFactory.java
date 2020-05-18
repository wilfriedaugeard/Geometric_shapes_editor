package sample.Factory;

import sample.Controller.Controller;
import sample.View.IShapeDrawer;
import sample.View.IView;

public interface IRectangleDrawerFactory {
    public IShapeDrawer createRectangleDrawer(double x, double y, double width, double height, int[] rgb, IView view, double arrondi);
}
