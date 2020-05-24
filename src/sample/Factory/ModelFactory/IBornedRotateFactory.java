package sample.Factory.ModelFactory;

import sample.Model.IShapeInter;

public interface IBornedRotateFactory {

    /**
     * @param shapeInter The IShapeInter to be decorated
     * @return An object with the functionality to limit the rotation with correct values
     */
    public IShapeInter createShape(IShapeInter shapeInter);
}
