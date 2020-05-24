package sample.Factory.ModelFactory;

import sample.Model.IShapeInter;
import sample.Model.ShapeGroup;

public class ShapeGroupFactory {

    /**
     * @return A IShapeInter group
     */
    public static IShapeInter createGroup() {
        return new ShapeGroup();
    }
}
