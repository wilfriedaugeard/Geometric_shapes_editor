package sample.Model.Decorator;

import sample.Model.IShapeInter;

public class BornedRotate extends DecoratorShapeInter{

    public BornedRotate(IShapeInter shapeInter) {
        shape = shapeInter;
    }

    private void computeRotate(IShapeInter s, double rotation){
        if(rotation < -360.0){
            s.setRotation(-360.0);
        }
        if(rotation > 360.0){
            s.setRotation(360.0);
        }
        if(rotation >= -360.0 && rotation <= 360.0) {
            s.setRotation(rotation);
        }
    }

    @Override
    public void setRotation(double rotation) {
        if(rotation < -360.0){
            super.shape.setRotation(-360.0);
        }
        if(rotation > 360.0){
            super.shape.setRotation(360.0);
        }
        if(rotation >= -360.0 && rotation <= 360.0) {
            super.shape.setRotation(rotation);
        }
    }
}
