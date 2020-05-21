package sample.View;

import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleDrawerJavaFX extends ShapeDrawerJavaFX  {
	private double x, y, width, height;
	private double arrondi;
	
	public RectangleDrawerJavaFX(double x, double y, double width, double height, int[] rgb, IView view, double arrondi) {
		super(view, Color.rgb(rgb[0],rgb[1],rgb[2]));
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.arrondi = arrondi;
	}

	@Override
	public void drawShape() {
		Rectangle rectangle = new Rectangle(x, y, width, height);
		rectangle.setFill(color);
		rectangle.setArcHeight(arrondi);
		rectangle.setArcWidth(arrondi);
		
		BorderPane bp = (BorderPane) view.getRoot();
		bp.getChildren().add(rectangle);
		view.getShapesInCanvas().add(rectangle);
	}

	@Override
	public void drawShapeInToolBar(int itemPos, int shapePos) {
		Rectangle rectangle = new Rectangle(x, y, width, height);
		rectangle.setFill(color);
		rectangle.setArcHeight(arrondi);
		rectangle.setArcWidth(arrondi);
		ToolBar toolBar = (ToolBar) view.getToolBar();
		toolBar.getItems().add(itemPos,rectangle);
		view.getShapesInToolBar().add(shapePos, rectangle);
		
	}
}
