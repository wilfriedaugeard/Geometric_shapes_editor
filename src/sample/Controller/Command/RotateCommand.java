package sample.Controller.Command;

import sample.Controller.IController;
import sample.Model.IShapeInter;

import java.util.ArrayList;

/**
 * Command to set a rotation to a shape
 */
public class RotateCommand implements ICommand {

    private IController controller;
    private IShapeInter shape;
    private boolean isShapeGroup;
    private double value;

    private double oldValue;
    private ArrayList<Double> oldvalues;

    public RotateCommand(IController controller, IShapeInter shape, double value, boolean isShapeGroup){
        this.controller = controller;
        this.shape = shape;
        this.value = value;
        this.isShapeGroup = isShapeGroup;
        oldvalues = new ArrayList<>();
    }

    @Override
    public void execute() {
        shape.setRotation(value);
        if(isShapeGroup){
            for(IShapeInter child : shape.getChildren()){
                oldvalues.add(child.getRotation());
                controller.updateViewRotate(child, child.getRotation());
            }
        }else{
            oldValue = shape.getRotation();
            controller.updateViewRotate(shape, shape.getRotation());
        }
    }

    @Override
    public void undo() {
        if(isShapeGroup){
            for(int i = 0; i < oldvalues.size(); i++){
                double oldVal = oldvalues.get(i);
                IShapeInter currentChild = shape.getChild(i);
                currentChild.setRotation(-oldVal);
                controller.updateViewRotate(shape.getChild(i), currentChild.getRotation());
            }
        }else{
            shape.setRotation(-oldValue);
            controller.updateViewRotate(shape, shape.getRotation());
        }
    }

    @Override
    public void redo() {
        shape.setRotation(value);
        if(isShapeGroup){
            for(IShapeInter child : shape.getChildren()){
                controller.updateViewRotate(child, child.getRotation());
            }
        }else{
            controller.updateViewRotate(shape, shape.getRotation());
        }
    }
}
