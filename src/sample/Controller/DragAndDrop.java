package sample.Controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Model.ShapeInter;


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
            shape.setTranslateX(newXPosition);
            shape.setTranslateY(newYPosition);

            for(ShapeInter model : controller.getShapesInCanvas()) {
                System.out.println("x from get shape = " + x);
                System.out.println("x from model shapeinter = " + model.getPos().getX());
                System.out.println("y from get shape = " + y);
                System.out.println("y from model shapeinter = " + model.getPos().getY());
                if(model.getPos().getX() == x && model.getPos().getY() == y) {
                    model.translate(dragX,dragY);
                    System.out.println("TEST");
                }
            }

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
            System.out.println("TESTTTT = " + shapeX + " TESTTTT = " + shapeY);
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_finalShapeToCanvas(finalShapeToCanvas);
        controller.getView().launch_moveShapeOnPressingMouse(moveShapeOnPressingMouse);
        controller.getView().launch_getShapeOnMousePressed(getShapeOnMousePressed);
    }

}
