package sample.Controller.Command;

import sample.Controller.Controller;
import sample.Model.ShapeInter;

import java.util.ArrayList;

public class RotateCommand implements Command{

    private Controller controller;
    private ShapeInter shape;
    private boolean isShapeGroup;
    private double value;

    private double oldValue;
    private ArrayList<Double> oldvalues;

    public RotateCommand(Controller controller, ShapeInter shape, double value, boolean isShapeGroup){
        this.controller = controller;
        this.shape = shape;
        this.value = value;
        this.isShapeGroup = isShapeGroup;
        oldvalues = new ArrayList<>();
    }

    @Override
    public void execute() {
        shape.setRotation(value);
        if(isShapeGroup == true){
            for(ShapeInter child : shape.getChildren()){
                oldvalues.add(child.getRotation());
            }
        }else{
            oldValue = shape.getRotation();
        }
        controller.updateViewRotate(shape, value, isShapeGroup);
    }

    @Override
    public void undo() {
        if(isShapeGroup == true){
            for(int i = 0; i < oldvalues.size(); i++){
                double oldVal = oldvalues.get(i);
                ShapeInter currentChild = shape.getChild(i);
                currentChild.setRotation(-oldVal);
                controller.updateViewRotate(shape.getChild(i), currentChild.getRotation(), false);
            }
        }else{
            shape.setRotation(-oldValue);
            controller.updateViewRotate(shape, shape.getRotation(), false);
        }
    }

    @Override
    public void redo() {
        shape.setRotation(value);
    }
}
