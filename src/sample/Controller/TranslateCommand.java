package sample.Controller;

import sample.Model.ShapeInter;

public class TranslateCommand implements Command{
    private double newPosX, newPosY;
    private ShapeInter shape;

    public TranslateCommand(double newPosX, double newPosY, ShapeInter shape) {
        this.newPosX = newPosX;
        this.newPosY = newPosY;
        this.shape = shape;
    }

    @Override
    public void execute() {
        shape.translate(newPosX, newPosY);
    }

}
