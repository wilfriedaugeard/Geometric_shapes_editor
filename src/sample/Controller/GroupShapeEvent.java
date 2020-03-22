package sample.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Model.ShapeInter;

public class GroupShapeEvent implements Events{
    private Controller controller;

    public GroupShapeEvent(Controller controller){
        this.controller = controller;
    }



   EventHandler<ActionEvent> groupShape = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ShapeInter shapeGroupTmp = controller.getShapeGroupTmp();
            System.out.println("TMP: "+shapeGroupTmp.getChildren());
            controller.getShapeGroups().add(shapeGroupTmp);
            controller.getShapeGroupTmp().remove(shapeGroupTmp);
            shapeGroupTmp.getChildren().clear();
        }
    };

    EventHandler<ActionEvent> DeGroupShape = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ShapeInter shapeGroupTmp = controller.getShapeGroupTmp();
            System.out.println("TMP: "+shapeGroupTmp.getChildren());
            System.out.println("GROUP: "+controller.getShapeGroups().toString());
            controller.getShapeGroups().remove(shapeGroupTmp);
            System.out.println("DeGROUP: "+controller.getShapeGroups().toString());

        }
    };


    @Override
    public void launchEvent() {
        controller.getView().launch_groupShape(groupShape);
        controller.getView().launch_deGroupShape(DeGroupShape);
    }
}
