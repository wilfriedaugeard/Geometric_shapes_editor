package sample.Controller;

import sample.Model.ShapeInter;

import java.util.ArrayList;

public class DeGroupShapeCommand implements Command{

    protected Controller controller;
    private ShapeInter shapeGroupTmp;
    private ArrayList<ShapeInter> shapeGroupsModified;
    private ArrayList<ShapeInter> shapesDeleted;

    public DeGroupShapeCommand(Controller controller, ShapeInter shapeGroup){
        this.controller = controller;
        this.shapeGroupTmp = shapeGroup;
        this.shapeGroupsModified = new ArrayList<>();
        this.shapesDeleted = new ArrayList<>();
    }

    @Override
    public void execute() {
        boolean hasNeverBeenExecuted = false;
        if(shapeGroupsModified.isEmpty()){
            hasNeverBeenExecuted = true;
        }
        for(ShapeInter shapeGroup : controller.getShapeGroups()){
            for(ShapeInter shape : shapeGroup.getChildren()){
                for(ShapeInter shapetoDegroup : shapeGroupTmp.getChildren()){
                    if(shape.equals(shapetoDegroup)){
                        if(hasNeverBeenExecuted==true) {
                            shapeGroupsModified.add(shapeGroup);
                            shapesDeleted.add(shape);
                        }
                        shapeGroup.remove(shape);
                    }
                }
            }
        }
    }

    @Override
    public void undo() {
        for(int i = 0; i < shapeGroupsModified.size(); i++){
            ShapeInter shapePreviouslyDeleted = shapesDeleted.get(i);
            shapeGroupsModified.get(i).add(shapePreviouslyDeleted);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
