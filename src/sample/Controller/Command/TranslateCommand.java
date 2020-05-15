package sample.Controller.Command;

import sample.Controller.Controller;
import sample.Model.ShapeInter;

public class TranslateCommand implements Command{
    private double dragX, dragY;
    private ShapeInter shape;
    private Controller controller;
    private boolean isShapeGroup;

    public TranslateCommand(double dragX, double dragY, ShapeInter shape, Controller controller, boolean isShapeGroup) {
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
