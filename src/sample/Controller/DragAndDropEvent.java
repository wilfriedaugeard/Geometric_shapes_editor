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
    private ShapeInter model;

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

            if(isInShapeGroup == true){
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

           mouseEvent.consume();
        }
    };

    EventHandler<MouseEvent> overTrash = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {

            Shape shape = (Shape) mouseEvent.getSource();

            double x = controller.getView().getShapeXPositionInToolBar(shape);
            double y = controller.getView().getShapeYPositionInToolBar(shape);

            ArrayList<Shape> shapes = controller.getView().getShapesInCanvas();
            if (shapes.size() == controller.getShapesInCanvas().size()) {
                double shapeX, shapeY;
                for (int i = 0; i < shapes.size(); i++) {
                    shapeX = controller.getView().getShapeXPositionInToolBar(shapes.get(i));
                    shapeY = controller.getView().getShapeYPositionInToolBar(shapes.get(i));
                    if (shapeX == x && shapeY == y) {
                        controller.getShapesInCanvas().get(i).setPos(PointFactory.getPoint(shapeX, shapeY));
                    }
                }
            }
            ArrayList<ShapeInter> shapeOnToolbar = new ArrayList<>();
            for (ShapeInter model : controller.getShapesInCanvas()) {
                if (model.getPos().getX() == x && model.getPos().getY() == y) {
                    // Test if the released shape is on the trash
                    if (controller.getView().isOnNode(controller.getView().getTrash(), PointFactory.getPoint(x, y))) {
                        if (!controller.getView().getShapesInCanvas().remove(shape)) {
                            System.out.println("Shape in view.getShapesCanvas not find");
                        }
                        if (!controller.getShapesInCanvas().remove(model)) {
                            System.out.println("model in getShapesInCanvas not find");
                        }
                        controller.getView().getRoot().getChildren().remove(shape);
                        return;
                    }else{
                        // Test if the released shape is on Toolbar and if has been modify
                        if (controller.getView().isOnNode(controller.getView().getToolBar(),PointFactory.getPoint(x,y)) && !sameShapeInToolBar(model)){
                            // View
                            int pos = controller.getView().getToolBar().getItems().size();
                            controller.getView().getToolBar().getItems().add(pos-1, shape);
                            controller.getView().getShapesInCanvas().remove(shape);
                            // Add shape on the list
                            shapeOnToolbar.add(model);
                        }
                    }
                }
            }
            // Controller modification
            if(shapeOnToolbar.size() != 0){
                // Draw shape
                for(ShapeInter modelToDraw : shapeOnToolbar) {
                    controller.getShapesInToolBar().add(model);
                    ShapeDrawer drawer = modelToDraw.createShapeDrawer(controller);
                    drawer.drawShapeInToolBar();
                }
                // Remove shape in Canvas Controller
                for(ShapeInter modelToRemove : shapeOnToolbar){
                    controller.getShapesInCanvas().remove(modelToRemove);
                }

            }
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
        controller.getView().launch_overTrash(overTrash);
    }

}
