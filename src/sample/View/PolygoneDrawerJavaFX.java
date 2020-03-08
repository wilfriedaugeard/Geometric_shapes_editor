package sample.View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PolygoneDrawerJavaFX extends ShapeDrawerJavaFX  {
	private Double[] points;
	
	public PolygoneDrawerJavaFX(Double[] points, View view, Color color) {
		super(view, color);
		this.points = points;
	}

	@Override
	public void drawShape() {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(points);
		polygon.setFill(color);
		
		view.getRoot().getChildren().add(polygon);
		view.getShapesInCanvas().add(polygon);
	}

	@Override
	public void drawShapeInToolBar() {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(points);
		polygon.setFill(color);
		
		view.getToolBar().getItems().add(polygon);
		view.getShapesInToolBar().add(polygon);
	}

}
