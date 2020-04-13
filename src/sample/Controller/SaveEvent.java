package sample.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class SaveEvent implements Events {
    private final Controller controller;

    public SaveEvent(Controller controller) {
        this.controller = controller;
    }

    EventHandler<ActionEvent> saveButton = new EventHandler<>() {
        public void handle(ActionEvent actionEvent) {
            ObjectOutputStream oos = null;
            try {
                File file = new File("save.ser");
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
