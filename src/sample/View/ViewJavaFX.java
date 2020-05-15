package sample.View;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Shape;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import sample.Model.Point;


public class ViewJavaFX {
	

	private Pane canvas;
	private ToolBar menuBar;
	private ToolBar toolBar;
	private Scene scene;
	private BorderPane root;
	
	/* Shape Menu on right click */
	private ContextMenu shapeMenu;
	private MenuItem group;
	private MenuItem deGroup;
	private MenuItem color;
	private Menu edit;
	private ColorPicker colorPicker;
	private MenuItem edit_shape;
	private MenuItem resize_shape;

	private Button save_as;
	private Button load;
	private Button undo;
	private Button redo;
	private Button trash;

	private ArrayList<Shape> shapesInToolBar;
	private ArrayList<Shape> shapesInCanvas;

	/**
	 * Width of the window
	 */
	private final int WIDTH = 600;

	/**
	 * Height of the window
	 */
	private final int HEIGHT = 600;

	public ViewJavaFX() {
		canvas = new Pane();
		menuBar = new ToolBar();

		toolBar = new ToolBar();
		root = new BorderPane();

		
		shapesInToolBar = new ArrayList<Shape>();
		shapesInCanvas = new ArrayList<Shape>();

		shapeMenu = new ContextMenu();
		group = new MenuItem("Group");
		deGroup = new MenuItem("De-group");
		edit = new Menu("Edit");

		colorPicker = new ColorPicker();
		color = new MenuItem("Edit color", colorPicker);
		edit_shape = new MenuItem("Rotate shape");
		resize_shape = new MenuItem("Resize shape");

		edit.getItems().addAll(resize_shape, edit_shape, color);
		
		scene = new Scene(root, WIDTH, HEIGHT);

        save_as = new Button("Save");
        load = new Button("Load");
        undo = new Button("Undo");
        redo = new Button("Redo");

        trash = new Button();


		Image image;
		try {
			image = new Image(new FileInputStream("src/sample/ressources/trash.png"),30,30,true, false);
			ImageView imageView = new ImageView(image);
			trash.setGraphic(imageView);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found !");
		}


	}

	public void addTrash(){
		final Pane leftSpacer = new Pane();
		VBox.setVgrow(
				leftSpacer,
				Priority.SOMETIMES
		);
		toolBar.getItems().addAll(leftSpacer, trash);
	}


	public void addMenuBar() {
		menuBar.getItems().add(save_as);
		menuBar.getItems().add(load);
		menuBar.getItems().add(undo);
		menuBar.getItems().add(redo);

		root.setTop(menuBar);
	}

	public void addCanvas() {
		/* ToolBar */
		toolBar.setOrientation(Orientation.VERTICAL);
		root.setLeft(toolBar);

		/* Canvas */
		//root.setBackground(Background.EMPTY);

	}

	public double getShapeXPositionInToolBar(Shape shape) {
		Bounds boundsInScene = shape.localToScene(shape.getBoundsInLocal());
		return boundsInScene.getMinX();
	}

	public double getShapeYPositionInToolBar(Shape shape) {
		Bounds boundsInScene = shape.localToScene(shape.getBoundsInLocal());
		return boundsInScene.getMinY();
	}

	/**
	 * @brief Get x pos of a node
	 * @param node
	 * @return x
	 */
	public double getNodeXPosition(Node node) {
		Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
		return boundsInScene.getMinX();
	}

	/**
	 * @brief Get y pos of a node
	 * @param node
	 * @return
	 */
	public double getNodeYPosition(Node node) {
		Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
		return boundsInScene.getMinY();
	}


	/**
	 * @brief Check if the point is on the Node
	 * @param node, point
	 * @return a boolean
	 */
	public boolean isOnNode(Control node, Point point){

		return point.getX() >= getNodeXPosition(node) &&
				point.getY() >= getNodeYPosition(node) &&
				point.getX() <= getNodeXPosition(node)+node.getWidth() &&
				point.getY() <= getNodeYPosition(node)+node.getHeight();
	}

	/*
	public boolean isOnTrash(Point point){

		return point.getX() >= getNodeXPosition(getTrash()) &&
				point.getY() >= getNodeYPosition(getTrash()) &&
				point.getX() <= getNodeXPosition(getTrash())+getTrash().getWidth() &&
				point.getY() <= getNodeYPosition(getTrash())+getTrash().getHeight();
	}*/

	public void onTrashInfo(Point point){
		double x2 = getNodeXPosition(getTrash())+getTrash().getWidth();
		double y2 = getNodeYPosition(getTrash())+getTrash().getHeight();
		System.out.println("Trash:\nx1,y1 ("+getNodeXPosition(getTrash())+","+getNodeYPosition(getTrash())+")");
		System.out.println("x2,y2 ("+x2+","+y2+")");
		System.out.println("Point:\nx1,x2 ("+point.getX()+","+point.getY()+")\n");
	}

	public void addShapeMenu() {
		shapeMenu.getItems().addAll(group, deGroup, edit);
	}

	public Scene getScene() {
		return scene;
	}



	public Pane getCanvas() {
		return canvas;
	}

	public ToolBar getMenuBar() {
		return menuBar;
	}

	public ToolBar getToolBar() {
		return toolBar;
	}

	public BorderPane getRoot() {
		return root;
	}

	public ContextMenu getShapeMenu() {
		return shapeMenu;
	}

	public MenuItem getGroup() {
		return group;
	}

	public MenuItem getDeGroup() {
		return deGroup;
	}

	public MenuItem getColor() {
		return color;
	}

	public ColorPicker getColorPicker() {
		return colorPicker;
	}

	public Button getSave_as() {
		return save_as;
	}

	public Button getLoad() {
		return load;
	}

	public Button getUndo() {
		return undo;
	}

	public Button getRedo() {
		return redo;
	}

	public Button getTrash() {
		return trash;
	}

	public ArrayList<Shape> getShapesInToolBar() {
		return shapesInToolBar;
	}

	public ArrayList<Shape> getShapesInCanvas() {
		return shapesInCanvas;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	/*Events*/

	public void launch_overToolbar(EventHandler<MouseEvent> event) {
		for (Shape item : shapesInCanvas) {
			item.setOnMouseReleased(event);
		}
	}

	public void launch_finalShapeToCanvas(EventHandler<MouseEvent> event) {
		for(Shape item : shapesInCanvas) {
			item.setOnMouseDragged(event);
		}
	}

	public void launch_moveShapeOnPressingMouse(EventHandler<MouseEvent> event) {
		for(Shape item : shapesInCanvas) {
			item.setOnMouseEntered(event);
		}
	}

	public void launch_getShapeOnMousePressed(EventHandler<MouseEvent> event) {
		for(Shape item : shapesInCanvas) {
			item.setOnMousePressed(event);
		}
	}

	public void launch_createShapeInToolBarOnClick(EventHandler<MouseEvent> event) {
		for(Shape item : shapesInToolBar) {
			item.setOnMouseClicked(event);
		}
	}

	/*Rectangle selection events on canvas*/

	public void launch_createSelectionRectangleOnClick(EventHandler<MouseEvent> event) {
		root.setOnMousePressed(event);
	}

	public void launch_rectangleSelectionReleased(EventHandler<MouseEvent> event) {
		root.setOnMouseReleased(event);
	}

	public void launch_selectionRectangleDraggedInCanvas(EventHandler<MouseEvent> event) {
		root.setOnMouseDragged(event);
	}

	/* Menu Item */

	public void launch_rightClick(EventHandler<ContextMenuEvent> event){
		for(Shape item : shapesInCanvas){
			item.setOnContextMenuRequested(event);
		}

	}

	public void launch_colorPickerHandler(EventHandler<ActionEvent> event) {
		colorPicker.setOnAction(event);
	}

	public void launch_groupShape(EventHandler<ActionEvent> event){
		group.setOnAction(event);
	}

	public void launch_deGroupShape(EventHandler<ActionEvent> event){
		deGroup.setOnAction(event);
	}


	/*Control clic*/

	public void launch_CTRLAndMouseOnClickShapeGroup(EventHandler<MouseEvent> event){
		for(Shape item : shapesInCanvas){
			item.setOnMouseClicked(event);
		}
	}

	/*Undo-redo events*/

	public void launch_undoEvent(EventHandler<ActionEvent> event){
		undo.setOnAction(event);
	}

	public void launch_redoEvent(EventHandler<ActionEvent> event){
		redo.setOnAction(event);
	}

	/*Save-load events*/

	public void launch_saveEvent(EventHandler<ActionEvent> event){
		save_as.setOnAction(event);
	}

	public void  launch_loadEvent(EventHandler<ActionEvent> event){
		load.setOnAction(event);
	}

	/*Edit events*/

	public void  launch_editShape(EventHandler<ActionEvent> event){
		edit_shape.setOnAction(event);
	}

	public void  launch_resizeShape(EventHandler<ActionEvent> event){
		resize_shape.setOnAction(event);
	}

}