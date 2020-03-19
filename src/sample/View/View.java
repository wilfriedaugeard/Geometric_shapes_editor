package sample.View;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

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


public class View implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Pane canvas;
	private ToolBar menuBar;
	private ToolBar toolBar;
	private Scene scene;
	private BorderPane root;
	
	/* Shape Menu on right click */
	private ContextMenu shapeMenu;
	private MenuItem group;
	private MenuItem deGroup;
	private MenuItem edit;
	private ColorPicker colorPicker;

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
	private final static int WIDTH = 600;

	/**
	 * Height of the window
	 */
	private final static int HEIGHT = 600;

	public View() {
		canvas = new Pane();
		menuBar = new ToolBar();

		toolBar = new ToolBar();
		root = new BorderPane();

		
		shapesInToolBar = new ArrayList<Shape>();
		shapesInCanvas = new ArrayList<Shape>();

		shapeMenu = new ContextMenu();
		group = new MenuItem("Group");
		deGroup = new MenuItem("De-group");
		colorPicker = new ColorPicker();
		edit = new MenuItem("Edit Color", colorPicker);
		
		scene = new Scene(root, WIDTH, HEIGHT);

        save_as = new Button("Save");
        load = new Button("Load");
        undo = new Button("Undo");
        redo = new Button("Redo");

        trash = new Button();
		//System.out.println(System.getProperty("user.dir"));

		Image image;
		try {
			image = new Image(new FileInputStream("src/sample/ressources/trash.png"),30,30,true, false);
			ImageView imageView = new ImageView(image);
			trash.setGraphic(imageView);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found !");
		}


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
		root.setRight(canvas);
		root.setBackground(Background.EMPTY);

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
	 * @brief Check if the point is on the Trash icon
	 * @param point
	 * @return a boolean
	 */
	public boolean isOnTrash(Point point){

		return point.getX() >= getNodeXPosition(getTrash()) &&
				point.getY() >= getNodeYPosition(getTrash()) &&
				point.getX() <= getNodeXPosition(getTrash())+getTrash().getWidth() &&
				point.getY() <= getNodeYPosition(getTrash())+getTrash().getHeight();
	}

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

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public MenuItem getEdit() {
		return edit;
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

	/*Events*/

	public void launch_overTrash(EventHandler<MouseEvent> event) {
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

}
