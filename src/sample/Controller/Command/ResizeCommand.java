package sample.Controller.Command;

import sample.Controller.Controller;
import sample.Model.ShapeInter;

import java.util.ArrayList;

public class ResizeCommand implements Command {

    private Controller controller;
    private ShapeInter shape;
    private boolean isShapeGroup;
    private double coeff;

    private ArrayList<Double> oldVector;
    private ArrayList<ArrayList<Double>> oldVectors;

    public ResizeCommand(Controller controller, ShapeInter shape, double value, boolean isShapeGroup){
        this.controller = controller;
        this.shape = shape;
        this.coeff = value/100;
        this.isShapeGroup = isShapeGroup;
    }

    @Override
    public void execute() {
        if(isShapeGroup){
            for(ShapeInter shapeChild : shape.getChildren()){
                ArrayList<Double> vector = shapeChild.getVector();
                ArrayList<Double> oldVecChild = new ArrayList<>();
                for(int i = 0; i < vector.size(); i++){
                    oldVecChild.add(vector.get(i));
                    vector.set(i, vector.get(i) * coeff);
                }
                oldVectors.add(oldVecChild);
                shapeChild.setVector(vector);
                controller.updateViewResize(shapeChild);
            }
        }else{
            ArrayList<Double> vector = shape.getVector();
            oldVector = new ArrayList<>();
            for(int i = 0; i < vector.size(); i++){
                oldVector.add(vector.get(i));
                vector.set(i, vector.get(i) * coeff);
            }
            shape.setVector(vector);
            controller.updateViewResize(shape);
        }
    }

    @Override
    public void undo() {
        if(isShapeGroup){
            for(int i = 0; i < shape.getChildren().size(); i++){
                shape.getChild(i).setVector(oldVectors.get(i));
                controller.updateViewResize(shape.getChild(i));
            }
        }else{
            shape.setVector(oldVector);
            controller.updateViewResize(shape);
        }
    }

    @Override
    public void redo() {
        if(isShapeGroup){
            for(ShapeInter shapeChild : shape.getChildren()){
                ArrayList<Double> vector = shapeChild.getVector();
                for(int i = 0; i < vector.size(); i++){
                    vector.set(i, vector.get(i) * coeff);
                }
                shapeChild.setVector(vector);
                controller.updateViewResize(shapeChild);
            }
        }else{
            ArrayList<Double> vector = shape.getVector();
            for(int i = 0; i < vector.size(); i++){
                vector.set(i, vector.get(i) * coeff);
            }
            shape.setVector(vector);
            controller.updateViewResize(shape);
        }
    }
}
