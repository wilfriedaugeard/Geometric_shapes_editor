package sample.Controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Model.Point;
import sample.Model.ShapeInter;

import java.util.ArrayList;


public class DragAndDrop implements Events {

    private double shapeX, shapeY;
    private Point2D MousePos;
    protected Controller controller;

    public DragAndDrop(Controller controller) {
        this.controller = controller;
    }

    EventHandler<MouseEvent> finalShapeToCanvas = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
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
                        controller.getShapesInCanvas().get(i).setPos(new Point(shapeX,shapeY));
                    }
                }
            }

            for(ShapeInter model : controller.getShapesInCanvas()) {
                if(model.getPos().getX() == x && model.getPos().getY() == y) {
                    model.translate(dragX,dragY);

                }
            }

            shape.setTranslateX(newXPosition);
            shape.setTranslateY(newYPosition);

        }
    };

    EventHandler<MouseEvent> moveShapeOnPressingMouse = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            Shape shape = (Shape) mouseEvent.getSource();
            shape.toFront();
        }
    };

    EventHandler<MouseEvent> getShapeOnMousePressed = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            Shape shape = (Shape) mouseEvent.getSource();

            shapeX = shape.getTranslateX();
            shapeY = shape.getTranslateY();
            MousePos = new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY());

        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_finalShapeToCanvas(finalShapeToCanvas);
        controller.getView().launch_moveShapeOnPressingMouse(moveShapeOnPressingMouse);
        controller.getView().launch_getShapeOnMousePressed(getShapeOnMousePressed);
    }

}
