package sample.Model;

import sample.Controller.Controller;
import sample.View.ShapeDrawer;

import java.util.ArrayList;

public class ShapeGroup implements ShapeInter {

    private ArrayList<ShapeInter> group;

    public ShapeGroup (){
        group = new ArrayList<ShapeInter>();
    }

    @Override
    public ShapeGroup clone() {
        try {
            return (ShapeGroup) super.clone();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return this;
    }

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
    public ShapeDrawer createShapeDrawer(Controller controller) {
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
