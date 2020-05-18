package sample.Controller.Events;

import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Controller.Command.Command;
import sample.Controller.Command.ResizeCommand;
import sample.Controller.Controller;
import sample.Controller.Command.RemoveCommand;
import sample.Controller.Command.TranslateCommand;
import sample.Factory.PointFactory;
import sample.Model.Point;
import sample.Model.ShapeInter;


public class DragAndDropEvent implements Events {

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
            System.out.println(mouseEvent.getEventType());
            shapeInView = (Shape) mouseEvent.getSource();
            double x = controller.getView().getShapeXPositionInToolBar(shapeInView);
            double y = controller.getView().getShapeYPositionInToolBar(shapeInView);

            int indexOfShape = controller.getView().getShapesInCanvas().indexOf(shapeInView);
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

        Double toolbar_w = ((ToolBar) controller.getView().getToolBar()).getWidth();
        // Resize shape
        if(shapeToTranslate.getVector().get(0) >= toolbar_w){
            Command resizeCommand;
            double margin_left =((ToolBar) controller.getView().getToolBar()).getPadding().getLeft()+1;
            double margin_right = ((ToolBar) controller.getView().getToolBar()).getPadding().getRight()+1;
            System.out.println(margin_right);
            double value = ((toolbar_w*100)/shapeToTranslate.getVector().get(0))-(margin_right+margin_left);
            for (ShapeInter shapeGroup : controller.getShapeGroups()) {
                if (shapeGroup.getChildren().contains(shapeToTranslate)) {
                    resizeCommand = new ResizeCommand(controller, shapeGroup, value, true);
                    controller.addLastCommand(resizeCommand);
                    controller.setCurrentPosInCommands(controller.getNbCommands());
                    resizeCommand.execute();
                    return;
                }
            }
            resizeCommand = new ResizeCommand(controller, shapeToTranslate, value, false);
            controller.addLastCommand(resizeCommand);
            controller.setCurrentPosInCommands(controller.getNbCommands());
            resizeCommand.execute();
        }

        // View
        toolbar.getItems().add(itemPos, shapeInView);
        controller.getView().getShapesInToolBar().add(shapePos, shapeInView);
        controller.getView().getShapesInToolBar().get(shapePos).setTranslateX(0);
        controller.getView().getShapesInToolBar().get(shapePos).setTranslateY(0);

        // Controller
        controller.getShapesInToolBar().add(shapeToTranslate);

        controller.removeShape(shapeToTranslate, shapeInView);
        controller.updateEvents();

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
                for (ShapeInter shape : group.getChildren()){
                    if(!shapeExisted(shape)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean shapeExisted(ShapeInter shapeExisted){
        for (ShapeInter shapeInToolBar : controller.getShapesInToolBar()) {
            if (shapeExisted.getRGB() == shapeInToolBar.getRGB()
                    && shapeExisted.getRotation() == shapeInToolBar.getRotation()
                    && shapeExisted.getVector().get(0).equals(shapeInToolBar.getVector().get(0))) {
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
