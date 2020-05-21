package sample.Model;

import sample.Controller.Controller;
import sample.Factory.IRectangleDrawerFactory;
import sample.Factory.RectangleDrawerJavaFXFactory;
import sample.View.IShapeDrawer;

public class RectangleJavaFX extends RectangleModel {

    public RectangleJavaFX(double width, double height, Point pos, RGB rgb) {
        super(width, height, pos, rgb);
    }

    @Override
    public IShapeDrawer createShapeDrawer(Controller controller) {
        IRectangleDrawerFactory rectangleDrawerFactory = new RectangleDrawerJavaFXFactory();

        RGB rgb = this.getRGB();
        double red = rgb.getR();
        double green = rgb.getG();
        double blue = rgb.getB();

        int[] rgbTab = new int[3];
        rgbTab[0] = (int) red; rgbTab[1] = (int) green; rgbTab[2] = (int) blue;


        return rectangleDrawerFactory.createRectangleDrawer(this.getPos().getX(), this.getPos().getY(), this.getWidth(), this.getHeight(),
                rgbTab,  controller.getView(), this.getArrondi());
    }
}
