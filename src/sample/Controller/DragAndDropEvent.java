package sample.Controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Factory.PointFactory;
import sample.Model.Point;
import sample.Model.ShapeInter;
import sample.View.ShapeDrawer;

import java.util.ArrayList;


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

    EventHandler<MouseEvent> finalShapeToCanvas = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                mouseEvent.consume();
                return;
            }

            double dragX = mouseEvent.getSceneX() - MousePos.getX();
            double dragY = mouseEvent.getSceneY() - MousePos.getY();

            Command translateCommand = null;

            if(isInShapeGroup){
                translateCommand = new TranslateCommand(dragX,dragY, shapeGroupToModify, controller);
            }else {
                translateCommand = new TranslateCommand(dragX, dragY, shapeToTranslate, controller);
            }

            controller.addLastCommand(translateCommand);
            controller.setCurrentPosInCommands(controller.getNbCommands());
            translateCommand.execute();

            MousePos.setX(mouseEvent.getSceneX());
            MousePos.setY(mouseEvent.getSceneY());

            mouseEvent.consume();
        }
    };



    EventHandler<MouseEvent> moveShapeOnPressingMouse = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton() != MouseButton.PRIMARY){
                mouseEvent.consume();
                return;
            }
            Shape shape = (Shape) mouseEvent.getSource();

            shape.toFront();
        }
    };

    EventHandler<MouseEvent> getShapeOnMousePressed = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton() != MouseButton.PRIMARY){
                mouseEvent.consume();
                return;
            }

            shapeInView = (Shape) mouseEvent.getSource();

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


    public void onTrash(){
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
        int itemPos = controller.getView().getToolBar().getItems().size()-2;
        int shapePos = controller.getShapesInToolBar().size();
        // View
        controller.getView().getToolBar().getItems().add(itemPos, shapeInView);
        controller.getView().getShapesInToolBar().add(shapePos, shapeInView);
        controller.getView().getShapesInCanvas().remove(shapeInView);

        // Controller
        controller.getShapesInToolBar().add(shapeToTranslate);
        controller.getShapesInCanvas().remove(shapeToTranslate);

        controller.getView().getShapesInToolBar().get(shapePos).setTranslateX(0);
        controller.getView().getShapesInToolBar().get(shapePos).setTranslateY(0);

        controller.updateEvents();

    }

    EventHandler<MouseEvent> overToolbar = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton() != MouseButton.PRIMARY){
                mouseEvent.consume();
                return;
            }

            double x = MousePos.getX();
            double y = MousePos.getY();

            double shapeY = controller.getView().getShapeYPositionInToolBar(shapeInView);
            double shapeX = controller.getView().getShapeXPositionInToolBar(shapeInView);
            shapeToTranslate.setPos(PointFactory.getPoint(shapeX, shapeY));

            // Test if the released shape is on the trash
            if (controller.getView().isOnNode(controller.getView().getTrash(), PointFactory.getPoint(x, y)))
                onTrash();
            // Test if the released shape is on Toolbar and if has been modify
            else{
                if (controller.getView().isOnNode(controller.getView().getToolBar(),PointFactory.getPoint(x,y)) && !sameShapeInToolBar(shapeToTranslate))
                    addToToolbar();
            }

            mouseEvent.consume();
        }
    };

    /**
     * Check if the shapeInCanvas has a same shape in toolbar
     */
    public boolean sameShapeInToolBar(ShapeInter shapeInCanvas) {
        for (ShapeInter shapeInToolBar : controller.getShapesInToolBar()) {
            if (shapeInCanvas.getRGB() == shapeInToolBar.getRGB()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void launchEvent() {
        controller.getView().launch_finalShapeToCanvas(finalShapeToCanvas);
        controller.getView().launch_moveShapeOnPressingMouse(moveShapeOnPressingMouse);
        controller.getView().launch_getShapeOnMousePressed(getShapeOnMousePressed);
        controller.getView().launch_overToolbar(overToolbar);
    }

}
