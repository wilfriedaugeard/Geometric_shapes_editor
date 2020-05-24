package sample.Model.Decorator;

import sample.Controller.IController;
import sample.Model.Point;
import sample.Model.RGB;
import sample.Model.IShapeInter;
import sample.View.Drawer.IShapeDrawer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A shapeInter Decorator
 */
public class DecoratorShapeInter implements IShapeInter, Serializable {
    protected IShapeInter shape;

    @Override
    public DecoratorShapeInter clone() {
        try {
            DecoratorShapeInter cloneShape = (DecoratorShapeInter) super.clone();
            cloneShape.shape = this.shape.clone();
            return cloneShape;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public void add(IShapeInter shapeInter) {
        shape.add(shapeInter);
    }

    @Override
    public void remove(IShapeInter shapeInter) {
        shape.remove(shapeInter);
    }

    @Override
    public ArrayList<IShapeInter> getChildren() {
        return shape.getChildren();
    }

    @Override
    public IShapeInter getChild(int n) {
        return shape.getChild(n);
    }

    @Override
    public IShapeDrawer createShapeDrawer(IController controller) {
        return shape.createShapeDrawer(controller);
    }

    @Override
    public Point getPos() {
        return shape.getPos();
    }

    @Override
    public void setPos(Point p) {
        shape.setPos(p);
    }

    @Override
    public void translate(double dx, double dy) {
        shape.translate(dx, dy);
    }

    @Override
    public double getRotation() {
        return shape.getRotation();
    }

    @Override
    public void setRotation(double r) {
        System.out.println("Deco call");
        shape.setRotation(r);
    }

    @Override
    public Point getRotationCenter() {
        return shape.getRotationCenter();
    }

    @Override
    public void setRotationCenter(Point p) {
        shape.setRotationCenter(p);
    }

    @Override
    public RGB getRGB() {
        return shape.getRGB();
    }

    @Override
    public void setRGB(RGB rgb) {
        shape.setRGB(rgb);
    }

    @Override
    public ArrayList<Double> getVector() {
        return shape.getVector();
    }

    @Override
    public void setVector(ArrayList<Double> vector) {
        shape.setVector(vector);
    }

    @Override
    public Double[] getPoints(int n, double l) {
        return shape.getPoints(n,l);
    }

    @Override
    public double getWidth() {
        return shape.getWidth();
    }

    @Override
    public double getHeight() {
        return shape.getHeight();
    }

    @Override
    public void setCoeff(double coeff) {
        shape.setCoeff(coeff);
    }

    @Override
    public double getCoeff() {
        return shape.getCoeff();
    }

    @Override
    public double getDeltaX() {
        return shape.getDeltaX();
    }

    @Override
    public void setDeltaX(double d) {
        shape.setDeltaX(d);
    }

    @Override
    public double getDeltaY() {
        return shape.getDeltaY();
    }

    @Override
    public void setDeltaY(double d) {
        shape.setDeltaY(d);
    }
}
