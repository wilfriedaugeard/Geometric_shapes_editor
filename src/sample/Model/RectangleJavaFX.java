package sample.Model;

import sample.Controller.Controller;
import sample.View.RectangleDrawerJavaFX;
import sample.View.IShapeDrawer;

public class RectangleJavaFX extends RectangleModel {

    public RectangleJavaFX(double width, double height, Point pos, sample.Model.RGB rgb) {
        super(width, height, pos, rgb);
    }

    @Override
    public IShapeDrawer createShapeDrawer(Controller controller) {
        RGB rgb = this.getRGB();
        double red = rgb.getR();
        double green = rgb.getG();
        double blue = rgb.getB();

        Double[] rgbTab = new Double[3];
        rgbTab[0] = red; rgbTab[1] = green; rgbTab[2] = blue;

        return new RectangleDrawerJavaFX(this.getPos().getX(), this.getPos().getY(), this.getWidth(), this.getHeight(),
                rgbTab,  controller.getView(), this.getArrondi());
    }
}
