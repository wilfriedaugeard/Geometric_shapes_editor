package sample.Model;

import sample.Controller.IController;
import sample.Factory.DrawerFactory.IRectangleDrawerFactory;
import sample.Factory.DrawerFactory.RectangleDrawerJavaFXFactory;
import sample.View.Drawer.IShapeDrawer;

public class RectangleJavaFX extends RectangleModel {

    public RectangleJavaFX(double width, double height, Point pos, RGB rgb) {
        super(width, height, pos, rgb);
    }


    @Override
    public void setDeltaX(double deltaX) {
        super.setDeltaX(deltaX);
    }


    @Override
    public void setDeltaY(double deltaY) {
        super.setDeltaY(deltaY);
    }

    @Override
    public IShapeDrawer createShapeDrawer(IController controller) {
        IRectangleDrawerFactory rectangleDrawerFactory = new RectangleDrawerJavaFXFactory();

        RGB rgb = this.getRGB();
        double red = rgb.getR();
        double green = rgb.getG();
        double blue = rgb.getB();
        double alpha = rgb.getA();

        int[] rgbTab = new int[4];
        rgbTab[0] = (int) red; rgbTab[1] = (int) green; rgbTab[2] = (int) blue; rgbTab[3] = (int) alpha;
        return rectangleDrawerFactory.createRectangleDrawer(this.getPos().getX(), this.getPos().getY(), this.getWidth(), this.getHeight(),
                rgbTab,  controller.getView(), this.getArrondi(), this.getRotation());
    }
}
