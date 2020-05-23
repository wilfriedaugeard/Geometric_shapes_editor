package sample.Model;

import sample.Controller.IController;
import sample.Factory.DrawerFactory.IPolygonDrawerFactory;
import sample.Factory.DrawerFactory.PolygonDrawerJavaFXFactory;
import sample.View.Drawer.IShapeDrawer;

public class PolygonJavaFX extends PolygonModel {

    public PolygonJavaFX(int nbEdges, double length, Point pos, RGB rgb) {
        super(nbEdges, length, pos, rgb);
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
        IPolygonDrawerFactory polygonDrawerFactory = new PolygonDrawerJavaFXFactory();
        RGB rgb = this.getRGB();
        double red = rgb.getR();
        double green = rgb.getG();
        double blue = rgb.getB();
        double alpha = rgb.getA();

        int[] rgbTab = new int[4];
        rgbTab[0] = (int) red; rgbTab[1] = (int) green; rgbTab[2] = (int) blue; rgbTab[3] = (int) alpha;

        Double[] points = this.getPoints(this.getNbEdges(), this.getLength());

        return polygonDrawerFactory.createPolygonDrawer(points, controller.getView(), rgbTab, this.getRotation());
    }


}
