package sample.Controller.Command;

import sample.Controller.IController;
import sample.Model.IShapeInter;
import sample.View.Drawer.IShapeDrawer;

import java.util.ArrayList;

/**
 * Command to remove a shape
 */
public class RemoveCommand implements ICommand {
    private IShapeInter shape;
    private IController controller;
    private ArrayList<IShapeInter> listOfShape;


    public RemoveCommand(IShapeInter shape, IController controller) {
        this.shape = shape;
        this.controller = controller;
        this.listOfShape = new ArrayList<>();
    }

    @Override
    public void execute() {
        for(IShapeInter shapeGroup : controller.getShapeGroups()) {
            if (shape.equals(shapeGroup)) {
                listOfShape.add(shapeGroup);
            }
        }
        controller.updateViewRemove(shape);
    }

    /**
     * Create a shape
     * @param shape The shape to create
     */
    private void createShape(IShapeInter shape){
        IShapeInter copy = shape.clone();
        IShapeDrawer drawer = copy.createShapeDrawer(controller);
        drawer.drawShape();
        controller.getShapesInCanvas().add(copy);

    }

    /**
     * Create a group shape
     * @param shape The group shape to create
     */
    private void createGroupShape(IShapeInter shape){
        ArrayList<IShapeInter> copyList = new ArrayList<>();
        for(IShapeInter child : shape.getChildren()){
            IShapeInter copy = child.clone();
            IShapeDrawer drawer = child.createShapeDrawer(controller);
            drawer.drawShape();
            controller.getShapesInCanvas().add(copy);
            copyList.add(copy);
        }
        shape.getChildren().clear();
        shape.getChildren().addAll(copyList);
        controller.getShapeGroups().add(shape);
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
