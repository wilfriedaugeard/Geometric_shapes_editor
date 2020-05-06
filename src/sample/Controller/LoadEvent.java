package sample.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import sample.Model.ShapeInter;
import sample.View.IShapeDrawer;

import java.io.*;

public class LoadEvent implements Events{
    private final Controller controller;

    public LoadEvent(Controller controller) {
        this.controller = controller;
    }

    EventHandler<ActionEvent> loadButton = new EventHandler<>() {
        public void handle(ActionEvent actionEvent) {

            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            String filename = null;
            if (selectedFile != null) {

                filename = selectedFile.getName();
            }
            else {
                System.out.println("File selection cancelled.");
            }

            ObjectInput ois = null;
            try {
                final FileInputStream file = new FileInputStream(filename);
                ois = new ObjectInputStream(file);
                final Controller controller_load = (Controller) ois.readObject();

                controller.getShapesInToolBar().clear();
                controller.getShapesInToolBar().addAll(controller_load.getShapesInToolBar());

                for(Shape shapeView : controller.getView().getShapesInCanvas()){
                    controller.getView().getRoot().getChildren().remove(shapeView);
                }
                controller.getView().getShapesInCanvas().clear();

                controller.getShapesInCanvas().clear();
                controller.getShapesInCanvas().addAll(controller_load.getShapesInCanvas());

                controller.getShapeGroups().clear();
                controller.getShapeGroups().addAll(controller_load.getShapeGroups());

                controller.getShapeGroupTmp().remove(controller.getShapeGroupTmp());

                controller.setCurrentPosInCommands(0);
                
                for (ShapeInter shape : controller.getShapesInCanvas()) {
                    IShapeDrawer drawer = shape.createShapeDrawer(controller);
                    drawer.drawShape();
                }
                controller.updateEvents();


            } catch (final IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ois != null) {
                        ois.close();
                    }
                } catch (final IOException ex) {
                    ex.printStackTrace();
                }
            }
            actionEvent.consume();
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_loadEvent(loadButton);
    }
}
