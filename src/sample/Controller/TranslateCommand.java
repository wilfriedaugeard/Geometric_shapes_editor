package sample.Controller;

import sample.Model.ShapeInter;

public class TranslateCommand implements Command{
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
        shape.translate(newPosX, newPosY);
        //controller.updateViewTranslate(newPosX,newPosY);
    }

}
