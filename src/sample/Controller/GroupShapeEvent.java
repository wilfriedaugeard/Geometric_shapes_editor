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
            System.out.println("TMP: "+shapeGroupTmp.getChildren());
            controller.getShapeGroups().add(shapeGroupTmp);
            controller.getShapeGroupTmp().getChildren().clear();
        }
    };

    EventHandler<ActionEvent> DeGroupShape = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ShapeInter shapeGroupTmp = controller.getShapeGroupTmp();
            System.out.println("TMP: "+shapeGroupTmp.getChildren());
            System.out.println("GROUP: "+controller.getShapeGroups().toString());
            System.out.println("DeGROUP: "+controller.getShapeGroups().toString());

            for(ShapeInter shapeGroup : controller.getShapeGroups()){
                for(ShapeInter shape : shapeGroup.getChildren()){
                    for(ShapeInter shapetoDegroup : shapeGroupTmp.getChildren()){
                        if(shape.equals(shapetoDegroup)){
                            shapeGroup.remove(shape);
                        }
                    }
                }
            }

        }
    };


    @Override
    public void launchEvent() {
        controller.getView().launch_groupShape(groupShape);
        controller.getView().launch_deGroupShape(DeGroupShape);
    }
}
