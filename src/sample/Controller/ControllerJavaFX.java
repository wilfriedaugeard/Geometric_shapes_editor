package sample.Controller;

import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import sample.Controller.Command.Command;
import sample.Controller.Events.*;
import sample.Factory.EventFactory;
import sample.Factory.PointFactory;
import sample.Model.*;
import sample.View.IShapeDrawer;
import sample.View.ViewJavaFXAdaptee;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class ControllerJavaFX implements Serializable {
    private static final long serialVersionUID = 1L;
    /* View */
    private transient ViewJavaFXAdaptee view;

    /* Model */
    private ArrayList<ShapeInter> shapesInToolBar;
    private ArrayList<ShapeInter> shapesInCanvas;
    private transient ArrayList<Event> events;
    private ArrayList<ShapeInter> shapeGroups;
    private ArrayList<ShapeInter> shapeGroupsInToolBar;
    private ShapeInter shapeGroupTmp;

    /* Pattern Command */
    private transient int currentPosInCommands;
    private transient LinkedList<Command> commands;

    /* State */
    private boolean existState;

    public ControllerJavaFX(ViewJavaFXAdaptee view) {

        this.view = view;
        shapesInCanvas = new ArrayList<>();
        shapesInToolBar = new ArrayList<>();
        shapeGroups = new ArrayList<>();
        shapeGroupsInToolBar = new ArrayList<>();
        events = new ArrayList<>();
        commands = new LinkedList<>();
        shapeGroupTmp = new ShapeGroup();
        currentPosInCommands = 0;
    }

    /**
     * Resize a shape.
     * @param shape The ShapeInter to resize.
     */
    public void resizeShape(ShapeInter shape){
        ToolBar toolbar = (ToolBar) view.getToolBar();
        double toolbar_w = toolbar.getPrefWidth();
        // Resize shape
        if(shape.getWidth() >= toolbar_w) {
            double margin_left = toolbar.getPadding().getLeft();
            double margin_right = toolbar.getPadding().getRight();
            double value = (toolbar_w - margin_left - margin_right) / shape.getWidth();
            ArrayList<Double> vector = shape.getVector();
            for (int i = 0; i < vector.size(); i++) {
                vector.set(i, vector.get(i) * value);
            }
            shape.setVector(vector);
            shape.setCoeff(value);
        }
    }

    /**
     * Add ShapeInter in ToolBar, and draw it in View.
     * @param shape The ShapeInter to add in the Toolbar's list
     * @param controller The Controller used for drawing
     * @param itemPos Index in the Toolbar's list
     * @param shapePos Index in the Toolbar's list in the View
     */
    public void addShapeInToolbar(ShapeInter shape, Controller controller, int itemPos, int shapePos){
        if(shape.getChildren().isEmpty()){
            System.out.println("Empty case in addShapeInToolBar function");
            shapesInToolBar.add(itemPos, shape);
        }
        else{
            int pos = shapePos;
            for (ShapeInter child : shape.getChildren()){
                shapesInToolBar.add(pos, child);
                pos++;
            }
            shapeGroupsInToolBar.add(shape);
        }
        IShapeDrawer drawer = shape.createShapeDrawer(controller);
        drawer.drawShapeInToolBar(itemPos, shapePos);
    }


    /**
     * Initialize every part of the View, by calling it's methods and by drawing the first Toolbar's elements.
     * @param controller Controller used to initialize view
     */
    public void initializeView(Controller controller) {
        view.addMenuBar();
        view.addCanvas();
        view.addShapeMenu();

        setState();
        if(!isExistState()){
            ToolBar toolBar = (ToolBar) view.getToolBar();
            toolBar.setPrefWidth(50);

            RectangleJavaFX rec1 = new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(247, 220, 111));
            RectangleJavaFX rec2 = new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(130, 224, 170));
            PolygonJavaFX  poly1 = new PolygonJavaFX(5, 25, new Point(0, 20), new RGB(133, 193, 233));
            PolygonJavaFX  poly2 = new PolygonJavaFX(7, 35, new Point(0, 20), new RGB(245, 203, 167));

            resizeShape(rec1);
            resizeShape(rec2);
            resizeShape(poly1);
            resizeShape(poly2);

            addShapeInToolbar(rec1, controller, toolBar.getItems().size(), toolBar.getItems().size());
            addShapeInToolbar(rec2, controller, toolBar.getItems().size(), toolBar.getItems().size());
            addShapeInToolbar(poly1,controller, toolBar.getItems().size(), toolBar.getItems().size());
            addShapeInToolbar(poly2,controller, toolBar.getItems().size(), toolBar.getItems().size());
        }
        else{
            loadState(controller);
        }



        view.addTrash();
    }


    /**
     * Update View after a ShapeInter, or a group of ShapeInter have been modified on their RGB color.
     */
    public void updateViewColor() {
        for (ShapeInter shapeModel : shapesInCanvas) {
            for (Shape shapeView : view.getShapesInCanvas()) {
                if (shapeModel.getPos().getX() == view.getShapeXPositionInToolBar(shapeView) && shapeModel.getPos().getY() == view.getShapeYPositionInToolBar(shapeView)) {
                    double red = shapeModel.getRGB().getR();
                    double blue = shapeModel.getRGB().getB();
                    double green = shapeModel.getRGB().getG();
                    shapeView.setFill(Color.rgb((int) red, (int) green,(int) blue));
                }
            }
        }
    }

    /**
     * Update View after a ShapeInter or a group of ShapeInter have been modified with their translate method.
     * @param shape The Shapeinter translated
     * @param dragX Value to translate shapeView in X coordinate
     * @param dragY Value to translate shapeView in Y coordinate
     * @param isShapeGroup true if param shape is a ShapeGroup
     */
    public void updateViewTranslate(ShapeInter shape, double dragX, double dragY, boolean isShapeGroup) {
        if (isShapeGroup) {
            for (ShapeInter child : shape.getChildren()) {
                int shapeIndex = shapesInCanvas.indexOf(child);
                Shape shapeView = view.getShapesInCanvas().get(shapeIndex);
                shapeView.setTranslateX(shapeView.getTranslateX() + dragX);
                shapeView.setTranslateY(shapeView.getTranslateY() + dragY);
            }
        } else {
            int shapeIndex = shapesInCanvas.indexOf(shape);
            if(shapeIndex < 0)
                return;
            Shape shapeView = view.getShapesInCanvas().get(shapeIndex);
            shapeView.setTranslateX(shapeView.getTranslateX() + dragX);
            shapeView.setTranslateY(shapeView.getTranslateY() + dragY);
        }
    }

    /**
     * Remove a shape in both sides : View and Controller.
     * @param shapeModel ShapeInter to remove
     * @param shapeView Shape to remove
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
    }

    /**
     * Update view after removing shape(s).
     * @param shape ShapeInter removed
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

    /**
     * Update View after a ShapeInter, or a group of ShapeInter have been modified on their rotation value.
     * @param shape ShapeInter rotated
     * @param value Value in degree of the rotation
     * @param isShapeGroup true if param shape is a ShapeGroup
     */
    public void updateViewRotate(ShapeInter shape, double value, boolean isShapeGroup) {
        if (isShapeGroup) {
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

    /**
     * Update View after a ShapeInter, or a group of ShapeInter have been modified on their size.
     * @param shape ShapeInter rezised
     */
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
            assert shapeView instanceof Rectangle;
            Rectangle rectangle = (Rectangle) shapeView;
            rectangle.setWidth(vector.get(0));
            rectangle.setHeight(vector.get(1));
        }
    }

    /**
     * Method used to relaunch every events initialized.
     */
    public void updateEvents(){
        for(Event event : getEvents()) {
            event.launchEvent();
        }
    }


    /**
     * Save the state of the toolbar
     */
    public void saveState() {
        System.out.println("SAVE STATE");
        setExistState(true);
        String filename = "state.ctrl";
        ObjectOutputStream oos = null;
        try {
            File file = new File(filename);
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(this);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Load toolbar state
     * @param controller The controller to load
     */
    public void loadState(Controller controller){
        String filename = "state.ctrl";
        ObjectInput ois = null;
        try {
            final FileInputStream file = new FileInputStream(filename);
            ois = new ObjectInputStream(file);
            final ControllerJavaFX controller_load = (ControllerJavaFX) ois.readObject();

            //controller.getShapesInToolBar().addAll(controller_load.getShapesInToolBar());
            ToolBar toolBar = (ToolBar) controller.getView().getToolBar();
            for (ShapeInter shape : controller_load.getShapesInToolBar()) {
                addShapeInToolbar(shape, controller, toolBar.getItems().size(), toolBar.getItems().size());
            }
            controller.updateEvents();

        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Verify if a state is saved and set the state boolean value
     */
    public void setState(){
        String filename = "state.ctrl";
        ObjectInput ois = null;
        try{
            final FileInputStream file = new FileInputStream(filename);
            ois = new ObjectInputStream(file);
            final ControllerJavaFX controller_load = (ControllerJavaFX) ois.readObject();
            setExistState(true);
        } catch (IOException | ClassNotFoundException e){
            setExistState(false);
        }
    }

    /**
     * Initialize every event and add them to the Event's list.
     * After that, launch every event with updateEvents method.
     * @param controller Controller where events are added
     */
    public void initEvents(Controller controller) {

        events.add(EventFactory.getEvent("RightClick",controller));
        events.add(EventFactory.getEvent("DragAndDropEvent",controller));
        events.add(EventFactory.getEvent("CreateShapeEvent",controller));
        events.add(EventFactory.getEvent("SelectionShapeEvent",controller));
        events.add(EventFactory.getEvent("GroupShapeEvent",controller));
        events.add(EventFactory.getEvent("RedoEvent",controller));
        events.add(EventFactory.getEvent("UndoEvent",controller));
        events.add(EventFactory.getEvent("SaveEvent",controller));
        events.add(EventFactory.getEvent("LoadEvent",controller));
        updateEvents();
    }

    /**
     * @return Temporary group of ShapeInter selected
     */
    public ShapeInter getShapeGroupTmp(){
        return shapeGroupTmp;
    }

    /**
     * @return ID for serializable
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return The list of events in the Controller
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * @return The list of ShapeInter groups in the ToolBar
     */
    public ArrayList<ShapeInter> getShapeGroupsInToolBar(){
        return shapeGroupsInToolBar;
    }

    /**
     * @return The scene initialized with JavaFX
     */
    public Scene getScene() {
        return (Scene) view.getScene();
    }

    /**
     * @return The View used by the software
     */
    public ViewJavaFXAdaptee getView() {
        return view;
    }

    /**
     * @return The list of ShapeInter in the ToolBar
     */
    public ArrayList<ShapeInter> getShapesInToolBar() {
        return shapesInToolBar;
    }

    /**
     * @return The list of ShapeInter groups in the Canvas
     */
    public ArrayList<ShapeInter> getShapesInCanvas() {
        return shapesInCanvas;
    }

    /**
     * @return The list of ShapeInter groups in the Canvas
     */
    public ArrayList<ShapeInter> getShapeGroups() {
        return shapeGroups;
    }

    /**
     * @return The list of Commands executed
     */
    public LinkedList<Command> getCommands() {
        return commands;
    }

    /**
     * Add in the last position a Command object in the Command's list.
     * @param command The object Command to add
     */
    public void addLastCommand(Command command){
        commands.addLast(command);
    }


    /**
     * @return The number of elements in the Command's list
     */
    public int getNbCommands(){
        return commands.size()-1;
    }


    /**
     * @return The current position pointed in the Command's list
     */
    public int getCurrentPosInCommands() {
        return currentPosInCommands;
    }


    /**
     * @param currentPosInCommands The current position in the Command's list wanted
     */
    public void setCurrentPosInCommands(int currentPosInCommands) {
        this.currentPosInCommands = currentPosInCommands;
    }


    /**
     * @param shapeGroupTmp The temporary ShapeGroup
     */
    public void setShapeGroupTmp(ShapeInter shapeGroupTmp) {
        this.shapeGroupTmp = shapeGroupTmp;
    }

    /**
     * @return The existState
     */
    public boolean isExistState() {
        return existState;
    }

    /**
     * @param existState Boolean to ckeck if a save of the soft state exist
     */
    public void setExistState(boolean existState) {
        this.existState = existState;
    }
}
