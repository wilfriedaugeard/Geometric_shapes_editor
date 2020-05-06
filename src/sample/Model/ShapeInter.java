package sample.Model;

import sample.Controller.Controller;
import sample.View.IShapeDrawer;

import java.util.ArrayList;

public interface ShapeInter extends Cloneable {

    public ShapeInter clone();

    /* Pattern Composite methods */
    void add(ShapeInter shapeInter);
    void remove(ShapeInter shapeInter);
    ArrayList<ShapeInter> getChildren();
    ShapeInter getChild(int n);

    /* Pattern Observer */
    //void addObserver(ShapeObserver obs);
    //void removeObserver(ShapeObserver obs);
    //void notifyObservers();

    /* Bridge */
    public IShapeDrawer createShapeDrawer(Controller controller);

    /* Simple methods */
    public Point getPos();
    public void setPos(Point p);
    public void translate(double dx, double dy);
    public int getRotation();
    public void setRotation(int r);
    public Point getRotationCenter();
    public void setRotationCenter(Point p);
    public RGB getRGB();
    public void setRGB(RGB rgb);
}
