package sample.Factory.ModelFactory;

import sample.Model.Decorator.BornedRotate;
import sample.Model.IShapeInter;

public class BornedRotateFactory implements IBornedRotateFactory{

    @Override
    public IShapeInter createShape(IShapeInter shapeInter) {
        return new BornedRotate(shapeInter);
    }
}
