package sample.Controller.Command;

import sample.Controller.IController;
import sample.Model.IShapeInter;

import java.util.ArrayList;

public class AddToToolbarCommand implements ICommand{
    private IShapeInter shapeInToolbar;
    private IShapeInter shapeCreated;
    private IController controller;
    private double posX, posY;
    private Boolean isInShapeGroup;
    private ArrayList<IShapeInter> copyList;

    public AddToToolbarCommand(IShapeInter shapeInToolbar, Boolean isInShapeGroup, IController controller) {
        this.shapeInToolbar = shapeInToolbar;
        this.controller = controller;
        this.isInShapeGroup = isInShapeGroup;
    }

    @Override
    public void execute() {
        posX = shapeInToolbar.getPos().getX();
        posY = shapeInToolbar.getPos().getY();
        controller.addToToolbar(controller, shapeInToolbar, isInShapeGroup);
    }

    @Override
    public void undo() {
        controller.removeShapeInToolbar(shapeInToolbar, isInShapeGroup);
        shapeCreated = controller.createShapeInCanvas(controller, shapeInToolbar, posX, posY, true);
    }

    @Override
    public void redo() {
        controller.addToToolbar(controller, shapeCreated, isInShapeGroup);
    }
}
