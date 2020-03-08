package sample.View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleDrawerJavaFX extends ShapeDrawerJavaFX  {
	private double x, y, width, height;
	
	public RectangleDrawerJavaFX(double x, double y, double width, double height, Color color, View view) {
		super(view, color);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void drawShape() {
		Rectangle rectangle = new Rectangle(x, y, width, height);
		rectangle.setFill(color);
		
		view.getRoot().getChildren().add(rectangle);
		view.getShapesInCanvas().add(rectangle);
	}

	@Override
	public void drawShapeInToolBar() {
		Rectangle rectangle = new Rectangle(x, y, width, height);
		rectangle.setFill(color);
		
		view.getToolBar().getItems().add(rectangle);
		view.getShapesInToolBar().add(rectangle);
		
	}
}
