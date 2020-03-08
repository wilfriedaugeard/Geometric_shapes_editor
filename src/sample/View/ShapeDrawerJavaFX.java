package sample.View;

import javafx.scene.paint.Color;

public abstract class ShapeDrawerJavaFX implements ShapeDrawer {
	protected View view;
	protected Color color;
	
	public ShapeDrawerJavaFX(View view, Color color) {
		this.view = view;
		this.color = color;
	}
			
	public abstract void drawShape();
	public abstract void drawShapeInToolBar();
	
}
