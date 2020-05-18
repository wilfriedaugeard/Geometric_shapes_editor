package sample.Controller.Events;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import sample.Controller.*;
import sample.Controller.Command.ColorShapeCommand;
import sample.Controller.Command.Command;
import sample.Controller.Command.ResizeCommand;
import sample.Controller.Command.RotateCommand;
import sample.Model.RGB;
import sample.Model.ShapeInter;

import java.util.ArrayList;

public class RightClick implements Event {
    private final Controller controller;
    private Shape shapeInCanvas;
    private ColorPicker colorPicker;
    private final ContextMenu shapeMenu;
    private double tmp_rotate;
    private double rotate_saved;


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

    private boolean containsOnlyDigits(String s){
        int len = s.length();
        for (int i = 0; i < len-1; i++) {
            if ((!Character.isDigit(s.charAt(i)))) {
                return false;
            }
        }
        return true;
    }


    // Resize a shape
    public void resizeShape(ShapeInter shape, double value){
        Command resizeCommand;
        for (ShapeInter shapeGroup : controller.getShapeGroups()) {
            if (shapeGroup.getChildren().contains(shape)) {
                resizeCommand = new ResizeCommand(controller, shapeGroup, value, true);
                controller.addLastCommand(resizeCommand);
                controller.setCurrentPosInCommands(controller.getNbCommands());
                resizeCommand.execute();
                return;
            }
        }
        resizeCommand = new ResizeCommand(controller, shape, value, false);
        controller.addLastCommand(resizeCommand);
        controller.setCurrentPosInCommands(controller.getNbCommands());
        resizeCommand.execute();
    }

    // Verify string before resize a shape
    public void resizeTreatment(String strValue, ShapeInter shapeSelected){
        if (containsOnlyDigits(strValue)) {
            double value = Double.parseDouble(strValue);
            resizeShape(shapeSelected,value);
        }
    }

    // Rotate a shape
    public void rotateShape(ShapeInter shape, double value){
        Command rotateCommand;
        for (ShapeInter shapeGroup : controller.getShapeGroups()) {
            if (shapeGroup.getChildren().contains(shape)) {
                rotateCommand = new RotateCommand(controller, shapeGroup, value, true);
                controller.addLastCommand(rotateCommand);
                controller.setCurrentPosInCommands(controller.getNbCommands());
                rotateCommand.execute();
                return;
            }
        }
        rotateCommand = new RotateCommand(controller, shape, value, false);
        controller.addLastCommand(rotateCommand);
        controller.setCurrentPosInCommands(controller.getNbCommands());
        rotateCommand.execute();
    }

    // Verify string before rotate a shape
    public void rotateTreatment(String strValue, ShapeInter shape){
        if(containsOnlyDigits(strValue)) {
            double value = Double.parseDouble(strValue);
            tmp_rotate += value;
            rotateShape(shape, value);
        }
    }


    EventHandler<ActionEvent> edit = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            int index = controller.getView().getShapesInCanvas().indexOf(shapeInCanvas);
            ShapeInter shapeSelected = controller.getShapesInCanvas().get(index);
            double size_saved = shapeSelected.getWidth();

            tmp_rotate = 0;
            rotate_saved = 0;

            TxtCapture dialog = new TxtCapture(controller,"SHAPE EDITOR");

            // BUTTON EVENTS
            dialog.getSubmit().setOnAction(e -> {
                if(!dialog.getResize().getText().isEmpty())
                    resizeTreatment(dialog.getResize().getText(), shapeSelected);
                if(!dialog.getRotation().getText().isEmpty()){
                    rotateTreatment(dialog.getRotation().getText(),shapeSelected);
                }
                dialog.closeWindow();
            });

            dialog.getApply().setOnAction(e -> {
                if(!dialog.getResize().getText().isEmpty())
                    resizeTreatment(dialog.getResize().getText(), shapeSelected);
                if(!dialog.getRotation().getText().isEmpty()){
                    rotateTreatment(dialog.getRotation().getText(),shapeSelected);
                }
            });

            dialog.getClear().setOnAction(e->{
                dialog.getResize().clear();
                dialog.getRotation().clear();

                double value = (size_saved*100)/shapeSelected.getWidth();
                resizeShape(shapeSelected, value);
                System.out.println(shapeSelected.getRotation());
                if(tmp_rotate != 0){
                    rotateShape(shapeSelected,-tmp_rotate);
                    tmp_rotate = 0;
                    rotate_saved = 0;
                }
            });


        }
    };



    @Override
    public void launchEvent() {
        controller.getView().launch_rightClick(getShapeOnMousePressed);
        controller.getView().launch_colorPickerHandler(colorPickerEv);
        controller.getView().launch_editShape(edit);
    }
}
