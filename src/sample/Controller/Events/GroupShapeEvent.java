package sample.Controller.Events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Controller.Command.AddGroupShapeCommand;
import sample.Controller.Command.Command;
import sample.Controller.Controller;
import sample.Controller.Command.DeGroupShapeCommand;
import sample.Model.ShapeInter;

public class GroupShapeEvent implements Event {
    private Controller controller;

    public GroupShapeEvent(Controller controller){
        this.controller = controller;
    }

   EventHandler<ActionEvent> groupShape = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ShapeInter shapeGroupTmp = controller.getShapeGroupTmp().clone();
            System.out.println("TMP: "+shapeGroupTmp.getChildren());
            Command addGroupShapeCommand = new AddGroupShapeCommand(shapeGroupTmp,controller);
            controller.addLastCommand(addGroupShapeCommand);
            controller.setCurrentPosInCommands(controller.getNbCommands());
            addGroupShapeCommand.execute();
        }
    };

    EventHandler<ActionEvent> DeGroupShape = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ShapeInter shapeGroupTmp = controller.getShapeGroupTmp();
            System.out.println("TMP: "+shapeGroupTmp.getChildren());
            System.out.println("GROUP: "+controller.getShapeGroups().toString());
            System.out.println("DeGROUP: "+controller.getShapeGroups().toString());

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
