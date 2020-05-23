package sample.Factory.ModelFactory;

import sample.Model.IShapeInter;
import sample.Model.ShapeGroup;

public class ShapeGroupFactory {

    public static IShapeInter createGroup() {
        return new ShapeGroup();
    }
}
