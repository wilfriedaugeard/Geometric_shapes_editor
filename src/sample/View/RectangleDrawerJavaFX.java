package sample.View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleDrawerJavaFX extends ShapeDrawerJavaFX  {
	private double x, y, width, height;
	private double arrondi;
	
	public RectangleDrawerJavaFX(double x, double y, double width, double height, Double[] rgb, View view, double arrondi) {
		super(view, Color.color(rgb[0],rgb[1],rgb[2]));
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
		
		view.getRoot().getChildren().add(rectangle);
		view.getShapesInCanvas().add(rectangle);
	}

	@Override
	public void drawShapeInToolBar() {
		Rectangle rectangle = new Rectangle(x, y, width, height);
		rectangle.setFill(color);
		rectangle.setArcHeight(arrondi);
		rectangle.setArcWidth(arrondi);
		
		view.getToolBar().getItems().add(rectangle);
		view.getShapesInToolBar().add(rectangle);
		
	}
}
