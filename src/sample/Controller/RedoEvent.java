package sample.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class RedoEvent implements Events{

    protected Controller controller;

    public RedoEvent(Controller controller) {
        this.controller = controller;
    }

    EventHandler<ActionEvent> redoEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if(controller.getCurrentPosInCommands() < controller.getCommands().size()-1) {
                int currentPos = controller.getCurrentPosInCommands() + 1;
                controller.setCurrentPosInCommands(currentPos);
                controller.getCommands().get(currentPos).redo();
            }
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_redoEvent(redoEvent);
    }
}
