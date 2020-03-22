package sample.Controller;

import sample.Model.ShapeInter;

public class AddShapeInGroupCommand implements Command {
    ShapeInter shapeGroup;
    ShapeInter shapeToAdd;

    public AddShapeInGroupCommand(ShapeInter shapeGroup, ShapeInter shapeToAdd){
        this.shapeGroup = shapeGroup;
        this.shapeToAdd = shapeToAdd;
    }

    @Override
    public void execute() {
        shapeGroup.add(shapeToAdd);
    }
}
