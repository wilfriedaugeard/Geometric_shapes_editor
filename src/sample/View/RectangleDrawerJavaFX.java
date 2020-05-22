package sample.View;

import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import sample.Factory.PointFactory;
import sample.Model.Point;

public class RectangleDrawerJavaFX extends ShapeDrawerJavaFX  {
	private double x, y, width, height;
	private double arrondi;
	private double rotation;
	
	public RectangleDrawerJavaFX(double x, double y, double width, double height, int[] rgb, IView view, double arrondi, double rotation) {
		super(view, Color.rgb(rgb[0],rgb[1],rgb[2], rgb[3]));
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.arrondi = arrondi;
		this.rotation = rotation;
	}

	private void rotateRectangle(Rectangle rectangle){
		Point oldPt = PointFactory.getPoint(view.getXPosition(rectangle), view.getYPosition(rectangle));
		Rotate rotateTransform = new Rotate(rotation);
		rectangle.getTransforms().add(rotateTransform);
		rectangle.setTranslateX(rectangle.getTranslateX() + (oldPt.getX() - view.getXPosition(rectangle)));
		rectangle.setTranslateY(rectangle.getTranslateY() + (oldPt.getY() - view.getYPosition(rectangle)));
	}

	@Override
	public void drawShape() {
		Rectangle rectangle = new Rectangle(x, y, width, height);
		rectangle.setFill(color);
		rectangle.setArcHeight(arrondi);
		rectangle.setArcWidth(arrondi);
		rotateRectangle(rectangle);
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
		rotateRectangle(rectangle);
		ToolBar toolBar = (ToolBar) view.getToolBar();
		toolBar.getItems().add(itemPos,rectangle);
		view.getShapesInToolBar().add(shapePos, rectangle);
		
	}
}
