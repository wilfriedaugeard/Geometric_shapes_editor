package sample.Controller.Command;

import sample.Controller.Controller;
import sample.Model.RGB;
import sample.Model.ShapeGroup;
import sample.Model.ShapeInter;

import java.util.ArrayList;

public class ColorShapeCommand implements Command{
        private RGB color;
        private RGB oldColor;
        private ArrayList<RGB> oldColors;
        private ShapeInter shape;
        private Controller controller;

        public ColorShapeCommand(RGB color, ShapeInter shape, Controller controller) {
            this.color = new RGB( (int)(color.getR()*255) ,(int) color.getG()*255,(int) color.getB()*255);
            this.shape = shape;
            this.controller = controller;
            this.oldColors = new ArrayList<>();
        }

        @Override
        public void execute() {
            if(shape instanceof ShapeGroup && oldColors.isEmpty()){
                for(ShapeInter shapeChild : shape.getChildren()){
                    oldColors.add(shapeChild.getRGB());
                }
            }else {
                this.oldColor = shape.getRGB();
            }
            shape.setRGB(color);
            controller.updateViewColor();
        }

        @Override
        public void undo() {
            if(shape instanceof ShapeGroup){
                for(int i = 0; i < shape.getChildren().size(); i++){
                    RGB oldColor = oldColors.get(i);
                    shape.getChild(i).setRGB(oldColor);
                }
            }else {
                shape.setRGB(oldColor);
            }
            controller.updateViewColor();
        }

        @Override
        public void redo() {
            execute();
        }

}
