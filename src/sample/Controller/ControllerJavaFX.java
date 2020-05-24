package sample.Controller;

import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import sample.Controller.Command.ICommand;
import sample.Controller.Command.ResizeCommand;
import sample.Controller.Events.*;
import sample.Factory.ControllerFactory.EventFactory;
import sample.Factory.ModelFactory.*;
import sample.Model.*;
import sample.Model.Decorator.BornedRotate;
import sample.Model.Decorator.DecoratorShapeInter;
import sample.View.Drawer.IShapeDrawer;
import sample.View.ViewJavaFXAdaptee;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class ControllerJavaFX implements Serializable {
    private static final long serialVersionUID = 1L;
    /* View */
    private transient ViewJavaFXAdaptee view;

    /* Model */
    private ArrayList<IShapeInter> shapesInToolBar;
    private ArrayList<IShapeInter> shapesInCanvas;
    private transient ArrayList<Event> events;
    private ArrayList<IShapeInter> shapeGroups;
    private ArrayList<IShapeInter> shapeGroupsInToolBar;
    private IShapeInter shapeGroupTmp;

    /* Pattern Command */
    private transient int currentPosInCommands;
    private transient LinkedList<ICommand> commands;

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
        shapeGroupTmp = ShapeGroupFactory.createGroup();
        currentPosInCommands = 0;
    }

    /**
     * Resize a shape.
     * @param shape The ShapeInter to resize.
     */
    public void resizeShape(IShapeInter shape){
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
    public void addShapeInToolbar(IShapeInter shape, IController controller, int itemPos, int shapePos){
        if(shape.getChildren().isEmpty()){
            shapesInToolBar.add(itemPos, shape);
        }
        else{
            int pos = shapePos;
            for (IShapeInter child : shape.getChildren()){
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
    public void initializeView(IController controller) {
        view.addMenuBar();
        view.addCanvas();
        view.addShapeMenu();

        setState();
        if(!isExistState()){
            ToolBar toolBar = (ToolBar) view.getToolBar();
            toolBar.setPrefWidth(50);

            IBornedRotateFactory bornedRotateFactory = new BornedRotateFactory();

            IShapeInter rec1 = bornedRotateFactory.createShape(ShapeModelFactory.createShape("Rectangle", 50, 25, PointFactory.getPoint(0, 0), RGBFactory.getRGB(247, 220, 111)));
            IShapeInter rec2 = bornedRotateFactory.createShape(ShapeModelFactory.createShape("Rectangle", 50, 25, PointFactory.getPoint(0, 0), RGBFactory.getRGB(130, 224, 170)));
            IShapeInter poly1 = bornedRotateFactory.createShape(ShapeModelFactory.createShape("Polygon", 5, 25, PointFactory.getPoint(0, 20), RGBFactory.getRGB(133, 193, 233)));
            IShapeInter poly2 = bornedRotateFactory.createShape(ShapeModelFactory.createShape("Polygon",  7, 35, PointFactory.getPoint(0, 20), RGBFactory.getRGB(245, 203, 167)));

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
        for(int i=0; i<getShapesInCanvas().size(); i++){
            IShapeInter shapeModel = getShapesInCanvas().get(i);
            Shape shapeView = getView().getShapesInCanvas().get(i);
            double red = shapeModel.getRGB().getR();
            double blue = shapeModel.getRGB().getB();
            double green = shapeModel.getRGB().getG();
            shapeView.setFill(Color.rgb((int) red, (int) green,(int) blue));
        }
    }

    /**
     * Update View after a ShapeInter or a group of ShapeInter have been modified with their translate method.
     * @param shape The Shapeinter translated
     * @param dragX Value to translate shapeView in X coordinate
     * @param dragY Value to translate shapeView in Y coordinate
     * @param isShapeGroup true if param shape is a ShapeGroup
     */
    public void updateViewTranslate(IShapeInter shape, double dragX, double dragY, boolean isShapeGroup) {
        if (isShapeGroup) {
            for (IShapeInter child : shape.getChildren()) {
                int shapeIndex = shapesInCanvas.indexOf(child);
                if(shapeIndex < 0)
                    return;
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
    public void removeShape(IShapeInter shapeModel, Shape shapeView) {
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
     * Remove a shape in toolbar
     * @param shapeInToolbar The shape to remove
     * @param isInShapeGroup A boolean to tell if the shape is a group
     */
    public void removeShapeInToolbar(IShapeInter shapeInToolbar, Boolean isInShapeGroup){
        ToolBar toolBar = (ToolBar) getView().getToolBar();
        int index = getShapesInToolBar().indexOf(shapeInToolbar);
        Shape shapeInView = getView().getShapesInToolBar().get(index);
        if(isInShapeGroup){
            for(IShapeInter child : shapeInToolbar.getChildren()){
                // View delete
                int i = getShapesInToolBar().indexOf(child);
                shapeInView = getView().getShapesInToolBar().get(i);
                toolBar.getItems().remove(shapeInView);
                getView().getShapesInToolBar().remove(shapeInView);
                getShapesInToolBar().remove(child);
            }
        }else{
            getShapesInToolBar().remove(shapeInToolbar);
            toolBar.getItems().remove(shapeInView);
            getView().getShapesInToolBar().remove(shapeInView);
        }
    }

    /**
     * Update view after removing shape(s).
     * @param shape ShapeInter removed
     */
    public void updateViewRemove(IShapeInter shape) {
        boolean noGroup = true;
        // Range shape group in order to find the corresponding shape in view
        ArrayList<IShapeInter> groupToRemove = new ArrayList<>();
        for (IShapeInter shapeGroup : getShapeGroups()) {
            if (shape.equals(shapeGroup)) {
                groupToRemove.add(shapeGroup);
                for (int nChild = 0; nChild < shapeGroup.getChildren().size(); nChild++) {
                    IShapeInter shapeModel = shapeGroup.getChild(nChild);
                    int i = getShapesInCanvas().indexOf(shapeModel);
                    Shape shapeView = view.getShapesInCanvas().get(i);
                    removeShape(shapeModel, shapeView);
                    noGroup = false;
                }
            }
        }
        getShapeGroups().removeAll(groupToRemove);
        // if the shape doesn't belong to a group
        if (noGroup) {
            int i = shapesInCanvas.indexOf(shape);
            Shape shapeView = view.getShapesInCanvas().get(i);
            removeShape(shape, shapeView);
        }
    }

    /**
     * Update View after a ShapeInter, or a group of ShapeInter have been modified on their rotation value.
     * @param shape ShapeInter rotated
     * @param value Value in degree of the rotation
     */
    public void updateViewRotate(IShapeInter shape, double value) {
        int shapeIndex = shapesInCanvas.indexOf(shape);
        Shape shapeView = view.getShapesInCanvas().get(shapeIndex);
        Point rotationCenter = shape.getRotationCenter();
        Point oldPt = PointFactory.getPoint(view.getXPosition(shapeView), view.getYPosition(shapeView));
        Rotate newRotation = new Rotate(value, rotationCenter.getX(), rotationCenter.getY());
        shapeView.getTransforms().add(newRotation);
        shapeView.setTranslateX(shapeView.getTranslateX() + (oldPt.getX() - view.getXPosition(shapeView)));
        shapeView.setTranslateY(shapeView.getTranslateY() + (oldPt.getY() - view.getYPosition(shapeView)));
    }

    /**
     * Update View after a ShapeInter, or a group of ShapeInter have been modified on their size.
     * @param shape ShapeInter rezised
     */
    public void updateViewResize(IShapeInter shape){
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
    public void loadState(IController controller){
        String filename = "state.ctrl";
        ObjectInput ois = null;
        try {
            final FileInputStream file = new FileInputStream(filename);
            ois = new ObjectInputStream(file);
            final ControllerJavaFX controller_load = (ControllerJavaFX) ois.readObject();

            controller.getShapeGroupsInToolBar().addAll(controller_load.getShapeGroupsInToolBar());
            ToolBar toolBar = (ToolBar) controller.getView().getToolBar();
            for (IShapeInter shape : controller_load.getShapesInToolBar()) {
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
            setExistState(true);
        } catch (IOException e){
            setExistState(false);
        }
    }

    /**
     * Initialize every event and add them to the Event's list.
     * After that, launch every event with updateEvents method.
     * @param controller Controller where events are added
     */
    public void initEvents(IController controller) {
        events.add(EventFactory.getEvent("RightClick",controller));
        events.add(EventFactory.getEvent("DragAndDropEvent",controller));
        events.add(EventFactory.getEvent("SelectionShapeEvent",controller));
        events.add(EventFactory.getEvent("GroupShapeEvent",controller));
        events.add(EventFactory.getEvent("RedoEvent",controller));
        events.add(EventFactory.getEvent("UndoEvent",controller));
        events.add(EventFactory.getEvent("SaveEvent",controller));
        events.add(EventFactory.getEvent("LoadEvent",controller));
        updateEvents();
    }

    /**
     * Create a shape in the canvas
     * @param controller The controller
     * @param shapeModel The shape to create
     * @param x The x position of the shape
     * @param y The y position of the shape
     * @param applyCoeff Boolean to apply the size coefficient
     * @return The created shape
     */
    public IShapeInter createShapeInCanvas(IController controller, IShapeInter shapeModel, double x, double y, boolean applyCoeff){
        for (IShapeInter group : controller.getShapeGroupsInToolBar()) {
            if (group.getChildren().contains(shapeModel)) {
                IShapeInter copy = group.clone();

                for(int i = 0; i<group.getChildren().size(); i++){
                    IShapeInter shapeInter = copy.getChild(i);
                    ArrayList<Double> vector = shapeInter.getVector();
                    if(applyCoeff){
                        for (int j = 0; j < vector.size(); j++) {
                            vector.set(j, vector.get(j) / shapeInter.getCoeff());
                        }
                    }
                    shapeInter.setVector(vector);
                    controller.getShapesInCanvas().add(shapeInter);

                    Point p = PointFactory.getPoint(x + copy.getChild(i).getDeltaX() , y+copy.getChild(i).getDeltaY());
                    shapeInter.setPos(p);
                }

                IShapeDrawer drawer = copy.createShapeDrawer(controller);
                drawer.drawShape();
                controller.getShapeGroups().add(copy);
                controller.updateEvents();
                return copy;
            }
        }
        IShapeInter copy = shapeModel.clone();
        ArrayList<Double> vector = copy.getVector();
        if (applyCoeff) {
            for (int i = 0; i < vector.size(); i++) {
                vector.set(i, vector.get(i) / copy.getCoeff());
            }
        }
        copy.setVector(vector);
        copy.setPos(PointFactory.getPoint(x, y));
        copy.setRGB(shapeModel.getRGB());
        IShapeDrawer drawer = copy.createShapeDrawer(controller);
        drawer.drawShape();
        controller.getShapesInCanvas().add(copy);
        controller.updateEvents();
        return copy;

    }


    /**
     * Resize a shape in order to add it in the toolbar
     * @param isShapeGroup A boolean to tell if the shape is a group
     * @param shape The shape to resize
     * @param coeff A resize coefficient
     */
    public void resizeInToolbar(boolean isShapeGroup, IShapeInter shape, double coeff){
        if(isShapeGroup){
            // Take the y min
            double min = Double.POSITIVE_INFINITY;
            IShapeInter shapeRef = shape;
            for(IShapeInter shapeChild : shape.getChildren()){
                min = Math.min(min, shapeChild.getPos().getY());
                shapeRef = shapeChild;
            }
            shapeRef.setDeltaX(0);
            shapeRef.setDeltaY(0);

            for(IShapeInter shapeChild : shape.getChildren()){
                if(!shapeChild.equals(shapeRef)){
                    double x = shapeChild.getPos().getX()-shapeRef.getPos().getX();
                    double y = shapeChild.getPos().getY()-shapeRef.getPos().getY();
                    shapeChild.setDeltaX(x);
                    shapeChild.setDeltaY(y);
                }
                ArrayList<Double> vector = shapeChild.getVector();
                for(int i = 0; i < vector.size(); i++){
                    vector.set(i, vector.get(i) * coeff);
                }
                shapeChild.setCoeff(coeff);
                shapeChild.setVector(vector);
                updateViewResize(shapeChild);
            }
        }else{
            ArrayList<Double> vector = shape.getVector();
            for(int i = 0; i < vector.size(); i++){
                vector.set(i, vector.get(i) * coeff);
            }
            shape.setCoeff(coeff);
            shape.setVector(vector);
            updateViewResize(shape);
        }
    }

    /**
     * A a shapeInter to the toolbar
     * @param controller The controller
     * @param shapeToTranslate The shape to add
     * @param isInShapeGroup A boolean to tell if the shape is a group
     */
    public void addToToolbar(IController controller, IShapeInter shapeToTranslate, boolean isInShapeGroup){
        ToolBar toolbar = (ToolBar) controller.getView().getToolBar();
        int itemPos = toolbar.getItems().size()-2;
        int shapePos = controller.getShapesInToolBar().size();

        double toolbar_w = toolbar.getWidth();
        double margin_left = toolbar.getPadding().getLeft();
        double margin_right = toolbar.getPadding().getRight();
        double value = (toolbar_w-margin_left-margin_right)/shapeToTranslate.getWidth();
        if(isInShapeGroup){
            for (IShapeInter shapeGroup : controller.getShapeGroups()) {
                if (shapeGroup.getChildren().contains(shapeToTranslate)) {
                    value = (toolbar_w-margin_left-margin_right)/shapeGroup.getWidth();
                    resizeInToolbar(isInShapeGroup, shapeGroup, value);
                    int index;
                    for(IShapeInter child : shapeGroup.getChildren()){
                        controller.addShapeInToolbar(child, controller, itemPos,shapePos);
                        index = controller.getShapesInCanvas().indexOf(child);
                        controller.removeShape(child, controller.getView().getShapesInCanvas().get(index));
                        itemPos++;
                        shapePos++;
                    }
                    controller.getShapeGroupsInToolBar().add(shapeGroup);
                    controller.updateEvents();
                    controller.saveState();
                    return;
                }
            }
        }
        resizeInToolbar(isInShapeGroup, shapeToTranslate, value);
        controller.addShapeInToolbar(shapeToTranslate, controller, itemPos,shapePos);
        int index = controller.getShapesInCanvas().indexOf(shapeToTranslate);
        controller.removeShape(shapeToTranslate, controller.getView().getShapesInCanvas().get(index));
        controller.updateEvents();
        controller.saveState();
    }
    /**
     * @return Temporary group of ShapeInter selected
     */
    public IShapeInter getShapeGroupTmp(){
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
    public ArrayList<IShapeInter> getShapeGroupsInToolBar(){
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
    public ArrayList<IShapeInter> getShapesInToolBar() {
        return shapesInToolBar;
    }

    /**
     * @return The list of ShapeInter groups in the Canvas
     */
    public ArrayList<IShapeInter> getShapesInCanvas() {
        return shapesInCanvas;
    }

    /**
     * @return The list of ShapeInter groups in the Canvas
     */
    public ArrayList<IShapeInter> getShapeGroups() {
        return shapeGroups;
    }

    /**
     * @return The list of Commands executed
     */
    public LinkedList<ICommand> getCommands() {
        return commands;
    }

    /**
     * Add in the last position a Command object in the Command's list.
     * @param command The object Command to add
     */
    public void addLastCommand(ICommand command){
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
    public void setShapeGroupTmp(IShapeInter shapeGroupTmp) {
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
