package sample.View.Drawer;

public interface IShapeDrawer {
	/**
	 * Draw a new Shape in the canvas. Add the new Shape in the list of shapes in the canvas.
	 */
	public void drawShape();

	/**
	 * @param itemPos Index where the new Shape is added in the list of shapes in the Toolbar
	 * @param shapePos Position for the new Shape in the Toolbar
	 */
	public void drawShapeInToolBar(int itemPos, int shapePos);
}
