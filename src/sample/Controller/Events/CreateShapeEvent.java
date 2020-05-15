package sample.Controller.Events;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Controller.Controller;
import sample.Factory.PointFactory;
import sample.Model.Point;
import sample.Model.ShapeInter;
import sample.View.IShapeDrawer;

public class CreateShapeEvent implements Events{

    protected Controller controller;

    public CreateShapeEvent(Controller controller) {
        this.controller = controller;
    }

    EventHandler<MouseEvent> createShapeInToolBarOnClick = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                mouseEvent.consume();
                return;
            }
            Shape shape = (Shape) mouseEvent.getSource();
            int shapeIndex = controller.getView().getShapesInToolBar().indexOf(shape);
            ShapeInter shapeModel = controller.getShapesInToolBar().get(shapeIndex);

            double x = controller.getView().getShapeXPositionInToolBar(shape);
            double y = controller.getView().getShapeYPositionInToolBar(shape);

            ShapeInter copy = shapeModel.clone();
            copy.setPos(PointFactory.getPoint(x,y));
            IShapeDrawer drawer = copy.createShapeDrawer(controller);
            drawer.drawShape();
            controller.getShapesInCanvas().add(copy);

            controller.updateEvents();
            mouseEvent.consume();
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_createShapeInToolBarOnClick(createShapeInToolBarOnClick);
    }
}
