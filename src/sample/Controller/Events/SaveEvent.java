package sample.Controller.Events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import sample.Controller.IController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class SaveEvent implements Event {
    private final IController controller;

    public SaveEvent(IController controller) {
        this.controller = controller;
    }

    /**
     * Create a file .ser
     */
    EventHandler<ActionEvent> saveButton = new EventHandler<>() {
        public void handle(ActionEvent actionEvent) {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("save.ser");
            File savedFile = fileChooser.showSaveDialog(null);
            String filename = null;
            if (savedFile != null) {
                filename = savedFile.getName();
            }


            ObjectOutputStream oos = null;
            try {
                File file = new File(filename);
                oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(controller);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (oos != null) {
                        oos.flush();
                        oos.close();
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
        controller.getView().launch_saveEvent(saveButton);
    }
}
