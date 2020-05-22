package sample.Model;

import sample.Controller.Controller;
import sample.Controller.ControllerJavaFX;
import sample.Factory.IPolygonDrawerFactory;
import sample.Factory.PolygonDrawerJavaFXFactory;
import sample.View.PolygoneDrawerJavaFX;
import sample.View.IShapeDrawer;

public class PolygonJavaFX extends PolygonModel {

    public PolygonJavaFX(int nbEdges, double length, Point pos, RGB rgb) {
        super(nbEdges, length, pos, rgb);
    }

    @Override
    public IShapeDrawer createShapeDrawer(Controller controller) {
        IPolygonDrawerFactory polygonDrawerFactory = new PolygonDrawerJavaFXFactory();
        RGB rgb = this.getRGB();
        double red = rgb.getR();
        double green = rgb.getG();
        double blue = rgb.getB();
        double alpha = rgb.getA();

        int[] rgbTab = new int[4];
        rgbTab[0] = (int) red; rgbTab[1] = (int) green; rgbTab[2] = (int) blue; rgbTab[3] = (int) alpha;

        Double[] points = this.getPoints(this.getNbEdges(), this.getLength());

        return polygonDrawerFactory.createPolygonDrawer(points, controller.getView(), rgbTab);
    }


}
