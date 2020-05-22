package sample.Controller;

import javafx.scene.shape.Shape;
import sample.Controller.Command.Command;
import sample.Controller.Events.Event;
import sample.Model.ShapeInter;
import sample.View.IView;

import java.util.ArrayList;
import java.util.LinkedList;

public class ControllerJavaFXAdaptee implements Controller {
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
    public void updateViewTranslate(ShapeInter shape, double dragX, double dragY, boolean isShapeGroup) {
        controllerJavaFX.updateViewTranslate(shape, dragX, dragY, isShapeGroup);
    }

    @Override
    public void removeShape(ShapeInter shapeModel, Object shapeView) {
        controllerJavaFX.removeShape(shapeModel, (Shape) shapeView);
    }

    @Override
    public void updateViewRemove(ShapeInter shape) {
        controllerJavaFX.updateViewRemove(shape);
    }

    @Override
    public void updateViewRotate(ShapeInter shape, double value, boolean isShapeGroup) {
        controllerJavaFX.updateViewRotate(shape, value, isShapeGroup);
    }

    @Override
    public void updateViewResize(ShapeInter shape) {
        controllerJavaFX.updateViewResize(shape);
    }

    @Override
    public void addShapeInToolbar(ShapeInter shape, Controller controller, int itemPos, int shapePos) {
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
    public ShapeInter getShapeGroupTmp() {
        return controllerJavaFX.getShapeGroupTmp();
    }

    @Override
    public void setShapeGroupTmp(ShapeInter shapeGroupTmp) {
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
    public ArrayList<ShapeInter> getShapesInToolBar() {
        return controllerJavaFX.getShapesInToolBar();
    }

    @Override
    public ArrayList<ShapeInter> getShapesInCanvas() {
        return controllerJavaFX.getShapesInCanvas();
    }

    @Override
    public ArrayList<ShapeInter> getShapeGroups() {
        return controllerJavaFX.getShapeGroups();
    }

    @Override
    public LinkedList<Command> getCommands() {
        return controllerJavaFX.getCommands();
    }

    @Override
    public void addLastCommand(Command command) {
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
    public void loadState(Controller controller) {
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
    public ArrayList<ShapeInter> getShapeGroupsInToolBar() {
        return controllerJavaFX.getShapeGroupsInToolBar();
    }
}
