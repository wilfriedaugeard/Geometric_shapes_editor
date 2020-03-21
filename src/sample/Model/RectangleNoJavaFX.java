package sample.Model;

import javafx.scene.paint.Color;
import sample.Controller.Controller;
import sample.View.RectangleDrawerJavaFX;
import sample.View.ShapeDrawer;

public class RectangleNoJavaFX extends ShapeNoJavaFX {
	private double width;
    private double height;
    private int arrondi;
	private Point rotationCenter;

	public RectangleNoJavaFX(double width, double height, Point pos, RGB rgb) {
		super(pos, rgb);
		this.width = width;
		this.height = height;
		rotationCenter = new Point();
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getArrondi() {
		return arrondi;
	}

	public void setArrondi(int arrondi) {
		this.arrondi = arrondi;
	}


	@Override
	public Point getRotationCenter() {
		double CenterX = getPos().getX() + width/2;
		double CenterY = getPos().getY() + height/2;
		rotationCenter.setX(CenterX);
		rotationCenter.setY(CenterY);
		return rotationCenter;
	}

	@Override
	public void setRotationCenter(Point center) {
		this.rotationCenter.setX(center.getX());
		this.rotationCenter.setY(center.getY());
	}

	@Override
	public ShapeDrawer createShapeDrawer(Controller controller) {
		RGB rgb = this.getRGB();
		int red = rgb.getR();
		int blue = rgb.getB();
		int green = rgb.getG();

		return new RectangleDrawerJavaFX(this.getPos().getX(), this.getPos().getY(), this.getWidth(), this.getHeight(),
				Color.rgb(red, green, blue),  controller.getView());
	}

}


