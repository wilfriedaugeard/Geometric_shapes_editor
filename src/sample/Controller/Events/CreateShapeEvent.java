package sample.Controller.Events;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Controller.Controller;
import sample.Factory.PointFactory;
import sample.Model.Point;
import sample.Model.ShapeInter;
import sample.View.IShapeDrawer;

public class CreateShapeEvent implements Event {

    protected Controller controller;

    public CreateShapeEvent(Controller controller) {
        this.controller = controller;
    }

    EventHandler<MouseEvent> createShapeInToolBarOnClick = new EventHandler<>() {
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                mouseEvent.consume();
                return;
            }
            Shape shape = (Shape) mouseEvent.getSource();
            int shapeIndex = controller.getView().getShapesInToolBar().indexOf(shape);
            ShapeInter shapeModel = controller.getShapesInToolBar().get(shapeIndex);
            for (ShapeInter group : controller.getShapeGroupsInToolBar()) {
                if (group.getChildren().contains(shapeModel)) {
                    double x = controller.getView().getShapeXPositionInToolBar(shape);
                    double y = controller.getView().getShapeYPositionInToolBar(shape);
                    ShapeInter copy = group.clone();
                    copy.setPos(PointFactory.getPoint(x, y));
                    double delta = 0;
                    for (ShapeInter shapeChild : copy.getChildren()) {
                        double x1 = shapeChild.getPos().getX();
                        double y1 = shapeChild.getPos().getY();
                        if (x1 < 0) {
                            delta = Math.min(delta, x1);
                        }
                    }
                    for (ShapeInter shapeInter : copy.getChildren()) {
                        Point p = PointFactory.getPoint((shapeInter.getPos().getX() + delta) * shapeInter.getCoeff(), shapeInter.getPos().getY() * shapeInter.getCoeff());
                        shapeInter.setPos(p);
                    }
                    for (ShapeInter shapeInter : copy.getChildren()) {
                        ArrayList<Double> vector = shapeInter.getVector();
                        for (int i = 0; i < vector.size(); i++) {
                            vector.set(i, vector.get(i) / group.getCoeff());
                        }
                        shapeInter.setVector(vector);
                        controller.getShapesInCanvas().add(shapeInter);
                    }
                    IShapeDrawer drawer = copy.createShapeDrawer(controller);
                    drawer.drawShape();
                    controller.getShapeGroups().add(copy);
                    controller.updateEvents();
                    mouseEvent.consume();
                    return;
                }
            }

            double x = controller.getView().getShapeXPositionInToolBar(shape);
            double y = controller.getView().getShapeYPositionInToolBar(shape);
            ShapeInter copy = shapeModel.clone();
            ArrayList<Double> vector = copy.getVector();
            for (int i = 0; i < vector.size(); i++) {
                vector.set(i, vector.get(i) / copy.getCoeff());
            }
            copy.setVector(vector);
            copy.setPos(PointFactory.getPoint(x, y));
            copy.setRGB(shapeModel.getRGB());
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
