package sample.Controller;

import sample.Model.RGB;
import sample.Model.ShapeInter;

public class ColorShapeCommand implements Command{
        private RGB color;
        private ShapeInter shape;
        private Controller controller;

        public ColorShapeCommand(RGB color, ShapeInter shape, Controller controller) {
            this.color = color;
            this.shape = shape;
            this.controller = controller;
        }

        @Override
        public void execute() {
            shape.setRGB(color);
            controller.updateView();
        }

}
