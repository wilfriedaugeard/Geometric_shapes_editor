package sample.Controller;

import javafx.scene.shape.Shape;
import sample.Controller.Command.ICommand;
import sample.Controller.Events.Event;
import sample.Model.IShapeInter;
import sample.View.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public interface IController extends Serializable {

    /**
     * Initialize every part of the View, by calling it's methods and by drawing the first Toolbar's elements.
     */
    public void initializeView();

    /**
     * Update View after a ShapeInter, or a group of ShapeInter have been modified on their RGB color.
     */
    public void updateViewColor();

    /**
     * Update View after a ShapeInter or a group of ShapeInter have been modified with their translate method.
     * @param shape The Shapeinter translated
     * @param dragX Value to translate shapeView in X coordinate
     * @param dragY Value to translate shapeView in Y coordinate
     * @param isShapeGroup true if param shape is a ShapeGroup
     */
    public void updateViewTranslate(IShapeInter shape, double dragX, double dragY, boolean isShapeGroup);

    /**
     * Remove a shape in both sides : View and Controller.
     * @param shapeModel ShapeInter to remove
     * @param shapeView Shape to remove
     */
    public void removeShape(IShapeInter shapeModel, Object shapeView);

    /**
     * Update view after removing shape(s).
     * @param shape ShapeInter removed
     */
    public void updateViewRemove(IShapeInter shape);

    /**
     * Update View after a ShapeInter, or a group of ShapeInter have been modified on their rotation value.
     * @param shape ShapeInter rotated
     * @param value Value in degree of the rotation
     */
    public void updateViewRotate(IShapeInter shape, double value);

    /**
     * Update View after a ShapeInter, or a group of ShapeInter have been modified on their size.
     * @param shape ShapeInter rezised
     */
    public void updateViewResize(IShapeInter shape);

    /**
     * Add ShapeInter in ToolBar, and draw it in View.
     * @param shape The ShapeInter to add in the Toolbar's list
     * @param controller The Controller used for drawing
     * @param itemPos Index in the Toolbar's list
     * @param shapePos Index in the Toolbar's list in the View
     */
    public void addShapeInToolbar(IShapeInter shape, IController controller, int itemPos, int shapePos);

    /*Events*/

    /**
     * Initialize every event and add them to the Event's list.
     * After that, launch every event with updateEvents method.
     */
    public void initEvents();

    /**
     * Method used to relaunch every events initialized.
     */
    public void updateEvents();

    /**
     * @return The list of events in the Controller
     */
    public ArrayList<Event> getEvents();

    /*Pattern Composite*/

    /**
     * @return Temporary group of ShapeInter selected
     */
    public IShapeInter getShapeGroupTmp();

    /**
     * @param shapeGroupTmp The temporary ShapeGroup
     */
    public void setShapeGroupTmp(IShapeInter shapeGroupTmp);

    /*View Links*/
    /**
     * @return The scene initialized
     */
    public Object getScene(); //ToAdapt

    /**
     * @return The View used by the software
     */
    public IView getView(); //ToAdapt

    /*Get shapes*/
    /**
     * @return The list of ShapeInter in the ToolBar
     */
    public ArrayList<IShapeInter> getShapesInToolBar();

    /**
     * @return The list of ShapeInter groups in the Canvas
     */
    public ArrayList<IShapeInter> getShapesInCanvas();

    /**
     * @return The list of ShapeInter groups in the Canvas
     */
    public ArrayList<IShapeInter> getShapeGroups();

    /**
     * @return The list of ShapeInter groups in the ToolBar
     */
    public ArrayList<IShapeInter> getShapeGroupsInToolBar();

    /*Pattern Command*/
    /**
     * @return The list of Commands executed
     */
    public LinkedList<ICommand> getCommands();

    /**
     * Add in the last position a Command object in the Command's list.
     * @param command The object Command to add
     */
    public void addLastCommand(ICommand command);

    /**
     * @return The number of elements in the Command's list
     */
    public int getNbCommands();

    /**
     * @return The current position pointed in the Command's list
     */
    public int getCurrentPosInCommands();

    /**
     * @param currentPosInCommands The current position in the Command's list wanted
     */
    public void setCurrentPosInCommands(int currentPosInCommands);

    /* Controller state */
    /**
     * Save the state of the toolbar
     */
    public void saveState();

    /**
     * Load toolbar state
     * @param controller The controller to load
     */
    public void loadState(IController controller);

    /**
     * @return The existState
     */
    public boolean isExistState();

    /**
     * @param existState Boolean to ckeck if a save of the soft state exist
     */
    public void setExistState(boolean existState);


    /**
     * Create a shape in the canvas
     * @param controller The controller
     * @param shapeModel The shape to create
     * @param x The x position of the shape
     * @param y The y position of the shape
     * @param applyCoeff Boolean to apply the size coefficient
     * @return The created shape
     */
    public IShapeInter createShapeInCanvas(IController controller, IShapeInter shapeModel, double x, double y, boolean applyCoeff);
}
