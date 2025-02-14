package sample.Controller.Events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Controller.IController;

/**
 * Redo action
 */
public class RedoEvent implements Event {

    protected IController controller;

    public RedoEvent(IController controller) {
        this.controller = controller;
    }

    /**
     * Redo a Command at the next position after current position in the Command's list, then increase the current position.
     */
    EventHandler<ActionEvent> redoEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if(controller.getCurrentPosInCommands() < controller.getNbCommands()) {
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
