package sample.Controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sample.Model.*;
import sample.View.ShapeDrawer;

import java.util.ArrayList;

public class GroupShapeEvent implements Events{
    protected Controller controller;
    private Point mousePosStart;
    private Rectangle selectionRectangle;

    public GroupShapeEvent(Controller controller) {
        this.controller = controller;
    }


    public boolean isOnToolbar(double x, double y){
        return x <= controller.getView().getToolBar().getWidth();
    }


    EventHandler<MouseEvent> createSelectionRectangleOnClick = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getSource() == controller.getView().getRoot() && !isOnToolbar(mouseEvent.getX(), mouseEvent.getY())) {
                mousePosStart = new Point(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                selectionRectangle = new Rectangle(mousePosStart.getX(), mousePosStart.getY(), 0, 0);
                controller.getView().getRoot().getChildren().add(selectionRectangle);

                /* CSS */
                selectionRectangle.setFill(Color.rgb(133,213,243));
                selectionRectangle.setStroke(Color.rgb(0,194,225));
                selectionRectangle.setStrokeDashOffset(10);
                selectionRectangle.getStrokeDashArray().addAll(3.0,7.0,3.0,7.0);
                selectionRectangle.setOpacity(0.3);
            }
        }
    };

    EventHandler<MouseEvent> moveSelectionRectangleOnPressingMouse = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
           // selectionRectangle.toBack();
        }
    };

    EventHandler<MouseEvent> selectionRectangleDraggedInCanvas = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            //selectionRectangle.toBack();
            if(selectionRectangle!=null) {
                double dragX = mouseEvent.getSceneX() - mousePosStart.getX();
                double dragY = mouseEvent.getSceneY() - mousePosStart.getY();
                //calculate new position
                double newWidth = mousePosStart.getX() + dragX;
                double newHeight = mousePosStart.getY() + dragY;
                if (newWidth >= 0 && newHeight >= 0) {
                    selectionRectangle.setWidth(dragX);
                    selectionRectangle.setHeight(dragY);
                }
            }
        }
    };

    EventHandler<MouseEvent> releasedMouseAndCreateGroupShape = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(selectionRectangle!=null) {
                Point MousePosEnd = new Point(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                ShapeGroup shapeGroup = new ShapeGroup();
                controller.getView().getRoot().getChildren().remove(selectionRectangle);
                for (ShapeInter s : controller.getShapesInCanvas()) {
                    Point rotationCenter = s.getRotationCenter();
                    if (rotationCenter.getX() >= mousePosStart.getX() && rotationCenter.getX() <= MousePosEnd.getX()
                            && rotationCenter.getY() >= mousePosStart.getY() && rotationCenter.getY() <= MousePosEnd.getY()) {
                        shapeGroup.add(s);
                    }
                }
            }
            /*
            if (shapeGroup.getChildren().isEmpty()==false){
                controller.getShapeGroups().add(shapeGroup);
            }*/
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_createSelectionRectangleOnClick(createSelectionRectangleOnClick);
        controller.getView().launch_rectangleSelectionReleased(releasedMouseAndCreateGroupShape);
        controller.getView().launch_selectionRectangleDraggedInCanvas(selectionRectangleDraggedInCanvas);
        controller.getView().launch_moveSelectionRectangleOnPressingMouse(moveSelectionRectangleOnPressingMouse);
    }
}
