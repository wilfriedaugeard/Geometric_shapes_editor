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
    private Point MousePos;
    protected Controller controller;

    public DeleteShape(Controller controller) {
        this.controller = controller;
    }


    EventHandler<MouseEvent> overTrash = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent mouseEvent) {
            Shape shape = (Shape) mouseEvent.getSource();
          
            System.out.println("resleased");
            double x = controller.getView().getShapeXPositionInToolBar(shape);
            double y = controller.getView().getShapeYPositionInToolBar(shape);

            for(ShapeInter model : controller.getShapesInCanvas()) {
                if(model.getPos().getX() == x && model.getPos().getY() == y){
                    controller.getView().onTrashInfo(new Point(x,y));
                    if(controller.getView().isOnTrash(new Point(x,y))){
                        System.out.println("ON TRASH");
                        if(!controller.getView().getShapesInCanvas().remove(shape)){
                            System.out.println("Shape in view.getShapesCanvas not find");
                        }
                        if(!controller.getShapesInCanvas().remove(model)){
                            System.out.println("model in getShapesInCanvas not find");
                        }
                        controller.getView().getRoot().getChildren().remove(shape);
                        return;
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
