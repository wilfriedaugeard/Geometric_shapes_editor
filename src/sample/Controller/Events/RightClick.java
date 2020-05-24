package sample.Controller.Events;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import sample.Controller.*;
import sample.Controller.Command.ColorShapeCommand;
import sample.Controller.Command.ICommand;
import sample.Controller.Command.ResizeCommand;
import sample.Controller.Command.RotateCommand;
import sample.Factory.ModelFactory.RGBFactory;
import sample.Model.IShapeInter;
import sample.View.TxtCapture;

import java.util.ArrayList;

/**
 * Right click action
 */
public class RightClick implements Event {
    private final IController controller;
    private Shape shapeInCanvas;
    private ColorPicker colorPicker;
    private final ContextMenu shapeMenu;
    private double tmp_rotate;
    private double size_saved;
    private double last_rotate;

    public RightClick(IController controller){
        this.controller = controller;
        this.colorPicker = (ColorPicker) controller.getView().getColorPicker();
        this.shapeMenu = (ContextMenu) controller.getView().getShapeMenu();
    }

    /**
     * Get shape infos on mouse pressed and show the menu.
     */
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

    /**
     * Event to get a color from colorPicker JavaFX.
     */
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

                        double red = colorPicker.getValue().getRed();
                        double green = colorPicker.getValue().getGreen();
                        double blue = colorPicker.getValue().getBlue();

                        int index = controller.getView().getShapesInCanvas().indexOf(value);
                        IShapeInter shapeSelected = controller.getShapesInCanvas().get(index);

                        ICommand colorShapeCommand = null;
                        for (IShapeInter shapeGroup : controller.getShapeGroups()) {
                            if (shapeGroup.getChildren().contains(shapeSelected)) {
                                colorShapeCommand = new ColorShapeCommand(RGBFactory.getRGB(red, green, blue), shapeGroup, controller);
                                controller.addLastCommand(colorShapeCommand);
                                controller.setCurrentPosInCommands(controller.getNbCommands());
                                colorShapeCommand.execute();
                                return;
                            }
                        }
                        colorShapeCommand = new ColorShapeCommand(RGBFactory.getRGB(red, green, blue), shapeSelected, controller);
                        controller.addLastCommand(colorShapeCommand);
                        controller.setCurrentPosInCommands(controller.getNbCommands());
                        colorShapeCommand.execute();
                    }
                }
            }
        }
    };

    /**
     * Check if a String contains only digits, including the particular case of '-' at the start.
     * @param s String to verify
     * @return true if the String contains only digits
     */
    private boolean containsOnlyDigits(String s){
        int len = s.length();
        int start = 0;
        if((s.charAt(0) == '-')){
            start = 1;
        }
        for (int i = start; i < len; i++) {
            if ((!Character.isDigit(s.charAt(i)))) {
                return false;
            }
        }
        return true;
    }


    /**
     * Resize a given shape with percent value
     * @param shape IShapeInter to be resized
     * @param value Value for resize
     */
    public void resizeShape(IShapeInter shape, double value){
        ICommand resizeCommand;
        System.out.println(shape.getWidth());
        for (IShapeInter shapeGroup : controller.getShapeGroups()) {
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
        System.out.println(shape.getWidth());
    }

    /**
     * Do the resize treatment only if the value is suitable for resize.
     * @param strValue String checked by containsOnlyDigits
     * @param shapeSelected IShapeInter to be resized if containsOnlyDigits returns true
     */
    public void resizeTreatment(String strValue, IShapeInter shapeSelected){
        if (containsOnlyDigits(strValue)) {
            double value = Double.parseDouble(strValue);
            resizeShape(shapeSelected,value);
            System.out.println(value);
        }
    }

    /**
     * Rotate a given shape.
     * @param shape IShapeInter shape to rotate
     * @param value Value supposed in degree for rotation
     */
    public void rotateShape(IShapeInter shape, double value){
        ICommand rotateCommand;
        for (IShapeInter shapeGroup : controller.getShapeGroups()) {
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

    /**
     * Do the rotate treatment only if the value is suitable for rotate.
     * @param strValue String checked by containsOnlyDigits
     * @param shape IShapeInter to rotate if containsOnlyDigits returns true
     */
    public void rotateTreatment(String strValue, IShapeInter shape){
        if(containsOnlyDigits(strValue)) {
            double value = Double.parseDouble(strValue);
            System.out.println(value);
            if(last_rotate != value){
                tmp_rotate += value;
                rotateShape(shape, value);
                last_rotate = value;
            }
        }
    }

    public void treatments(IShapeInter shape, TxtCapture dialog){
        if(!dialog.getResize().getText().isEmpty()){
            resizeTreatment(dialog.getResize().getText(), shape);
        }
        if(!dialog.getRotation().getText().isEmpty()){
            rotateTreatment(dialog.getRotation().getText(),shape);
        }
    }

    /**
     * Clear fileds of form
     * @param shapeSelected The shape to edit
     */
    public void clearField(IShapeInter shapeSelected){
        double value = (size_saved*100)/shapeSelected.getWidth();
        resizeShape(shapeSelected, value);
        if(tmp_rotate != 0){
            rotateShape(shapeSelected,-tmp_rotate);
            tmp_rotate = 0;

        }
        last_rotate = 0;
    }

    /**
     * Edit button event
     */
    EventHandler<ActionEvent> edit = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            int index = controller.getView().getShapesInCanvas().indexOf(shapeInCanvas);
            IShapeInter shapeSelected = controller.getShapesInCanvas().get(index);
            size_saved = shapeSelected.getWidth();
            tmp_rotate = 0;

            TxtCapture dialog = new TxtCapture();
            // BUTTON EVENTS
            dialog.getSubmit().setOnAction(e -> {
                treatments(shapeSelected, dialog);
                dialog.closeWindow();
            });

            dialog.getApply().setOnAction(e -> {
                clearField(shapeSelected);
                treatments(shapeSelected, dialog);
                System.out.println(shapeSelected.getRotation());

            });

            dialog.getClear().setOnAction(e->{
                dialog.getResize().clear();
                dialog.getRotation().clear();
                clearField(shapeSelected);
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
