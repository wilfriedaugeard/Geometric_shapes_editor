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

public class ColorPickerEvent implements Events {
    private Controller controller;


    public ColorPickerEvent(Controller controller){
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
                for(int i = 0 ; i < shapes.size(); i++) {
                    shapeX = controller.getView().getShapeXPositionInToolBar(shapes.get(i));
                    shapeY = controller.getView().getShapeYPositionInToolBar(shapes.get(i));
                    if(shapeX == x && shapeY == y) {
                        System.out.println("COLOR PICKER !");
                        ColorPicker colorPicker = controller.getView().getColorPicker();
                        colorPicker.setValue((Color) shapes.get(i).getFill());

                        final Shape shapeTmp = shapes.get(i);
                        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                shapeTmp.setFill(colorPicker.getValue());
                            }
                        });
                        Label l = new Label("Choose a color");
                        VBox vBox = new VBox(l,colorPicker);
                        Scene scene = new Scene(vBox, 150,50);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();

                    }
                }
            }

        }
    };


    @Override
    public void launchEvent() {
        controller.getView().launch_colorPickerOnRightClick(getShapeOnMousePressed);
    }
}
