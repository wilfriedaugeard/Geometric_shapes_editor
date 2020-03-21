package sample.Controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Model.Point;
import sample.Model.RGB;
import sample.Model.Rectangle;
import sample.Model.ShapeInter;
import sample.View.ShapeDrawer;

public class GroupShapeEvent implements Events{
    protected Controller controller;
    private Point mousePosStart;
    private ShapeInter selectionRectangle;

    public GroupShapeEvent(Controller controller) {
        this.controller = controller;
    }

    EventHandler<MouseEvent> createSelectionRectangleOnClick = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            //if()mouseEvent.getSource() == NULL ou != Shape, pour être sûr qu'on ne fait pas un rectangle alors que y a un shape
            mousePosStart = new Point(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            selectionRectangle = new Rectangle(0, 0, mousePosStart, new RGB(0, 0, 0));
            ShapeDrawer drawer = selectionRectangle.createShapeDrawer(controller);
            drawer.drawShape();

            Shape shape = (Shape) mouseEvent.getSource();
            shape.setStrokeDashOffset(5);
        }
    };

    EventHandler<MouseEvent> moveSelectionRectangleOnPressingMouse = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            Shape shape = (Shape) mouseEvent.getSource();
            shape.toBack();
        }
    };

    EventHandler<MouseEvent> selectionRectangleInCanvas = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            Rectangle shape = (Rectangle) mouseEvent.getSource();

            double dragX = mouseEvent.getSceneX() - mousePosStart.getX();
            double dragY = mouseEvent.getSceneY() - mousePosStart.getY();
            //calculate new position
            double newXPosition = mousePosStart.getX() + dragX;
            double newYPosition = mousePosStart.getY() + dragY;


        }
    };

    EventHandler<MouseEvent> releasedMouseAndCreateGroupShape = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            Point MousePosEnd = new Point(mouseEvent.getSceneX(),mouseEvent.getSceneY());
            Shape shape = (Shape) mouseEvent.getSource();
            controller.getView().getShapesInCanvas().remove(shape);
            double CenterX; double CenterY;
            for(Shape s : controller.getView().getShapesInCanvas()){

            }
        }
    };

    @Override
    public void launchEvent() {

    }
}
