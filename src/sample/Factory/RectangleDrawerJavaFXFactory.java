package sample.Factory;

import sample.View.Drawer.IShapeDrawer;
import sample.View.IView;
import sample.View.Drawer.RectangleDrawerJavaFX;

public class RectangleDrawerJavaFXFactory implements IRectangleDrawerFactory{

    @Override
    public IShapeDrawer createRectangleDrawer(double x, double y, double width, double height, int[] rgb, IView view, double arrondi, double rotation) {
        return new RectangleDrawerJavaFX(x, y, width, height, rgb, view, arrondi, rotation);
    }
}
