package sample.Controller;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Model.Point;
import sample.Model.ShapeInter;
import sample.View.IShapeDrawer;

public class CreateShapeEvent implements Events {
    protected Controller controller;

    public CreateShapeEvent(Controller controller) {
        this.controller = controller;
    }
    EventHandler<MouseEvent> createShapeInToolBarOnClick = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton() != MouseButton.PRIMARY){
                mouseEvent.consume();
                return;
            }
            Shape shape = (Shape) mouseEvent.getSource();
            double x = controller.getView().getShapeXPositionInToolBar(shape);
            double y = controller.getView().getShapeYPositionInToolBar(shape);

            /* Set the model position */
            ArrayList<Shape> shapes = controller.getView().getShapesInToolBar();
            if(shapes.size() == controller.getShapesInToolBar().size()) {
                double shapeX, shapeY;
                for(int i = 0 ; i < shapes.size(); i++) {
                    shapeX = controller.getView().getShapeXPositionInToolBar(shapes.get(i));
                    shapeY = controller.getView().getShapeYPositionInToolBar(shapes.get(i));

                    if(shapeX == x && shapeY == y) {
                        controller.getShapesInToolBar().get(i).setPos(new Point(x, y));
                    }
                }
            }

            for(ShapeInter model : controller.getShapesInToolBar()) {
                if(model.getPos().getX() == x && model.getPos().getY() == y) {
                    ShapeInter copy = model.clone();
                    IShapeDrawer drawer = copy.createShapeDrawer(controller);
                    drawer.drawShape();
                    controller.getShapesInCanvas().add(copy);

                }
            }

            controller.updateEvents();
            mouseEvent.consume();
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_createShapeInToolBarOnClick(createShapeInToolBarOnClick);
    }
}
