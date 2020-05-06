package sample.View;

import javafx.scene.paint.Color;

public abstract class ShapeDrawerJavaFX implements IShapeDrawer {
	protected ViewJavaFXAdaptee view;
	protected Color color;
	
	public ShapeDrawerJavaFX(ViewJavaFXAdaptee view, Color color) {
		this.view = view;
		this.color = color;
	}
			
	public abstract void drawShape();
	public abstract void drawShapeInToolBar();
	
}
