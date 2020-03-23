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
            ShapeInter shapeGroupTmp = controller.getShapeGroupTmp().clone();
            System.out.println("TMP: "+shapeGroupTmp);
            System.out.println("TMP: "+shapeGroupTmp.getChildren());
            System.out.println("TMP: "+shapeGroupTmp.getChildren().size());
            controller.getShapeGroups().add(shapeGroupTmp);
            System.out.println("TMP: "+controller.getShapeGroupTmp());
            System.out.println("TMP: "+controller.getShapeGroupTmp().getChildren());
            System.out.println("TMP: "+controller.getShapeGroupTmp().getChildren().size());
            controller.getShapeGroupTmp().getChildren().clear();
            System.out.println("TMP: "+controller.getShapeGroupTmp().getChildren().size());
            System.out.println("TMP: "+shapeGroupTmp.getChildren().size());
        }
    };

    EventHandler<ActionEvent> DeGroupShape = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ShapeInter shapeGroupTmp = controller.getShapeGroupTmp();
            System.out.println("TMP: "+shapeGroupTmp.getChildren());
            System.out.println("GROUP: "+controller.getShapeGroups().toString());
            System.out.println("DeGROUP: "+controller.getShapeGroups().toString());
        }
    };


    @Override
    public void launchEvent() {
        controller.getView().launch_groupShape(groupShape);
        controller.getView().launch_deGroupShape(DeGroupShape);
    }
}
