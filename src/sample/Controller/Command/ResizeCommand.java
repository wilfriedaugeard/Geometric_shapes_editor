package sample.Controller.Command;

import sample.Controller.IController;
import sample.Factory.PointFactory;
import sample.Model.Point;
import sample.Model.IShapeInter;

import java.util.ArrayList;

public class ResizeCommand implements ICommand {

    private IController controller;
    private IShapeInter shape;
    private boolean isShapeGroup;
    private double coeff;
    private double oldCoeff;


    private ArrayList<Double> oldVector;
    private ArrayList<ArrayList<Double>> oldVectors;

    public ResizeCommand(IController controller, IShapeInter shape, double value, boolean isShapeGroup){
        this.controller = controller;
        this.shape = shape;
        this.coeff = value/100;
        this.oldCoeff = shape.getCoeff();
        shape.setCoeff(coeff);
        this.isShapeGroup = isShapeGroup;
        oldVectors = new ArrayList<>();
    }

    @Override
    public void execute() {
        if(isShapeGroup){
            // Take the y min
            double min = Double.POSITIVE_INFINITY;
            IShapeInter shapeRef = shape;
            for(IShapeInter shapeChild : shape.getChildren()){
                min = Math.min(min, shapeChild.getPos().getY());
                shapeRef = shapeChild;
            }
            shapeRef.setDeltaX(0);
            shapeRef.setDeltaY(0);

            for(IShapeInter shapeChild : shape.getChildren()){
                if(!shapeChild.equals(shapeRef)){
                    double x = shapeChild.getPos().getX()-shapeRef.getPos().getX();
                    double y = shapeChild.getPos().getY()-shapeRef.getPos().getY();
                    shapeChild.setDeltaX(x);
                    shapeChild.setDeltaY(y);
                }
                ArrayList<Double> vector = shapeChild.getVector();
                ArrayList<Double> oldVecChild = new ArrayList<>();
                for(int i = 0; i < vector.size(); i++){
                    oldVecChild.add(vector.get(i));
                    vector.set(i, vector.get(i) * coeff);
                }
                shapeChild.setCoeff(coeff);
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
            shape.setCoeff(coeff);
            shape.setVector(vector);
            controller.updateViewResize(shape);
        }
    }

    @Override
    public void undo() {
        if(isShapeGroup){
            for(int i = 0; i < shape.getChildren().size(); i++){
                shape.getChild(i).setVector(oldVectors.get(i));
                shape.getChild(i).setCoeff(oldCoeff);
                Point p = PointFactory.getPoint(shape.getPos().getX()/coeff, shape.getPos().getY()/coeff);
                shape.setPos(p);
                controller.updateViewResize(shape.getChild(i));
            }
        }else{
            shape.setVector(oldVector);
            shape.setCoeff(oldCoeff);
            controller.updateViewResize(shape);
        }
    }

    @Override
    public void redo() {
        if(isShapeGroup){
            for(IShapeInter shapeChild : shape.getChildren()){
                ArrayList<Double> vector = shapeChild.getVector();
                for(int i = 0; i < vector.size(); i++){
                    vector.set(i, vector.get(i) * coeff);
                }
                shapeChild.setVector(vector);
                shapeChild.setCoeff(coeff);
                Point p = PointFactory.getPoint(shapeChild.getPos().getX()*coeff, shapeChild.getPos().getY()*coeff);
                shapeChild.setPos(p);
                controller.updateViewResize(shapeChild);
            }
        }else{
            ArrayList<Double> vector = shape.getVector();
            for(int i = 0; i < vector.size(); i++){
                vector.set(i, vector.get(i) * coeff);
            }
            shape.setVector(vector);
            shape.setCoeff(coeff);
            controller.updateViewResize(shape);
        }
    }
}
