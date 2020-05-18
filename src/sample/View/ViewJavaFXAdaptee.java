package sample.View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Model.Point;

import java.util.ArrayList;

/**
 * Pattern Adapter
 */

public class ViewJavaFXAdaptee implements IView{
    private final ViewJavaFX viewJavaFX;

    public ViewJavaFXAdaptee(ViewJavaFX viewJavaFX) {
        this.viewJavaFX = viewJavaFX;
    }

    /* UI */
    @Override
    public void addMenuBar() {
        this.viewJavaFX.addMenuBar();
    }

    @Override
    public void addCanvas() {
        this.viewJavaFX.addCanvas();
    }

    @Override
    public void addTrash() {
        this.viewJavaFX.addTrash();
    }

    @Override
    public void addShapeMenu() {
        this.viewJavaFX.addShapeMenu();
    }

    /* Position */
    @Override
    public double getShapeXPositionInToolBar(Object shape) {
        return this.viewJavaFX.getShapeXPositionInToolBar((Shape)shape);
    }

    @Override
    public double getShapeYPositionInToolBar(Object shape) {
        return this.viewJavaFX.getShapeYPositionInToolBar((Shape) shape);
    }

    @Override
    public double getXPosition(Object node) {
        return this.viewJavaFX.getNodeXPosition((Node) node);
    }

    @Override
    public double getYPosition(Object node) {
        return this.viewJavaFX.getNodeYPosition((Node) node);
    }

    @Override
    public boolean isOn(Object node, Point point) {
        return this.viewJavaFX.isOnNode((Control) node, point);
    }

    /* Events */
    @Override
    public void launch_overToolbar(Object event) {
        this.viewJavaFX.launch_overToolbar((EventHandler<MouseEvent>) event);
    }

    @Override
    public void launch_finalShapeToCanvas(Object event) {
        this.viewJavaFX.launch_finalShapeToCanvas((EventHandler<MouseEvent>) event);
    }

    @Override
    public void launch_moveShapeOnPressingMouse(Object event) {
        this.viewJavaFX.launch_moveShapeOnPressingMouse((EventHandler<MouseEvent>) event);
    }

    @Override
    public void launch_getShapeOnMousePressed(Object event) {
        this.viewJavaFX.launch_getShapeOnMousePressed((EventHandler<MouseEvent>) event);
    }

    @Override
    public void launch_createShapeInToolBarOnClick(Object event) {
        this.viewJavaFX.launch_createShapeInToolBarOnClick((EventHandler<MouseEvent>) event);
    }

    @Override
    public void launch_createSelectionRectangleOnClick(Object event) {
        this.viewJavaFX.launch_createSelectionRectangleOnClick((EventHandler<MouseEvent>) event);
    }

    @Override
    public void launch_rectangleSelectionReleased(Object event) {
        this.viewJavaFX.launch_rectangleSelectionReleased((EventHandler<MouseEvent>) event);
    }

    @Override
    public void launch_selectionRectangleDraggedInCanvas(Object event) {
        this.viewJavaFX.launch_selectionRectangleDraggedInCanvas((EventHandler<MouseEvent>) event);
    }

    @Override
    public void launch_rightClick(Object event) {
        this.viewJavaFX.launch_rightClick((EventHandler<ContextMenuEvent>) event);
    }

    @Override
    public void launch_colorPickerHandler(Object event) {
        this.viewJavaFX.launch_colorPickerHandler((EventHandler<ActionEvent>) event);
    }

    @Override
    public void launch_groupShape(Object event) {
        this.viewJavaFX.launch_groupShape((EventHandler<ActionEvent>) event);
    }

    @Override
    public void launch_deGroupShape(Object event) {
        this.viewJavaFX.launch_deGroupShape((EventHandler<ActionEvent>) event);
    }

    @Override
    public void launch_CTRLAndMouseOnClickShapeGroup(Object event) {
        this.viewJavaFX.launch_CTRLAndMouseOnClickShapeGroup((EventHandler<MouseEvent>) event);
    }

    @Override
    public void launch_undoEvent(Object event) {
        this.viewJavaFX.launch_undoEvent((EventHandler<ActionEvent>) event);
    }

    @Override
    public void launch_redoEvent(Object event) {
        this.viewJavaFX.launch_redoEvent((EventHandler<ActionEvent>) event);
    }

    @Override
    public void launch_saveEvent(Object event) {
        this.viewJavaFX.launch_saveEvent((EventHandler<ActionEvent>) event);
    }

    @Override
    public void launch_loadEvent(Object event) {
        this.viewJavaFX.launch_loadEvent((EventHandler<ActionEvent>) event);
    }

    @Override
    public void launch_editShape(Object event) {
        this.viewJavaFX.launch_editShape((EventHandler<ActionEvent>) event);
    }

    @Override
    public void launch_dragInToolBar(Object event) {
        this.viewJavaFX.launch_dragInToolBar((EventHandler<MouseEvent>) event);
    }

    /* Other */
    @Override
    public void onTrashInfo(Point point) {
        this.viewJavaFX.onTrashInfo(point);
    }

    @Override
    public ArrayList<Shape> getShapesInToolBar() {
        return this.viewJavaFX.getShapesInToolBar();
    }

    @Override
    public ArrayList<Shape> getShapesInCanvas() {
        return this.viewJavaFX.getShapesInCanvas();
    }

    /* Getters */
    @Override
    public Object getScene() {
        return this.viewJavaFX.getScene();
    }

    @Override
    public Object getCanvas() {
        return this.viewJavaFX.getCanvas();
    }

    @Override
    public Object getMenuBar() {
        return this.viewJavaFX.getMenuBar();
    }

    @Override
    public Object getToolBar() {
        return this.viewJavaFX.getToolBar();
    }

    @Override
    public Object getRoot() {
        return this.viewJavaFX.getRoot();
    }

    @Override
    public Object getShapeMenu() {
        return this.viewJavaFX.getShapeMenu();
    }

    @Override
    public Object getGroup() {
        return this.viewJavaFX.getGroup();
    }

    @Override
    public Object getDeGroup() {
        return this.viewJavaFX.getDeGroup();
    }

    @Override
    public Object getColor() {
        return this.viewJavaFX.getColor();
    }

    @Override
    public Object getColorPicker() {
        return this.viewJavaFX.getColorPicker();
    }

    @Override
    public Object getSave_as() {
        return this.viewJavaFX.getSave_as();
    }

    @Override
    public Object getLoad() {
        return this.viewJavaFX.getLoad();
    }

    @Override
    public Object getUndo() {
        return this.viewJavaFX.getUndo();
    }

    @Override
    public Object getRedo() {
        return this.viewJavaFX.getRedo();
    }

    @Override
    public Object getTrash() {
        return this.viewJavaFX.getTrash();
    }

    @Override
    public int getWIDTH() {
        return viewJavaFX.getWIDTH();
    }

    @Override
    public int getHEIGHT() {
        return viewJavaFX.getHEIGHT();
    }

}
