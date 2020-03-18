package sample.Controller;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import sample.Model.Point;
import sample.Model.ShapeInter;

import java.util.ArrayList;

public class DeleteShape implements Events {

    private double shapeX, shapeY;
    private Point2D MousePos;
    protected Controller controller;

    public DeleteShape(Controller controller) {
        this.controller = controller;
    }


    EventHandler<MouseEvent> overTrash = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent mouseEvent) {
            Shape shape = (Shape) mouseEvent.getSource();

            double x = controller.getView().getShapeXPositionInToolBar(shape);
            double y = controller.getView().getShapeYPositionInToolBar(shape);

            Point p = new Point(x,y);
            for(ShapeInter model : controller.getShapesInCanvas()) {
                /*System.out.println("Model x: "+model.getPos().getX());
                System.out.println("Layout x: "+shape.getTranslateX());*/
                if(model.getPos().getX() == shape.getTranslateX() && model.getPos().getY() == shape.getTranslateY()) { // Ne marche pas
                    if(controller.getView().isOnTrash(p)){
                        controller.getView().getShapesInCanvas().remove(model);
                        controller.getShapesInCanvas().remove(model);
                    }
                }
            }

        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_overTrash(overTrash);
    }
}
