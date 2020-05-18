package sample.Factory;

import sample.View.IShapeDrawer;
import sample.View.IView;
import sample.View.RectangleDrawerJavaFX;

public class RectangleDrawerJavaFXFactory implements IRectangleDrawerFactory{

    @Override
    public IShapeDrawer createRectangleDrawer(double x, double y, double width, double height, Double[] rgb, IView view, double arrondi) {
        return new RectangleDrawerJavaFX(x, y, width, height, rgb, view, arrondi);
    }
}
