package sample.Model.Decorator;

import sample.Model.IShapeInter;

/**
 * Add a born to the shape rotation
 */
public class BornedRotate extends DecoratorShapeInter{

    public BornedRotate(IShapeInter shapeInter) {
        shape = shapeInter;
    }

    /**
     * Compute the rotation and born it if it should borned
     * @param s The shape to born
     * @param rotation The rotation to born
     */
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

    /**
     * Set the rotation of the shape
     * @param rotation The rotation to set
     */
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
