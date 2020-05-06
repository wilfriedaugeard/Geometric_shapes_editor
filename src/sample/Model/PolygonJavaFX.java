package sample.Model;

import sample.Controller.Controller;
import sample.View.PolygoneDrawerJavaFX;
import sample.View.IShapeDrawer;

public class PolygonJavaFX extends PolygonModel {

    public PolygonJavaFX(int nbEdges, double length, Point pos, RGB rgb) {
        super(nbEdges, length, pos, rgb);
    }

    @Override
    public IShapeDrawer createShapeDrawer(Controller controller) {
        RGB rgb = this.getRGB();
        double red = rgb.getR();
        double green = rgb.getG();
        double blue = rgb.getB();

        Double[] rgbTab = new Double[3];
        rgbTab[0] = red; rgbTab[1] = green; rgbTab[2] = blue;

        Double[] points = this.getPoints(this.getNbEdges(), this.getLength());

        return new PolygoneDrawerJavaFX(points, controller.getView(), rgbTab);
    }


}
