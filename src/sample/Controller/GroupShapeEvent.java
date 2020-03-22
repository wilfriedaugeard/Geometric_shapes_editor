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


    public boolean isOnToolbar(double x){
        return x <= controller.getView().getToolBar().getWidth();
    }


    EventHandler<MouseEvent> createSelectionRectangleOnClick = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            for(Shape item : controller.getView().getShapesInCanvas()){
                System.out.println(mouseEvent.getSceneX()+" "+mouseEvent.getSceneY());
                double x2 = controller.getView().getShapeXPositionInToolBar(item);
                double y2 = controller.getView().getShapeYPositionInToolBar(item);
                int i = controller.getView().getRoot().getChildren().indexOf(item);
                System.out.println(controller.getView().getRoot().getChildren().get(i));
                System.out.println(x2+" "+y2);
                System.out.println(item.contains(mouseEvent.getX(),mouseEvent.getY())+"\n");
                if(item.contains(mouseEvent.getX(),mouseEvent.getY())){
                    return;
                }
            }
            if(mouseEvent.getSource() == controller.getView().getRoot() && !isOnToolbar(mouseEvent.getX())) {
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

    EventHandler<MouseEvent> selectionRectangleDraggedInCanvas = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(selectionRectangle!=null) {
                double newWidth = mouseEvent.getSceneX() - mousePosStart.getX();
                double newHeight = mouseEvent.getSceneY() - mousePosStart.getY();
                //calculate new position
                selectionRectangle.setWidth(newWidth);
                selectionRectangle.setHeight(newHeight);
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
                        if(shapeGroup.getChildren().contains(s)==false) {
                            shapeGroup.add(s);
                        }
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
    }
}
