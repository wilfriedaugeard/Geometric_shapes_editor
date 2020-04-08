package sample.View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PolygoneDrawerJavaFX extends ShapeDrawerJavaFX  {
	private Double[] points;
	
	public PolygoneDrawerJavaFX(Double[] points, View view, Double[] rgb) {
		super(view, Color.color(rgb[0],rgb[1],rgb[2]));
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
