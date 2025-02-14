package sample.Controller.Events;

import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sample.Controller.IController;
import sample.Model.*;

import java.util.ArrayList;

/**
 * Shape selection
 */
public class SelectionShapeEvent implements Event {
    protected IController controller;
    private Point mousePosStart;
    private Rectangle selectionRectangle;
    private IShapeInter shapeGroup;

    public SelectionShapeEvent(IController controller) {
        this.controller = controller;
        shapeGroup = controller.getShapeGroupTmp();
    }

    /**
     * Check if a x point is on the toolbar
     * @param x The x position
     * @return True if is on toolbar
     */
    public boolean isOnToolbar(double x){
        ToolBar toolBar = (ToolBar) controller.getView().getToolBar();
        return x <= toolBar.getWidth();
    }

    /**
     * Mouse pressed, begin to the creation of the selection rectangle
     */
    EventHandler<MouseEvent> createSelectionRectangleOnClick = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            BorderPane bp = (BorderPane) controller.getView().getRoot();
            for(Shape item : controller.getView().getShapesInCanvas()){
                double x2 = controller.getView().getShapeXPositionInToolBar(item);
                double y2 = controller.getView().getShapeYPositionInToolBar(item);
                int i = bp.getChildren().indexOf(item);
                if(item.contains(mouseEvent.getX(),mouseEvent.getY())){
                    return;
                }
            }
            if(mouseEvent.getSource() == controller.getView().getRoot() && !isOnToolbar(mouseEvent.getX())) {
                mousePosStart = new Point(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                selectionRectangle = new Rectangle(mousePosStart.getX(), mousePosStart.getY(), 0, 0);
                bp.getChildren().add(selectionRectangle);

                /* CSS */
                selectionRectangle.setFill(Color.rgb(133,213,243));
                selectionRectangle.setStroke(Color.rgb(0,194,225));
                selectionRectangle.setStrokeDashOffset(10);
                selectionRectangle.getStrokeDashArray().addAll(3.0,7.0,3.0,7.0);
                selectionRectangle.setOpacity(0.3);
            }
        }
    };

    /**
     * Drag of the selection rectangle
     */
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

    /**
     * Mouse released, add all shapes in the selection rectangle to a tempory group
     */
    EventHandler<MouseEvent> releasedMouseAndCreateGroupShape = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(selectionRectangle!=null) {
                Point MousePosEnd = new Point(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                BorderPane bp = (BorderPane) controller.getView().getRoot();
                bp.getChildren().remove(selectionRectangle);
                selectionRectangle = null;
                for (IShapeInter s : controller.getShapesInCanvas()) {
                    Point rotationCenter = s.getRotationCenter();
                    if (rotationCenter.getX() >= mousePosStart.getX() && rotationCenter.getX() <= MousePosEnd.getX()
                            && rotationCenter.getY() >= mousePosStart.getY() && rotationCenter.getY() <= MousePosEnd.getY()) {
                            if (!shapeGroup.getChildren().contains(s)) {
                                shapeGroup.add(s);
                        }
                    }
                }
            }
            mouseEvent.consume();
        }
    };

    /**
     *  Control + click event. Add the selectioned shape to a tempory group
     */
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
                            IShapeInter shapetoAdd = controller.getShapesInCanvas().get(i);
                            if (!shapeGroup.getChildren().contains(shapetoAdd)) {
                                for(IShapeInter shapeGroup : controller.getShapeGroups()){
                                    if(shapeGroup.getChildren().contains(shapetoAdd)){
                                        return;
                                    }
                                }
                                shapeGroup.add(shapetoAdd);
                            }
                        }
                    }
                }
            }
            mouseEvent.consume();
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_CTRLAndMouseOnClickShapeGroup(CTRLAndMouseOnClickShapeGroup);
        controller.getView().launch_createSelectionRectangleOnClick(createSelectionRectangleOnClick);
        controller.getView().launch_rectangleSelectionReleased(releasedMouseAndCreateGroupShape);
        controller.getView().launch_selectionRectangleDraggedInCanvas(selectionRectangleDraggedInCanvas);
    }
}
