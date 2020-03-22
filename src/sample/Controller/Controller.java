package sample.Controller;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import sample.Model.*;
import sample.View.ShapeDrawer;
import sample.View.View;

import java.util.ArrayList;
import java.util.LinkedList;

public class Controller {

    /* View */
    private View view;

    /* Model */
    private ArrayList<ShapeInter> shapesInToolBar;
    private ArrayList<ShapeInter> shapesInCanvas;
    private ArrayList<Events> events;
    private ArrayList<ShapeInter> shapeGroups;
    private ShapeInter shapeGroupTmp;

    /* Pattern Command */
    private LinkedList<Command> commands;

    public Controller(View view) {
        this.view = view;
        shapesInCanvas = new ArrayList<>();
        shapesInToolBar = new ArrayList<>();
        shapeGroups = new ArrayList<>();
        events = new ArrayList<>();
        commands = new LinkedList<>();
        shapeGroupTmp = new ShapeGroup();
    }

    public void initializeView() {
        view.addMenuBar();
        view.addCanvas();
        view.addShapeMenu();

        shapesInToolBar.add(new RectangleNoJavaFX(50, 25, new Point(0,0), new RGB(1, 0, 0)));
        shapesInToolBar.add(new RectangleNoJavaFX(50, 25, new Point(0,0), new RGB(0, 1, 0)));
        shapesInToolBar.add(new Polygon(5, 25, new Point(20,0), new RGB(0, 0, 1)));
        shapesInToolBar.add(new Polygon(7, 35, new Point(20,0), new RGB(1, 1, 0)));

        for(ShapeInter shape : shapesInToolBar) {
           ShapeDrawer drawer = shape.createShapeDrawer(this);
           drawer.drawShapeInToolBar();
        }

        view.getToolBar().getItems().add(view.getTrash());

    }

    public void updateView() {
        for(ShapeInter shapeModel : shapesInCanvas) {
            for(Shape shapeView : view.getShapesInCanvas()) {
                if(shapeModel.getPos().getX() == view.getShapeXPositionInToolBar(shapeView) && shapeModel.getPos().getY() == view.getShapeYPositionInToolBar(shapeView)) {
                    double red = shapeModel.getRGB().getR();
                    double blue = shapeModel.getRGB().getB();
                    double green = shapeModel.getRGB().getG();
                    shapeView.setFill(Color.color(red,green,blue));
                }
            }
        }
    }


    public void initEvents() {

        events.add(new RightClick(this));
        events.add(new DragAndDropEvent(this));
        events.add(new CreateShapeEvent(this));
        events.add(new SelectionShapeEvent(this));
        events.add(new GroupShapeEvent(this));

        for(Events event : events) {
            event.launchEvent();
        }
    }

    public ShapeInter getShapeGroupTmp(){
        return shapeGroupTmp;
    }


    public ArrayList<Events> getEvents() {
        return events;
    }

    public Scene getScene() {
        return view.getScene();
    }

    public View getView() {
        return this.view;
    }

    public ArrayList<ShapeInter> getShapesInToolBar() {
        return shapesInToolBar;
    }

    public ArrayList<ShapeInter> getShapesInCanvas() {
        return shapesInCanvas;
    }

    public ArrayList<ShapeInter> getShapeGroups() {
        return shapeGroups;
    }

    public LinkedList<Command> getCommands() {
        return commands;
    }
}
