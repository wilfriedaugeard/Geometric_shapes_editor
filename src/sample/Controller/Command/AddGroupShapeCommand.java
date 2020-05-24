package sample.Controller.Command;

import sample.Controller.IController;
import sample.Model.IShapeInter;


/**
 * Command to create a group of shape
 */
public class AddGroupShapeCommand implements ICommand {
    IShapeInter shapeGroup;
    IController controller;

    public AddGroupShapeCommand(IShapeInter shapeGroup, IController controller){
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
