package sample.Controller.Command;

import sample.Controller.IController;
import sample.Model.IShapeInter;

import java.util.ArrayList;

public class DeGroupShapeCommand implements ICommand {

    protected IController controller;
    private IShapeInter shapeGroupTmp;
    private ArrayList<IShapeInter> shapeGroupsModified;
    private ArrayList<IShapeInter> shapesDeleted;

    public DeGroupShapeCommand(IController controller, IShapeInter shapeGroup){
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
        for(IShapeInter shapeGroup : controller.getShapeGroups()){
            for(IShapeInter shape : shapeGroup.getChildren()){
                for(IShapeInter shapetoDegroup : shapeGroupTmp.getChildren()){
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
            IShapeInter shapePreviouslyDeleted = shapesDeleted.get(i);
            shapeGroupsModified.get(i).add(shapePreviouslyDeleted);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
