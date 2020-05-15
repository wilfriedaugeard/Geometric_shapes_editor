package sample.Controller.Events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Controller.Controller;

public class UndoEvent implements Events {

    protected Controller controller;

    public UndoEvent(Controller controller){
        this.controller = controller;
    }

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
