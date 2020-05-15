package sample.Controller;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import sample.Controller.Command.Command;
import sample.Controller.Events.*;
import sample.Factory.PointFactory;
import sample.Model.*;
import sample.View.IShapeDrawer;
import sample.View.ViewJavaFXAdaptee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class ControllerJavaFX implements Serializable {
    private static final long serialVersionUID = 1L;
    /* View */
    private transient ViewJavaFXAdaptee view;

    /* Model */
    private ArrayList<ShapeInter> shapesInToolBar;
    private ArrayList<ShapeInter> shapesInCanvas;
    private transient ArrayList<Events> events;
    private ArrayList<ShapeInter> shapeGroups;
    private ShapeInter shapeGroupTmp;

    /* Pattern Command */
    private transient int currentPosInCommands;
    private transient LinkedList<Command> commands;

    public ControllerJavaFX(ViewJavaFXAdaptee view) {
        this.view = view;
        shapesInCanvas = new ArrayList<>();
        shapesInToolBar = new ArrayList<>();
        shapeGroups = new ArrayList<>();
        events = new ArrayList<>();
        commands = new LinkedList<>();
        shapeGroupTmp = new ShapeGroup();
        currentPosInCommands = 0;
    }

    public void initializeView(Controller controller) {
        view.addMenuBar();
        view.addCanvas();
        view.addShapeMenu();

        shapesInToolBar.add(new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(1, 0, 0)));
        shapesInToolBar.add(new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(0, 1, 0)));
        shapesInToolBar.add(new PolygonJavaFX(5, 25, new Point(0, 20), new RGB(0, 0, 1)));
        shapesInToolBar.add(new PolygonJavaFX(7, 35, new Point(0, 20), new RGB(1, 1, 0)));

        for (ShapeInter shape : shapesInToolBar) {
            IShapeDrawer drawer = shape.createShapeDrawer(controller);
            drawer.drawShapeInToolBar();
        }

        view.addTrash();
    }

    public void updateViewColor() {
        for (ShapeInter shapeModel : shapesInCanvas) {
            for (Shape shapeView : view.getShapesInCanvas()) {
                if (shapeModel.getPos().getX() == view.getShapeXPositionInToolBar(shapeView) && shapeModel.getPos().getY() == view.getShapeYPositionInToolBar(shapeView)) {
                    double red = shapeModel.getRGB().getR();
                    double blue = shapeModel.getRGB().getB();
                    double green = shapeModel.getRGB().getG();
                    shapeView.setFill(Color.color(red, green, blue));
                }
            }
        }
    }

    public void updateViewTranslate(ShapeInter shape, double dragX, double dragY, boolean isShapeGroup) {
        if (isShapeGroup == true) {
            for (ShapeInter child : shape.getChildren()) {
                int shapeIndex = shapesInCanvas.indexOf(child);
                Shape shapeView = view.getShapesInCanvas().get(shapeIndex);
                shapeView.setTranslateX(shapeView.getTranslateX() + dragX);
                shapeView.setTranslateY(shapeView.getTranslateY() + dragY);
            }
        } else {
            int shapeIndex = shapesInCanvas.indexOf(shape);
            Shape shapeView = view.getShapesInCanvas().get(shapeIndex);
            shapeView.setTranslateX(shapeView.getTranslateX() + dragX);
            shapeView.setTranslateY(shapeView.getTranslateY() + dragY);
        }
    }

    /**
     * Remove a shape in view and controller
     */
    public void removeShape(ShapeInter shapeModel, Shape shapeView) {
        if (!getView().getShapesInCanvas().remove(shapeView)) {
            System.out.println("Shape in view.getShapesCanvas not find");
        }
        if (!getShapesInCanvas().remove(shapeModel)) {
            System.out.println("model in getShapesInCanvas not find");
        }
        BorderPane bp = (BorderPane) getView().getRoot();
        bp.getChildren().remove(shapeView);
        //shapesInCanvas.remove(shapeModel);
    }

    /**
     * Update view after removing shape(s)
     */
    public void updateViewRemove(ShapeInter shape) {
        boolean noGroup = true;
        // Range shape group in order to find the corresponding shape in view
        for (ShapeInter shapeGroup : getShapeGroups()) {
            if (shape.equals(shapeGroup)) {
                for (int nChild = 0; nChild < shapeGroup.getChildren().size(); nChild++) {
                    for (int i = 0; i < view.getShapesInCanvas().size(); i++) {
                        ShapeInter shapeModel = shapeGroup.getChild(nChild);
                        Shape shapeView = view.getShapesInCanvas().get(i);
                        if (shapeModel.getPos().getX() == view.getShapeXPositionInToolBar(shapeView) && shapeModel.getPos().getY() == view.getShapeYPositionInToolBar(shapeView)) {
                            removeShape(shapeModel, shapeView);
                            noGroup = false;
                        }
                    }
                }
            }
        }
        // if the shape doesn't belong to a group
        if (noGroup) {
            for (int i = 0; i < view.getShapesInCanvas().size(); i++) {
                Shape shapeView = view.getShapesInCanvas().get(i);
                if (shape.getPos().getX() == view.getShapeXPositionInToolBar(shapeView) && shape.getPos().getY() == view.getShapeYPositionInToolBar(shapeView)) {
                    removeShape(shape, shapeView);
                }
            }
        }
    }

    public void updateViewRotate(ShapeInter shape, double value, boolean isShapeGroup) {
        if (isShapeGroup == true) {
            for (ShapeInter child : shape.getChildren()) {
                int shapeIndex = shapesInCanvas.indexOf(child);
                Shape shapeView = view.getShapesInCanvas().get(shapeIndex);
                Point rotationCenter = child.getRotationCenter();
                Point oldPt = PointFactory.getPoint(view.getXPosition(shapeView), view.getYPosition(shapeView));
                Rotate newRotation = new Rotate(value, rotationCenter.getX(), rotationCenter.getY());
                shapeView.getTransforms().add(newRotation);
                shapeView.setTranslateX(shapeView.getTranslateX() + (oldPt.getX() - view.getXPosition(shapeView)));
                shapeView.setTranslateY(shapeView.getTranslateY() + (oldPt.getY() - view.getYPosition(shapeView)));
            }
        } else {
            int shapeIndex = shapesInCanvas.indexOf(shape);
            Shape shapeView = view.getShapesInCanvas().get(shapeIndex);
            Point rotationCenter = shape.getRotationCenter();
            Point oldPt = PointFactory.getPoint(view.getXPosition(shapeView), view.getYPosition(shapeView));
            Rotate newRotation = new Rotate(value, rotationCenter.getX(), rotationCenter.getY());
            shapeView.getTransforms().add(newRotation);
            shapeView.setTranslateX(shapeView.getTranslateX() + (oldPt.getX() - view.getXPosition(shapeView)));
            shapeView.setTranslateY(shapeView.getTranslateY() + (oldPt.getY() - view.getYPosition(shapeView)));
        }
    }

    public void updateViewResize(ShapeInter shape){
        int shapeIndex = shapesInCanvas.indexOf(shape);
        Shape shapeView = view.getShapesInCanvas().get(shapeIndex);
        ArrayList<Double> vector = shape.getVector();
        //Polygon
        if(vector.size() == 1){
            Polygon polygon = (Polygon) shapeView;
            Point oldPt = PointFactory.getPoint(view.getXPosition(polygon), view.getYPosition(polygon));
            int nbEdges = polygon.getPoints().size()/2;
            Double[] newPoints = shape.getPoints(nbEdges, vector.get(0));
            polygon.getPoints().clear();
            polygon.getPoints().addAll(newPoints);
            polygon.setTranslateX(shapeView.getTranslateX() + (oldPt.getX() - view.getXPosition(polygon)));
            polygon.setTranslateY(shapeView.getTranslateY() + (oldPt.getY() - view.getYPosition(polygon)));
        }
        //Rectangle
        if(vector.size() == 2){
            Rectangle rectangle = (Rectangle) shapeView;
            rectangle.setWidth(vector.get(0));
            rectangle.setHeight(vector.get(1));
        }
    }

    public void updateEvents(){
        for(Events event : getEvents()) {
            event.launchEvent();
        }
    }


    public void initEvents(Controller controller) {

        events.add(new RightClick(controller));
        events.add(new DragAndDropEvent(controller));
        events.add(new CreateShapeEvent(controller));
        events.add(new SelectionShapeEvent(controller));
        events.add(new GroupShapeEvent(controller));
        events.add(new RedoEvent(controller));
        events.add(new UndoEvent(controller));
        events.add(new SaveEvent(controller));
        events.add(new LoadEvent(controller));
        updateEvents();
    }

    public ShapeInter getShapeGroupTmp(){
        return shapeGroupTmp;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ArrayList<Events> getEvents() {
        return events;
    }

    public Scene getScene() {
        return (Scene) view.getScene();
    }

    public ViewJavaFXAdaptee getView() {
        return view;
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

    public void addLastCommand(Command command){
        commands.addLast(command);
    }

    public int getNbCommands(){
        return commands.size()-1;
    }

    public int getCurrentPosInCommands() {
        return currentPosInCommands;
    }

    public void setCurrentPosInCommands(int currentPosInCommands) {
        this.currentPosInCommands = currentPosInCommands;
    }

    public void setShapeGroupTmp(ShapeInter shapeGroupTmp) {
        this.shapeGroupTmp = shapeGroupTmp;
    }
}
