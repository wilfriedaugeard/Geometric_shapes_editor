package sample.Controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RightClick implements Events {
    private Controller controller;
    private Shape shapeInCanvas;


    public RightClick(Controller controller){
        this.controller = controller;
    }

    EventHandler<ContextMenuEvent> getShapeOnMousePressed = new EventHandler<ContextMenuEvent>() {
        public void handle(ContextMenuEvent event) {

            shapeInCanvas = (Shape) event.getSource();

            double x = controller.getView().getShapeXPositionInToolBar(shapeInCanvas);
            double y = controller.getView().getShapeYPositionInToolBar(shapeInCanvas);

            ArrayList<Shape> shapes = controller.getView().getShapesInCanvas();
            if(shapes.size() == controller.getShapesInCanvas().size()) {
                double shapeX, shapeY;
                for (Shape value : shapes) {
                    shapeX = controller.getView().getShapeXPositionInToolBar(value);
                    shapeY = controller.getView().getShapeYPositionInToolBar(value);
                    if (shapeX == x && shapeY == y) {
                        controller.getView().getColorPicker().setValue((Color) shapeInCanvas.getFill());
                        controller.getView().getShapeMenu().show(value, event.getScreenX(),event.getScreenY());
                    }
                }
            }
            event.consume();

        }
    };



    EventHandler<ActionEvent> colorPicker = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            double x = controller.getView().getShapeXPositionInToolBar(shapeInCanvas);
            double y = controller.getView().getShapeYPositionInToolBar(shapeInCanvas);

            ArrayList<Shape> shapes = controller.getView().getShapesInCanvas();
            if(shapes.size() == controller.getShapesInCanvas().size()) {
                double shapeX, shapeY;
                for (Shape value : shapes) {
                    shapeX = controller.getView().getShapeXPositionInToolBar(value);
                    shapeY = controller.getView().getShapeYPositionInToolBar(value);
                    if (shapeX == x && shapeY == y) {
                        ColorPicker colorPicker = controller.getView().getColorPicker();
                        shapeInCanvas.setFill(colorPicker.getValue());


                    }
                }
            }
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_rightClick(getShapeOnMousePressed);
        controller.getView().launch_colorPickerHandler(colorPicker);
    }
}
