package sample.Model;

import sample.Controller.Controller;
import sample.View.IShapeDrawer;

import java.io.Serializable;
import java.util.ArrayList;

public class ShapeGroup implements ShapeInter, Serializable {

    private ArrayList<ShapeInter> group;
    private double coeff;
    public ShapeGroup (){
        group = new ArrayList<>();
    }

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
        return null;
    }

    @Override
    public Point getPos() {
        return null;
    }

    @Override
    public void setPos(Point p) {

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
        return null;
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
    public void setCoeff(double coeff) {
        this.coeff = coeff;
    }

    @Override
    public double getCoeff() {
        return coeff;
    }

}
