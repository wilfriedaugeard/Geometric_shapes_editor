package sample.Factory.ModelFactory;

import sample.Model.IShapeInter;

public interface IBornedRotateFactory {
    public IShapeInter createShape(IShapeInter shapeInter);
}
