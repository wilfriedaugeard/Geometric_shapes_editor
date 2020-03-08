package sample.Model;

import sample.Controller.Controller;
import sample.View.ShapeDrawer;

public abstract class ShapeNoJavaFX implements ShapeInter{
	private int rotation;
    private Point rotationCenter;
    private Point translationCenter;
    private Point pos;
    private RGB RGB;
    
	public ShapeNoJavaFX(Point pos, RGB RGB) {
		super();
		this.pos = pos;
		this.RGB = RGB;
	}

	public ShapeNoJavaFX clone() {
		try {
			return (ShapeNoJavaFX) super.clone();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return this;
	}
	
	public void translate(double dx, double dy) {
        pos.setX(pos.getX() + dx);
        pos.setY(pos.getY() + dy);
    }
	
	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public int getRotation() {
		return rotation;
	}
	
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public Point getRotationCenter() {
		return rotationCenter;
	}
	
	public void setRotationCenter(Point rotationCenter) {
		this.rotationCenter = rotationCenter;
	}
	
	public Point getTranslationCenter() {
		return translationCenter;
	}
	
	public void setTranslationCenter(Point translationCenter) {
		this.translationCenter = translationCenter;
	}
	
	public RGB getRGB() {
		return RGB;
	}
	
	public void setRGB(RGB RGB) {
		this.RGB = RGB;
	}


	public abstract ShapeDrawer createShapeDrawer(Controller controller);

}
