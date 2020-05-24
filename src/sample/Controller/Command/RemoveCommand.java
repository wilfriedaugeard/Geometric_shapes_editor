package sample.Controller.Command;

import sample.Controller.IController;
import sample.Model.IShapeInter;
import sample.View.Drawer.IShapeDrawer;

import java.util.ArrayList;


public class RemoveCommand implements ICommand {
    private IShapeInter shape;
    private IShapeInter shapeBackUp;
    private IController controller;


    public RemoveCommand(IShapeInter shape, IController controller) {
        this.shape = shape;
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.updateViewRemove(shape);
    }



    @Override
    public void undo() {
        shapeBackUp = controller.createShapeInCanvas(controller, shape, shape.getPos().getX(), shape.getPos().getY(), false);

    }

    @Override
    public void redo() {
        controller.updateViewRemove(shapeBackUp);
    }
}
