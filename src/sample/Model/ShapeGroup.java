package sample.Model;

import sample.Controller.Controller;
import sample.Factory.*;
import sample.View.IShapeDrawer;

import java.io.Serializable;
import java.util.ArrayList;

public class ShapeGroup implements ShapeInter, Serializable {

    private ArrayList<ShapeInter> group;
    private double coeff;
    private Point pos;
    public ShapeGroup (){
        group = new ArrayList<>();
    }
    private double deltaX = 0;
    private double deltaY = 0;

    @Override
    public ShapeGroup clone() {
        try {
            ArrayList<ShapeInter> groupCloned = new ArrayList<>(group.size());
            groupCloned.addAll(group);
            ShapeGroup clone = (ShapeGroup) super.clone();
            clone.group = groupCloned;
            return clone;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /*Pattern composite methods*/
    @Override
    public void add(ShapeInter shapeInter){
        group.add(shapeInter);
    }

    @Override
    public void remove(ShapeInter shapeInter){
        group.remove(shapeInter);
    }

    @Override
    public ArrayList<ShapeInter> getChildren(){
        return group;
    }

    @Override
    public ShapeInter getChild(int n){
        return group.get(n);
    }

    @Override
    public IShapeDrawer createShapeDrawer(Controller controller) {
        IGroupDrawerFactory groupDrawerFactory = new GroupDrawerJavaFXFactory();
        ArrayList<IShapeDrawer> drawerlist = new ArrayList<>();
        for(ShapeInter shape : getChildren()){
            drawerlist.add(shape.createShapeDrawer(controller));
        }
        return groupDrawerFactory.createGroupDrawer(controller.getView(), getPos().getX(), getPos().getY(), getWidth(), getHeight(), drawerlist);
    }

    /**
     * Return the x min and y min of shapes in a group
     * @return
     */
    @Override
    public Point getPos() {
        double x = Double.MAX_VALUE;
        double y = Double.MAX_VALUE;
        for (ShapeInter shape : getChildren()){
            x = Double.min(shape.getPos().getX(),x);
            y = Double.min(shape.getPos().getY(),y);
        }
        return PointFactory.getPoint(x,y);
    }

    /**
     *  translate all shapes
     * @param p
     */
    @Override
    public void setPos(Point p) {
        double x = p.getX()-getPos().getX();
        double y = p.getY()-getPos().getY();
        for(ShapeInter shape : group) {
            translate(x, y);
        }
    }

    @Override
    public void translate(double dx, double dy) {
        for(ShapeInter shape : group) {
            shape.getPos().setX(shape.getPos().getX() + dx);
            shape.getPos().setY(shape.getPos().getY() + dy);
        }
    }

    @Override
    public double getRotation() {
        return 0;
    }

    @Override
    public void setRotation(double r) {
        for(ShapeInter shape : group){
            shape.setRotation(r);
        }
    }

    @Override
    public Point getRotationCenter() {
        return null;
    }

    @Override
    public void setRotationCenter(Point p) {

    }

    @Override
    public RGB getRGB() {
        RGB rgb = new RGB(0,0,0);
        return rgb;
    }

    @Override
    public void setRGB(RGB rgb) {
        for(ShapeInter shape : group){
            shape.setRGB(rgb);
        }
    }

    @Override
    public ArrayList<Double> getVector() {
        Double size = 0.0;
        for(ShapeInter shape : getChildren()){
            size += shape.getVector().get(0);
        }
        ArrayList<Double> vector = new ArrayList<>();
        vector.add(size);
        return vector;
    }

    @Override
    public void setVector(ArrayList<Double> vector) {
        return;
    }

    @Override
    public Double[] getPoints(int n, double l) {
        return null;
    }

    @Override
    public double getWidth() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (ShapeInter shape : getChildren()){
            min = Double.min(shape.getPos().getX(),min);
            max = Double.max(shape.getPos().getX()+shape.getWidth(),max);
        }
        return max-min;
    }

    @Override
    public double getHeight() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (ShapeInter shape : getChildren()){
            min = Double.min(shape.getPos().getY(),min);
            max = Double.max(shape.getPos().getY()+shape.getHeight(),max);
        }
        return max-min;
    }

    @Override
    public void setCoeff(double coeff) {
        this.coeff = coeff;
    }

    @Override
    public double getCoeff() {
        return coeff;
    }

    @Override
    public double getDeltaX() {
        System.out.println("getD ShapeGroup");
        return deltaX;
    }

    @Override
    public void setDeltaX(double d) {
        deltaX = d;
        for(ShapeInter child : getChildren()){
            child.setDeltaX(d);
        }
    }

    @Override
    public double getDeltaY() {
        return deltaY;
    }

    @Override
    public void setDeltaY(double d) {
        deltaY = d;
        for(ShapeInter child : getChildren()){
            child.setDeltaY(d);
        }
    }
}
