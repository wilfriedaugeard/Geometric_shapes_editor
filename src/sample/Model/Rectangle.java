package sample.Model;

import javafx.scene.paint.Color;
import sample.Controller.Controller;
import sample.View.RectangleDrawerJavaFX;
import sample.View.ShapeDrawer;

public class Rectangle extends ShapeNoJavaFX {
	private double width;
    private double height;
    private int arrondi;
    
	public Rectangle(double width, double height, Point pos, RGB RGB) {
		super(pos, RGB);
		this.width = width;
		this.height = height;
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


	public ShapeDrawer createShapeDrawer(Controller controller) {
		RGB rgb = this.getRGB();
		int red = rgb.getR();
		int blue = rgb.getB();
		int green = rgb.getG();

		return new RectangleDrawerJavaFX(this.getPos().getX(), this.getPos().getY(), this.getWidth(), this.getHeight(),
				Color.rgb(red, green, blue),  controller.getView());
	}

}


