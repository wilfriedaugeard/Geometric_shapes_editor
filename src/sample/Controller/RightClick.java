package sample.Controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RightClick implements Events {
    private Controller controller;


    public RightClick(Controller controller){
        this.controller = controller;
    }

    EventHandler<MouseEvent> getShapeOnMousePressed = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton() != MouseButton.SECONDARY){
                return;
            }
            Shape shape = (Shape) mouseEvent.getSource();

            double x = controller.getView().getShapeXPositionInToolBar(shape);
            double y = controller.getView().getShapeYPositionInToolBar(shape);

            ArrayList<Shape> shapes = controller.getView().getShapesInCanvas();
            if(shapes.size() == controller.getShapesInCanvas().size()) {
                double shapeX, shapeY;
                for (Shape value : shapes) {
                    shapeX = controller.getView().getShapeXPositionInToolBar(value);
                    shapeY = controller.getView().getShapeYPositionInToolBar(value);
                    if (shapeX == x && shapeY == y) {
                        coloPicker(value);
                    }
                }
            }
            mouseEvent.consume();

        }
    };


    public void coloPicker(final Shape shape){
        ColorPicker colorPicker = controller.getView().getColorPicker();
        colorPicker.setValue((Color) shape.getFill());
        colorPicker.setOnAction(event -> shape.setFill(colorPicker.getValue()));
        Label l = new Label("Choose a color");
        VBox vBox = new VBox(l,colorPicker);
        Scene scene = new Scene(vBox, 150,50);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }


    @Override
    public void launchEvent() {
        controller.getView().launch_rightClick(getShapeOnMousePressed);
    }
}
