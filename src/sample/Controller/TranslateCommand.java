package sample.Controller;

import sample.Model.ShapeInter;

public class TranslateCommand implements Command{
    private double dragX, dragY;
    private ShapeInter shape;
    private Controller controller;

    public TranslateCommand(double dragX, double dragY, ShapeInter shape, Controller controller) {
        this.dragX = dragX;
        this.dragY = dragY;
        this.shape = shape;
        this.controller = controller;
    }


    @Override
    public void execute() {
        shape.translate(dragX, dragY);
        controller.updateViewTranslate(dragX, dragY);
    }

    @Override
    public void undo() {
        shape.translate(-dragX,-dragY);
        controller.updateViewTranslate(-dragX,-dragY);
    }

    @Override
    public void redo() {
        execute();
    }

}
