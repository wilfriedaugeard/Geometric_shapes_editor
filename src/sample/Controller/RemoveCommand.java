package sample.Controller;

import sample.Model.ShapeInter;

import java.util.ArrayList;


public class RemoveCommand implements Command{
    private ShapeInter shape;
    private Controller controller;
    private ArrayList<ShapeInter> listOfShape;


    public RemoveCommand(ShapeInter shape, Controller controller) {
        this.shape = shape;
        this.controller = controller;
        this.listOfShape = new ArrayList<>();
    }

    @Override
    public void execute() {
        controller.updateViewRemove(shape);
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
