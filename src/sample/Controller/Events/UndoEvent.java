package sample.Controller.Events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Controller.IController;

public class UndoEvent implements Event {

    protected IController controller;

    public UndoEvent(IController controller){
        this.controller = controller;
    }

    /**
     * Undo a Command at the current position in the Command's list, then decrease the current position.
     */
    EventHandler<ActionEvent> undoEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if(controller.getCurrentPosInCommands() > 0) {
                int currentPos = controller.getCurrentPosInCommands();
                System.out.println("index : " + currentPos);
                controller.getCommands().get(currentPos).undo();
                controller.setCurrentPosInCommands(currentPos-1);
            }
        }
    };

    @Override
    public void launchEvent() {
        controller.getView().launch_undoEvent(undoEvent);
    }
}
