package sample.View;

import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import javax.tools.Tool;

public class PolygoneDrawerJavaFX extends ShapeDrawerJavaFX  {
	private Double[] points;
	
	public PolygoneDrawerJavaFX(Double[] points, IView view, int[] rgb) {
		super(view, Color.rgb(rgb[0],rgb[1],rgb[2]));
		this.points = points;
	}

	@Override
	public void drawShape() {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(points);
		polygon.setFill(color);
		BorderPane bp = (BorderPane) view.getRoot();
		bp.getChildren().add(polygon);
		view.getShapesInCanvas().add(polygon);
	}

	@Override
	public void drawShapeInToolBar() {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(points);
		polygon.setFill(color);
		ToolBar toolBar = (ToolBar) view.getToolBar();
		toolBar.getItems().add(polygon);
		view.getShapesInToolBar().add(polygon);
	}

}
