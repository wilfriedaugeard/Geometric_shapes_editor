package sample.Controller.Command;

import sample.Controller.IController;
import sample.Model.IShapeInter;

public class CreateShapeCommand implements ICommand{
    private IShapeInter shapeInToolbar;
    private IShapeInter shapeCreated;
    private IController controller;
    private double posX, posY;

    public CreateShapeCommand(IShapeInter shape, double x, double y, IController controller) {
        this.shapeInToolbar = shape;
        this.controller = controller;
        this.posX = x;
        this.posY = y;
    }

    @Override
    public void execute() {
        shapeCreated = controller.createShapeInCanvas(controller, shapeInToolbar, posX, posY, true);
    }

    @Override
    public void undo() {
        System.out.println("undo");
        controller.updateViewRemove(shapeCreated);
    }

    @Override
    public void redo() {
        shapeCreated = controller.createShapeInCanvas(controller, shapeInToolbar, posX, posY, true);
    }
}
