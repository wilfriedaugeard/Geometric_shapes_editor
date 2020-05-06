package sample.Controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import sample.Model.RGB;
import sample.Model.ShapeInter;

import java.util.ArrayList;

public class RightClick implements Events {
    private final Controller controller;
    private Shape shapeInCanvas;
    private ColorPicker colorPicker;
    private final ContextMenu shapeMenu;


    public RightClick(Controller controller){
        this.controller = controller;
        this.colorPicker = (ColorPicker) controller.getView().getColorPicker();
        this.shapeMenu = (ContextMenu) controller.getView().getShapeMenu();
    }

    EventHandler<ContextMenuEvent> getShapeOnMousePressed = new EventHandler<>() {
        public void handle(ContextMenuEvent event) {

            shapeInCanvas = (Shape) event.getSource();

            double x = controller.getView().getShapeXPositionInToolBar(shapeInCanvas);
            double y = controller.getView().getShapeYPositionInToolBar(shapeInCanvas);

            ArrayList<Shape> shapes = controller.getView().getShapesInCanvas();
            if (shapes.size() == controller.getShapesInCanvas().size()) {
                double shapeX, shapeY;
                for (Shape value : shapes) {
                    shapeX = controller.getView().getShapeXPositionInToolBar(value);
                    shapeY = controller.getView().getShapeYPositionInToolBar(value);
                    if (shapeX == x && shapeY == y) {
                        colorPicker.setValue((Color) shapeInCanvas.getFill());
                        shapeMenu.show(value, event.getScreenX(), event.getScreenY());
                    }
                }
            }
            event.consume();

        }
    };

    EventHandler<ActionEvent> colorPickerEv = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {

            double x = controller.getView().getShapeXPositionInToolBar(shapeInCanvas);
            double y = controller.getView().getShapeYPositionInToolBar(shapeInCanvas);

            ArrayList<Shape> shapes = controller.getView().getShapesInCanvas();
            if (shapes.size() == controller.getShapesInCanvas().size()) {
                double shapeX, shapeY;
                for (Shape value : shapes) {
                    shapeX = controller.getView().getShapeXPositionInToolBar(value);
                    shapeY = controller.getView().getShapeYPositionInToolBar(value);
                    if (shapeX == x && shapeY == y) {
                        colorPicker = (ColorPicker) controller.getView().getColorPicker();
                        //shapeInCanvas.setFill(colorPicker.getValue());

                        double red = colorPicker.getValue().getRed();
                        double green = colorPicker.getValue().getGreen();
                        double blue = colorPicker.getValue().getBlue();

                        int index = controller.getView().getShapesInCanvas().indexOf(value);
                        ShapeInter shapeSelected = controller.getShapesInCanvas().get(index);

                        Command colorShapeCommand = null;
                        for (ShapeInter shapeGroup : controller.getShapeGroups()) {
                            System.out.println("SHAPEGROUP : " + shapeGroup.getChildren().size());
                            System.out.println("SHAPEGROUP : " + shapeGroup);
                            if (shapeGroup.getChildren().contains(shapeSelected)) {
                                colorShapeCommand = new ColorShapeCommand(new RGB(red, green, blue), shapeGroup, controller);
                                controller.addLastCommand(colorShapeCommand);
                                controller.setCurrentPosInCommands(controller.getNbCommands());
                                colorShapeCommand.execute();
                                return;
                            }
                        }
                        colorShapeCommand = new ColorShapeCommand(new RGB(red, green, blue), shapeSelected, controller);
                        controller.addLastCommand(colorShapeCommand);
                        controller.setCurrentPosInCommands(controller.getNbCommands());
                        colorShapeCommand.execute();
                    }
                }
            }
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_rightClick(getShapeOnMousePressed);
        controller.getView().launch_colorPickerHandler(colorPickerEv);
    }
}
