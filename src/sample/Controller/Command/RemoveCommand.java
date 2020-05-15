package sample.Controller.Command;

import sample.Controller.Controller;
import sample.Model.ShapeInter;
import sample.View.IShapeDrawer;

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
        for(ShapeInter shapeGroup : controller.getShapeGroups()) {
            if (shape.equals(shapeGroup)) {
                listOfShape.add(shapeGroup);
            }
        }
        controller.updateViewRemove(shape);
    }

    /**
     * Create a shape
     * @param shape
     */
    private void createShape(ShapeInter shape){
        ShapeInter copy = shape.clone();
        IShapeDrawer drawer = copy.createShapeDrawer(controller);
        drawer.drawShape();
        controller.getShapesInCanvas().add(copy);
    }

    /**
     * Create a group shape
     * @param shape
     */
    private void createGroupShape(ShapeInter shape){
        controller.getShapeGroups().add(shape);
        for(int i = 0; i < shape.getChildren().size(); i++){
            createShape(shape.getChild(i));
        }
    }

    @Override
    public void undo() {
        if(listOfShape.isEmpty()){
            createShape(shape);
        }else{
            createGroupShape(shape);
        }
        controller.updateEvents();

    }

    @Override
    public void redo() {
        execute();
    }
}
