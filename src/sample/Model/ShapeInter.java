package sample.Model;

import sample.Controller.Controller;
import sample.View.ShapeDrawer;

public interface ShapeInter extends Cloneable {

    public ShapeInter clone();

    public ShapeDrawer createShapeDrawer(Controller controller);

    public Point getPos();
    public void setPos(Point p);
    public void translate(double dx, double dy);

    public int getRotation();
    public void setRotation(int r);

    public Point getRotationCenter();
    public void setRotationCenter(Point p);

    public Point getTranslationCenter();
    public void setTranslationCenter(Point p);

    public RGB getRGB();
    public void setRGB(RGB rgb);

}
