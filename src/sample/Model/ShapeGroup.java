package sample.Model;

import sample.Controller.IController;
import sample.Factory.DrawerFactory.GroupDrawerJavaFXFactory;
import sample.Factory.DrawerFactory.IGroupDrawerFactory;
import sample.Factory.ModelFactory.PointFactory;
import sample.View.Drawer.IShapeDrawer;

import java.io.Serializable;
import java.util.ArrayList;

public class ShapeGroup implements IShapeInter, Serializable {

    private ArrayList<IShapeInter> group;
    private double coeff;
    private Point pos;
    private double deltaX = 0;
    private double deltaY = 0;

    public ShapeGroup (){
        group = new ArrayList<>();
    }


    @Override
    public ShapeGroup clone() {
        try {
            ArrayList<IShapeInter> groupCloned = new ArrayList<>(group.size());
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
    public void add(IShapeInter shapeInter){
        group.add(shapeInter);
    }

    @Override
    public void remove(IShapeInter shapeInter){
        group.remove(shapeInter);
    }

    @Override
    public ArrayList<IShapeInter> getChildren(){
        return group;
    }

    @Override
    public IShapeInter getChild(int n){
        return group.get(n);
    }

    @Override
    public IShapeDrawer createShapeDrawer(IController controller) {
        IGroupDrawerFactory groupDrawerFactory = new GroupDrawerJavaFXFactory();
        ArrayList<IShapeDrawer> drawerlist = new ArrayList<>();
        for(IShapeInter shape : getChildren()){
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
        for (IShapeInter shape : getChildren()){
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
        for(IShapeInter shape : group) {
            translate(x, y);
        }
    }

    @Override
    public void translate(double dx, double dy) {
        for(IShapeInter shape : group) {
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
        for(IShapeInter shape : group){
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
        for(IShapeInter shape : group){
            shape.setRGB(rgb);
        }
    }

    @Override
    public ArrayList<Double> getVector() {
        Double size = 0.0;
        for(IShapeInter shape : getChildren()){
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
        for (IShapeInter shape : getChildren()){
            min = Double.min(shape.getPos().getX(),min);
            max = Double.max(shape.getPos().getX()+shape.getWidth(),max);
        }
        return max-min;
    }

    @Override
    public double getHeight() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (IShapeInter shape : getChildren()){
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
        return deltaX;
    }

    @Override
    public void setDeltaX(double d) {
        deltaX = d;
        for(IShapeInter child : getChildren()){
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
        for(IShapeInter child : getChildren()){
            child.setDeltaY(d);
        }
    }
}
