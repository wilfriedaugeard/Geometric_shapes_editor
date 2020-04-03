package sample.Controller;

import sample.Model.ShapeInter;

public class TranslateCommand implements Command{
    private double oldPosX, oldPosY;
    private double newPosX, newPosY;
    private ShapeInter shape;
    private Controller controller;

    public TranslateCommand(double newPosX, double newPosY, ShapeInter shape, Controller controller) {
        this.newPosX = newPosX;
        this.newPosY = newPosY;
        this.shape = shape;
        this.controller = controller;
    }

    @Override
    public void execute() {
        oldPosX = shape.getPos().getX();
        oldPosY = shape.getPos().getY();
        shape.translate(newPosX, newPosY);
        //controller.updateViewTranslate(newPosX,newPosY);
    }

    @Override
    public void undo() {
        shape.translate(oldPosX,oldPosY);
    }

    @Override
    public void redo() {
        execute();
    }

}
