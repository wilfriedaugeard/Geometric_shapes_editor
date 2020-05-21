package sample.Model;

import sample.Controller.Controller;
import sample.Controller.ControllerJavaFX;
import sample.View.IShapeDrawer;

import java.util.ArrayList;

public interface ShapeInter extends Cloneable {

    public ShapeInter clone();

    /* Pattern Composite methods */
    void add(ShapeInter shapeInter);
    void remove(ShapeInter shapeInter);
    ArrayList<ShapeInter> getChildren();
    ShapeInter getChild(int n);

    /* Bridge */
    public IShapeDrawer createShapeDrawer(Controller controller);

    /* Simple methods */
    public Point getPos();
    public void setPos(Point p);
    public void translate(double dx, double dy);
    public double getRotation();
    public void setRotation(double r);
    public Point getRotationCenter();
    public void setRotationCenter(Point p);
    public RGB getRGB();
    public void setRGB(RGB rgb);
    public ArrayList<Double> getVector();
    public void setVector(ArrayList<Double> vector);
    public Double[] getPoints(int n, double l);
    public double getWidth();
    public double getHeight();
    public void setCoeff(double coeff);
    public double getCoeff();
}
