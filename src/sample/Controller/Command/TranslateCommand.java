package sample.Controller.Command;

import sample.Controller.IController;
import sample.Model.IShapeInter;

public class TranslateCommand implements ICommand {
    private double dragX, dragY;
    private IShapeInter shape;
    private IController controller;
    private boolean isShapeGroup;

    public TranslateCommand(double dragX, double dragY, IShapeInter shape, IController controller, boolean isShapeGroup) {
        this.dragX = dragX;
        this.dragY = dragY;
        this.shape = shape;
        this.controller = controller;
        this.isShapeGroup = isShapeGroup;
    }


    @Override
    public void execute() {
        shape.translate(dragX, dragY);
        controller.updateViewTranslate(shape, dragX, dragY, isShapeGroup);
    }

    @Override
    public void undo() {
        shape.translate(-dragX,-dragY);
        controller.updateViewTranslate(shape, -dragX,-dragY, isShapeGroup);
    }

    @Override
    public void redo() {
        execute();
    }

}
