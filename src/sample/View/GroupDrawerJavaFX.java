package sample.View;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GroupDrawerJavaFX extends ShapeDrawerJavaFX {
    private double x, y, width, height;
    private ArrayList<IShapeDrawer> shapeDrawerList;

    public GroupDrawerJavaFX(IView view, double x, double y, double width, double height, ArrayList<IShapeDrawer> shapeDrawerList) {
        super(view, Color.rgb(0,0,0));
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.shapeDrawerList = shapeDrawerList;
    }

    @Override
    public void drawShape() {
        for (IShapeDrawer shape : shapeDrawerList){
            shape.drawShape();
        }
    }

    @Override
    public void drawShapeInToolBar(int itemPos, int shapePos) {
        for (IShapeDrawer shape : shapeDrawerList){
            shape.drawShapeInToolBar(itemPos, shapePos);
            itemPos++;
            shapePos++;
        }
    }
}
