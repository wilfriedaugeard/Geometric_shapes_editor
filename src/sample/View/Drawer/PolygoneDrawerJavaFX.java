package sample.View.Drawer;

import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import sample.Factory.ModelFactory.PointFactory;
import sample.Model.Point;
import sample.View.IView;

public class PolygoneDrawerJavaFX extends ShapeDrawerJavaFX {
	private Double[] points;
	private double rotation;
	
	public PolygoneDrawerJavaFX(Double[] points, IView view, int[] rgb, double rotation) {
		super(view, Color.rgb(rgb[0],rgb[1],rgb[2], rgb[3]));
		this.points = points;
		this.rotation = rotation;
	}

	/**
	 * @param polygon Polygon to rotate
	 */
	private void rotatePolygon(Polygon polygon){
		Point oldPt = PointFactory.getPoint(view.getXPosition(polygon), view.getYPosition(polygon));
		Rotate rotateTransform = new Rotate(rotation);
		polygon.getTransforms().add(rotateTransform);
		polygon.setTranslateX(polygon.getTranslateX() + (oldPt.getX() - view.getXPosition(polygon)));
		polygon.setTranslateY(polygon.getTranslateY() + (oldPt.getY() - view.getYPosition(polygon)));
	}

	@Override
	public void drawShape() {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(points);
		polygon.setFill(color);
		rotatePolygon(polygon);
		BorderPane bp = (BorderPane) view.getRoot();
		bp.getChildren().add(polygon);
		view.getShapesInCanvas().add(polygon);
	}

	@Override
	public void drawShapeInToolBar(int itemPos, int shapePos) {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(points);
		polygon.setFill(color);
		rotatePolygon(polygon);
		ToolBar toolBar = (ToolBar) view.getToolBar();
		toolBar.getItems().add(itemPos, polygon);
		view.getShapesInToolBar().add(shapePos, polygon);
	}

}
