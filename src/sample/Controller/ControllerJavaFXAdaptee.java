package sample.Controller;

import javafx.scene.shape.Shape;
import sample.Controller.Command.ICommand;
import sample.Controller.Events.Event;
import sample.Model.IShapeInter;
import sample.View.IView;

import java.util.ArrayList;
import java.util.LinkedList;

public class ControllerJavaFXAdaptee implements IController {
    private ControllerJavaFX controllerJavaFX;

    public ControllerJavaFXAdaptee(ControllerJavaFX controllerJavaFX){
        this.controllerJavaFX = controllerJavaFX;
    }

    @Override
    public void initializeView() {
        controllerJavaFX.initializeView(this);
    }

    @Override
    public void updateViewColor() {
        controllerJavaFX.updateViewColor();
    }

    @Override
    public void updateViewTranslate(IShapeInter shape, double dragX, double dragY, boolean isShapeGroup) {
        controllerJavaFX.updateViewTranslate(shape, dragX, dragY, isShapeGroup);
    }

    @Override
    public void removeShape(IShapeInter shapeModel, Object shapeView) {
        controllerJavaFX.removeShape(shapeModel, (Shape) shapeView);
    }

    @Override
    public void updateViewRemove(IShapeInter shape) {
        controllerJavaFX.updateViewRemove(shape);
    }

    @Override
    public void updateViewRotate(IShapeInter shape, double value) {
        controllerJavaFX.updateViewRotate(shape, value);
    }

    @Override
    public void updateViewResize(IShapeInter shape) {
        controllerJavaFX.updateViewResize(shape);
    }

    @Override
    public void addShapeInToolbar(IShapeInter shape, IController controller, int itemPos, int shapePos) {
        controllerJavaFX.addShapeInToolbar(shape,controller,itemPos, shapePos);
    }

    @Override
    public void updateEvents() {
        controllerJavaFX.updateEvents();
    }

    @Override
    public void initEvents() {
        controllerJavaFX.initEvents(this);
    }

    @Override
    public ArrayList<Event> getEvents() {
        return controllerJavaFX.getEvents();
    }

    @Override
    public IShapeInter getShapeGroupTmp() {
        return controllerJavaFX.getShapeGroupTmp();
    }

    @Override
    public void setShapeGroupTmp(IShapeInter shapeGroupTmp) {
        controllerJavaFX.setShapeGroupTmp(shapeGroupTmp);
    }

    @Override
    public Object getScene() {
        return controllerJavaFX.getScene();
    }

    @Override
    public IView getView() {
        return controllerJavaFX.getView();
    }

    @Override
    public ArrayList<IShapeInter> getShapesInToolBar() {
        return controllerJavaFX.getShapesInToolBar();
    }

    @Override
    public ArrayList<IShapeInter> getShapesInCanvas() {
        return controllerJavaFX.getShapesInCanvas();
    }

    @Override
    public ArrayList<IShapeInter> getShapeGroups() {
        return controllerJavaFX.getShapeGroups();
    }

    @Override
    public LinkedList<ICommand> getCommands() {
        return controllerJavaFX.getCommands();
    }

    @Override
    public void addLastCommand(ICommand command) {
        controllerJavaFX.addLastCommand(command);
    }

    @Override
    public int getNbCommands() {
        return controllerJavaFX.getNbCommands();
    }

    @Override
    public int getCurrentPosInCommands() {
        return controllerJavaFX.getCurrentPosInCommands();
    }

    @Override
    public void setCurrentPosInCommands(int currentPosInCommands) {
        controllerJavaFX.setCurrentPosInCommands(currentPosInCommands);
    }

    @Override
    public void saveState() {
        controllerJavaFX.saveState();
    }

    @Override
    public void loadState(IController controller) {
        controllerJavaFX.loadState(controller);
    }

    @Override
    public boolean isExistState() {
        return controllerJavaFX.isExistState();
    }

    @Override
    public void setExistState(boolean existState) {
        controllerJavaFX.setExistState(existState);
    }

    @Override
    public IShapeInter createShapeInCanvas(IController controller, IShapeInter shapeModel, double x, double y, boolean applyCoeff) {
        return controllerJavaFX.createShapeInCanvas(controller, shapeModel,x,y, applyCoeff);
    }

    @Override
    public void addToToolbar(IController controller, IShapeInter shapeToTranslate, boolean isInShapeGroup) {
        controllerJavaFX.addToToolbar(controller, shapeToTranslate, isInShapeGroup);
    }

    @Override
    public void removeShapeInToolbar(IShapeInter shapeInToolbar, Boolean isInShapeGroup) {
        controllerJavaFX.removeShapeInToolbar(shapeInToolbar, isInShapeGroup);
    }

    @Override
    public ArrayList<IShapeInter> getShapeGroupsInToolBar() {
        return controllerJavaFX.getShapeGroupsInToolBar();
    }
}
