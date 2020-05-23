package sample.Controller.Command;

import sample.Controller.IController;
import sample.Factory.ModelFactory.RGBFactory;
import sample.Model.RGB;
import sample.Model.ShapeGroup;
import sample.Model.IShapeInter;

import java.util.ArrayList;

public class ColorShapeCommand implements ICommand {
        private RGB color;
        private RGB oldColor;
        private ArrayList<RGB> oldColors;
        private IShapeInter shape;
        private IController controller;

        public ColorShapeCommand(RGB color, IShapeInter shape, IController controller) {
            this.color = RGBFactory.getRGB( (int)(color.getR()*255) ,(int) (color.getG()*255),(int) (color.getB()*255));
            this.shape = shape;
            this.controller = controller;
            this.oldColors = new ArrayList<>();
        }

        @Override
        public void execute() {
            if(shape instanceof ShapeGroup && oldColors.isEmpty()){
                for(IShapeInter shapeChild : shape.getChildren()){
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
