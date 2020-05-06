package sample.Model;

import sample.Controller.Controller;
import sample.View.IShapeDrawer;

import java.io.Serializable;
import java.util.ArrayList;

public class ShapeGroup implements ShapeInter, Serializable {

    private ArrayList<ShapeInter> group;

    public ShapeGroup (){
        group = new ArrayList<ShapeInter>();
    }

    @Override
    public ShapeGroup clone() {
        try {
            ArrayList<ShapeInter> groupCloned = new ArrayList<>(group.size());
            for(ShapeInter shape : group){
                groupCloned.add(shape);
            }
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
    public int getRotation() {
        return 0;
    }

    @Override
    public void setRotation(int r) {

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



}
