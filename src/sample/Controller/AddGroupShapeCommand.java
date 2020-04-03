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
        controller.getShapeGroupTmp().getChildren().clear();
    }

    @Override
    public void undo() {
        controller.getShapeGroups().remove(shapeGroup);
    }

    @Override
    public void redo() {
        execute();
    }
}
