package sample.View;
import javafx.scene.shape.Shape;
import sample.Model.Point;

import java.util.ArrayList;

public interface IView {
    /* UI */

    /**
     * Add top menu bar, including Save, Load, Undo and Redo buttons.
     */
    void addMenuBar();

    /**
     * Add Canvas to the View.
     */
    void addCanvas();

    /**
     * Add trash to the View.
     */
    void addTrash();

    /**
     * Add every element for the menu when a right click happens on a Shape.
     * Including "Group", "Degroup" and "Edit" actions.
     */
    void addShapeMenu();

    /* Position */

    /**
     * @param shape Shape to get position
     * @return Position X from Shape
     */
    double getShapeXPositionInToolBar(Object shape);

    /**
     * @param shape Shape to get position
     * @return Position Y from Shape
     */
    double getShapeYPositionInToolBar(Object shape);

    /**
     * @param node Node to get position
     * @return Position X from node
     */
    double getXPosition(Object node);

    /**
     * @param node Node to get position
     * @return Position Y from node
     */
    double getYPosition(Object node);

    /**
     * @param node Node to check
     * @param point Point object to check if it's in node properties
     * @return true if the point is on the node
     */
    boolean isOn(Object node, Point point);

    /* Events */

    /**
     * Set on mouse released on Shapes from the Toolbar and the Canvas.
     * @param event Event to set on View elements
     */
    void launch_overToolbar(Object event);

    /**
     * Set on mouse dragged on Shapes from the Toolbar and the Canvas.
     * @param event Event to set on View elements
     */
    void launch_finalShapeToCanvas(Object event);

    /**
     * Set on mouse entered on Shapes from the Canvas.
     * @param event Event to set on View elements
     */
    void launch_moveShapeOnPressingMouse(Object event);

    /**
     * Set on mouse pressed on Shapes from the Toolbar and the Canvas.
     * @param event Event to set on View elements
     */
    void launch_getShapeOnMousePressed(Object event);

    /**
     * Set on mouse pressed on the root.
     * @param event Event to set on View elements
     */
    void launch_createSelectionRectangleOnClick(Object event);

    /**
     * Set on mouse released on the root.
     * @param event Event to set on View elements
     */
    void launch_rectangleSelectionReleased(Object event);

    /**
     * Set on mouse dragged on the root.
     * @param event Event to set on View elements
     */
    void launch_selectionRectangleDraggedInCanvas(Object event);

    /**
     * Request menu on right click on Shapes from the Canvas.
     * @param event Event to set on View elements
     */
    void launch_rightClick(Object event);

    /**
     * Set action on the menu item colorPicker.
     * @param event Event to set on View elements
     */
    void launch_colorPickerHandler(Object event);

    /**
     * Set action on the menu item group.
     * @param event Event to set on View elements
     */
    void launch_groupShape(Object event);

    /**
     * Set action on the menu item degroup.
     * @param event Event to set on View elements
     */
    void launch_deGroupShape(Object event);

    /**
     * Set on mouse clicked on Shapes from the Canvas.
     * @param event Event to set on View elements
     */
    void launch_CTRLAndMouseOnClickShapeGroup(Object event);

    /**
     * Set action on undo button.
     * @param event Event to set on View elements
     */
    void launch_undoEvent(Object event);

    /**
     * Set action on redo button.
     * @param event Event to set on View elements
     */
    void launch_redoEvent(Object event);

    /**
     * Set action on save button.
     * @param event Event to set on View elements
     */
    void launch_saveEvent(Object event);

    /**
     * Set action on load button.
     * @param event Event to set on View elements
     */
    void launch_loadEvent(Object event);

    /**
     * Set action on edit option.
     * @param event Event to set on View elements
     */
    void launch_editShape(Object event);

    /**
     * Set on mouse pressed on Shapes from the Toolbar.
     * @param event Event to set on View elements
     */
    void launch_dragInToolBar(Object event);

    /*Getters*/

    ArrayList<Shape> getShapesInToolBar();
    ArrayList<Shape> getShapesInCanvas();
    Object getScene();
    Object getCanvas();
    Object getMenuBar();
    Object getToolBar();
    Object getRoot();
    Object getShapeMenu();
    Object getGroup() ;
    Object getDeGroup();
    Object getColor();
    Object getColorPicker();
    Object getSave_as();
    Object getLoad();
    Object getUndo();
    Object getRedo();
    Object getTrash();
    int getWIDTH();
    int getHEIGHT();

    /* Other */
    void onTrashInfo(Point point);

}
