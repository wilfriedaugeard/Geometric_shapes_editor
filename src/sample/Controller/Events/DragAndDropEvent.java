package sample.Controller.Events;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;
import sample.Controller.Command.Command;
import sample.Controller.Command.ResizeCommand;
import sample.Controller.Controller;
import sample.Controller.Command.RemoveCommand;
import sample.Controller.Command.TranslateCommand;
import sample.Factory.PointFactory;
import sample.Model.Point;
import sample.Model.ShapeInter;
import sample.View.IShapeDrawer;


public class DragAndDropEvent implements Event {

    /*No shape group case*/
    private ShapeInter shapeToTranslate;

    /*Shape group case*/
    private ShapeInter shapeGroupToModify;
    private boolean isInShapeGroup;

    /*General cases*/
    private Shape shapeInView;
    private Point MousePos;
    protected Controller controller;

    private Point BeginPos;

    public DragAndDropEvent(Controller controller) {
        this.controller = controller;
    }

    EventHandler<MouseEvent> finalShapeToCanvas = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                mouseEvent.consume();
                return;
            }

            double dragX = mouseEvent.getSceneX() - MousePos.getX();
            double dragY = mouseEvent.getSceneY() - MousePos.getY();

            Command translateCommand;

            if (isInShapeGroup) {
                translateCommand = new TranslateCommand(dragX, dragY, shapeGroupToModify, controller, isInShapeGroup);
            } else {
                translateCommand = new TranslateCommand(dragX, dragY, shapeToTranslate, controller, isInShapeGroup);
            }

            controller.addLastCommand(translateCommand);
            controller.setCurrentPosInCommands(controller.getNbCommands());
            translateCommand.execute();

            MousePos.setX(mouseEvent.getSceneX());
            MousePos.setY(mouseEvent.getSceneY());

            mouseEvent.consume();
        }
    };

    EventHandler<MouseEvent> moveShapeOnPressingMouse = mouseEvent -> {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) {
            mouseEvent.consume();
            return;
        }
        Shape shape = (Shape) mouseEvent.getSource();

        shape.toFront();
    };

    EventHandler<MouseEvent> getShapeOnMousePressed = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton() != MouseButton.PRIMARY){
                mouseEvent.consume();
                return;
            }
            shapeInView = (Shape) mouseEvent.getSource();
            double x = controller.getView().getShapeXPositionInToolBar(shapeInView);
            double y = controller.getView().getShapeYPositionInToolBar(shapeInView);

            int indexOfShape = controller.getView().getShapesInCanvas().indexOf(shapeInView);
            if(indexOfShape < 0){
                mouseEvent.consume();
                return;
            }

            shapeToTranslate = controller.getShapesInCanvas().get(indexOfShape);
            isInShapeGroup = false;

            //Get the shape's group if it is in a ShapeGroup
            for(ShapeInter shapeGroup : controller.getShapeGroups()){
                if(shapeGroup.getChildren().contains(shapeToTranslate)){
                    shapeGroupToModify = shapeGroup;
                    isInShapeGroup = true;
                }
            }

            MousePos = PointFactory.getPoint(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            BeginPos = PointFactory.getPoint(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            mouseEvent.consume();
        }
    };


    private void onTrash(){
        Command removeCommand;

        if(isInShapeGroup){
            removeCommand = new RemoveCommand(shapeGroupToModify, controller);
        }else {
            removeCommand = new RemoveCommand(shapeToTranslate, controller);
        }

        controller.addLastCommand(removeCommand);
        controller.setCurrentPosInCommands(controller.getNbCommands());
        removeCommand.execute();

    }

    public void addToToolbar(){
        ToolBar toolbar = (ToolBar) controller.getView().getToolBar();
        int itemPos = toolbar.getItems().size()-2;
        int shapePos = controller.getShapesInToolBar().size();

        double toolbar_w = toolbar.getWidth();
        // Resize shape
        if(shapeToTranslate.getWidth() >= toolbar_w){
            double margin_left = toolbar.getPadding().getLeft();
            double margin_right = toolbar.getPadding().getRight();
            double value = (toolbar_w-margin_left-margin_right)/shapeToTranslate.getWidth();
            value *= 100;
            Command resizeCommand;
            if(isInShapeGroup){
                for (ShapeInter shapeGroup : controller.getShapeGroups()) {
                    if (shapeGroup.getChildren().contains(shapeToTranslate)) {
                        if(shapeGroup.getWidth() >= toolbar_w) {
                            value = (toolbar_w-margin_left-margin_right)/shapeGroup.getWidth();
                            value *= 100;
                            resizeCommand = new ResizeCommand(controller, shapeGroup, value, true);
                            controller.addLastCommand(resizeCommand);
                            controller.setCurrentPosInCommands(controller.getNbCommands());
                            resizeCommand.execute();
                        }
                        controller.addShapeInToolbar(shapeGroup, controller, itemPos,shapePos);
                        int index;
                        for(ShapeInter child : shapeGroup.getChildren()){
                            index = controller.getShapesInCanvas().indexOf(child);
                            controller.removeShape(child, controller.getView().getShapesInCanvas().get(index));
                        }
                        controller.updateEvents();
                        controller.saveState();
                        return;
                    }
                }
            }else{
                if(shapeToTranslate.getWidth() >= toolbar_w) {
                    resizeCommand = new ResizeCommand(controller, shapeToTranslate, value, false);
                    controller.addLastCommand(resizeCommand);
                    controller.setCurrentPosInCommands(controller.getNbCommands());
                    resizeCommand.execute();
                }
            }
        }

        // View
        //toolbar.getItems().add(itemPos, shapeInView);
        //controller.getView().getShapesInToolBar().add(shapePos, shapeInView);

        // Controller
        controller.addShapeInToolbar(shapeToTranslate, controller, itemPos,shapePos);
        controller.removeShape(shapeToTranslate, shapeInView);
        controller.updateEvents();
        controller.saveState();
    }

    EventHandler<MouseEvent> overToolbar = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                mouseEvent.consume();
                return;
            }

            double x = MousePos.getX();
            double y = MousePos.getY();

            double shapeY = controller.getView().getShapeYPositionInToolBar(shapeInView);
            double shapeX = controller.getView().getShapeXPositionInToolBar(shapeInView);
            shapeToTranslate.setPos(PointFactory.getPoint(shapeX, shapeY));

            // Test if the released shape is on the trash
            if (controller.getView().isOn(controller.getView().getTrash(), PointFactory.getPoint(x, y)))
                onTrash();
                // Test if the released shape is on Toolbar and if has been modify
            else {
                if (controller.getView().isOn(controller.getView().getToolBar(), PointFactory.getPoint(x, y)) && !sameShapeInToolBar(shapeToTranslate))
                    addToToolbar();
                    mouseEvent.consume();
            }

            mouseEvent.consume();
        }
    };


    public boolean sameShapeGroupInToolBar(ShapeInter shapeGroupInToolbar){
        for (ShapeInter group : controller.getShapeGroups()){
            if(group.getChildren().contains(shapeGroupInToolbar)){
                System.out.println(group);
                for (ShapeInter shape : group.getChildren()){
                    if(!shapeExisted(shape)){
                        return false;
                    }
                }
                if(group.getWidth()/group.getCoeff() != shapeGroupInToolbar.getWidth()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean shapeExisted(ShapeInter shapeExisted){
        for (ShapeInter shapeInToolBar : controller.getShapesInToolBar()) {
            if (shapeExisted.getRGB() == shapeInToolBar.getRGB()
                    && shapeExisted.getRotation() == shapeInToolBar.getRotation()
                    && shapeExisted.getWidth() == shapeInToolBar.getWidth()/shapeInToolBar.getCoeff()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the shapeInCanvas has a same shape in toolbar
     */
    public boolean sameShapeInToolBar(ShapeInter shapeInCanvas) {
        if(isInShapeGroup){
            return sameShapeGroupInToolBar(shapeInCanvas);
        }
        else{
            return  shapeExisted(shapeInCanvas);
        }
    }

    @Override
    public void launchEvent() {
        controller.getView().launch_finalShapeToCanvas(finalShapeToCanvas);
        controller.getView().launch_moveShapeOnPressingMouse(moveShapeOnPressingMouse);
        controller.getView().launch_getShapeOnMousePressed(getShapeOnMousePressed);
        controller.getView().launch_overToolbar(overToolbar);
    }

}
