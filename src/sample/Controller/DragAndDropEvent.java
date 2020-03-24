package sample.Controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Model.Point;
import sample.Model.RGB;
import sample.Model.ShapeInter;
import javafx.scene.shape.MoveTo;
import sample.View.ShapeDrawer;

import java.util.ArrayList;


public class DragAndDropEvent implements Events {

    private double shapeX, shapeY;
    private Point MousePos;
    private ShapeInter model;
    protected Controller controller;

    public DragAndDropEvent(Controller controller) {
        this.controller = controller;
    }

    EventHandler<MouseEvent> finalShapeToCanvas = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton() != MouseButton.PRIMARY){
                mouseEvent.consume();
                return;
            }

            Shape shape = (Shape) mouseEvent.getSource();

            double x = controller.getView().getShapeXPositionInToolBar(shape);
            double y = controller.getView().getShapeYPositionInToolBar(shape);

            double dragX = mouseEvent.getSceneX() - MousePos.getX();
            double dragY = mouseEvent.getSceneY() - MousePos.getY();
            //calculate new position
            double newXPosition = shapeX + dragX;
            double newYPosition = shapeY + dragY;

            ArrayList<Shape> shapes = controller.getView().getShapesInCanvas();
            if(shapes.size() == controller.getShapesInCanvas().size()) {
                double shapeX, shapeY;
                for(int i = 0 ; i < shapes.size(); i++) {
                    shapeX = controller.getView().getShapeXPositionInToolBar(shapes.get(i));
                    shapeY = controller.getView().getShapeYPositionInToolBar(shapes.get(i));
                    if(shapeX == x && shapeY == y) {

//                        boolean isShapeInGroup = false;
                        ShapeInter shapeMoved = controller.getShapesInCanvas().get(i);
                        Command translateCommand = null;
//
//                        for (ShapeInter shapeGroup : controller.getShapeGroups()){
//                            if(shapeGroup.getChildren().contains(shapeMoved)){
//                                isShapeInGroup = true;
//                                translateCommand = new TranslateCommand(newXPosition, newYPosition, shapeGroup, controller);
//                            }
                        //}
                        //if(isShapeInGroup == false) {
                            translateCommand = new TranslateCommand(newXPosition, newYPosition, shapeMoved, controller);
                        //}
                        controller.getCommands().addLast(translateCommand);
                        translateCommand.execute();
                    }
                }
            }
            shape.setTranslateX(newXPosition);
            shape.setTranslateY(newYPosition);
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
            Shape shape = (Shape) mouseEvent.getSource();

            shapeX = shape.getTranslateX();
            shapeY = shape.getTranslateY();
            MousePos = new Point(mouseEvent.getSceneX(), mouseEvent.getSceneY());

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
                        controller.getShapesInCanvas().get(i).setPos(new Point(shapeX, shapeY));
                    }
                }
            }
            ArrayList<ShapeInter> shapeOnToolbar = new ArrayList<>();
            for (ShapeInter model : controller.getShapesInCanvas()) {
                if (model.getPos().getX() == x && model.getPos().getY() == y) {
                    // Test if the released shape is on the trash
                    if (controller.getView().isOnNode(controller.getView().getTrash(), new Point(x, y))) {
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
                        if (controller.getView().isOnNode(controller.getView().getToolBar(),new Point(x,y)) && !sameShapeInToolBar(model)){
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
