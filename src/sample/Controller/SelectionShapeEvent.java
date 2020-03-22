package sample.Controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sample.Model.*;
import sample.View.ShapeDrawer;

import java.util.ArrayList;

public class SelectionShapeEvent implements Events{
    protected Controller controller;
    private Point mousePosStart;
    private Rectangle selectionRectangle;
    private ShapeInter shapeGroup;

    public SelectionShapeEvent(Controller controller) {
        this.controller = controller;
        shapeGroup = controller.getShapeGroupTmp();
    }


    public boolean isOnToolbar(double x){
        return x <= controller.getView().getToolBar().getWidth();
    }

    EventHandler<MouseEvent> createSelectionRectangleOnClick = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            for(Shape item : controller.getView().getShapesInCanvas()){
                double x2 = controller.getView().getShapeXPositionInToolBar(item);
                double y2 = controller.getView().getShapeYPositionInToolBar(item);
                int i = controller.getView().getRoot().getChildren().indexOf(item);
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
                controller.getView().getRoot().getChildren().remove(selectionRectangle);
                for (ShapeInter s : controller.getShapesInCanvas()) {
                    Point rotationCenter = s.getRotationCenter();
                    if (rotationCenter.getX() >= mousePosStart.getX() && rotationCenter.getX() <= MousePosEnd.getX()
                            && rotationCenter.getY() >= mousePosStart.getY() && rotationCenter.getY() <= MousePosEnd.getY()) {
                        if(!shapeGroup.getChildren().contains(s)) {
                            shapeGroup.add(s);
                        }
                    }
                }
            }
            /*
            if (shapeGroup.getChildren().isEmpty()==false){
                controller.getShapeGroups().add(shapeGroup);
            }*/
            mouseEvent.consume();
        }
    };

    EventHandler<MouseEvent> CTRLAndMouseOnClickShapeGroup = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton() != MouseButton.PRIMARY){
                return;
            }
            if(mouseEvent.isControlDown()) {
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
                            ShapeInter shapetoAdd = controller.getShapesInCanvas().get(i);
                            if (!shapeGroup.getChildren().contains(shapetoAdd)) {
                                for(ShapeInter shapeGroup : controller.getShapeGroups()){
                                    if(shapeGroup.getChildren().contains(shapetoAdd)){
                                        return;
                                    }
                                }
                                shapeGroup.add(shapetoAdd);
                                System.out.println(shapetoAdd.getClass().toString() + " is added to ShapeGroup");
                            }
                        }
                    }
                }
            }
            mouseEvent.consume();
        }
    };

    public ShapeInter getShapeGroup() {
        return shapeGroup;
    }

    @Override
    public void launchEvent() {
        controller.getView().launch_CTRLAndMouseOnClickShapeGroup(CTRLAndMouseOnClickShapeGroup);
        controller.getView().launch_createSelectionRectangleOnClick(createSelectionRectangleOnClick);
        controller.getView().launch_rectangleSelectionReleased(releasedMouseAndCreateGroupShape);
        controller.getView().launch_selectionRectangleDraggedInCanvas(selectionRectangleDraggedInCanvas);
    }
}
