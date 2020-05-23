package sample.Controller.Events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Controller.Command.AddGroupShapeCommand;
import sample.Controller.Command.ICommand;
import sample.Controller.IController;
import sample.Controller.Command.DeGroupShapeCommand;
import sample.Model.IShapeInter;

public class GroupShapeEvent implements Event {
    private IController controller;

    public GroupShapeEvent(IController controller){
        this.controller = controller;
    }

   EventHandler<ActionEvent> groupShape = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            IShapeInter shapeGroupTmp = controller.getShapeGroupTmp().clone();
            ICommand addGroupShapeCommand = new AddGroupShapeCommand(shapeGroupTmp,controller);
            controller.addLastCommand(addGroupShapeCommand);
            controller.setCurrentPosInCommands(controller.getNbCommands());
            addGroupShapeCommand.execute();
        }
    };

    EventHandler<ActionEvent> DeGroupShape = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            IShapeInter shapeGroupTmp = controller.getShapeGroupTmp();
            DeGroupShapeCommand deGroupShapeCommand = new DeGroupShapeCommand(controller, shapeGroupTmp);
            controller.addLastCommand(deGroupShapeCommand);
            controller.setCurrentPosInCommands(controller.getNbCommands());
            deGroupShapeCommand.execute();
        }
    };


    @Override
    public void launchEvent() {
        controller.getView().launch_groupShape(groupShape);
        controller.getView().launch_deGroupShape(DeGroupShape);
    }
}
