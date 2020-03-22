package sample.Controller;

import sample.Model.ShapeInter;

public class AddGroupShapeCommand implements Command{
    ShapeInter shapeGroup;
    Controller controller;

    public AddGroupShapeCommand(ShapeInter shapeGroup, Controller controller){
        this.shapeGroup = shapeGroup;
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.getShapeGroups().add(shapeGroup);
    }
}
