package sample.View.Drawer;

import javafx.scene.paint.Color;
import sample.View.IView;

public abstract class ShapeDrawerJavaFX implements IShapeDrawer {
	protected IView view;
	protected Color color;
	
	public ShapeDrawerJavaFX(IView view, Color color) {
		this.view = view;
		this.color = color;
	}

	public abstract void drawShape();
	public abstract void drawShapeInToolBar(int itemPos, int shapePos);
	
}
