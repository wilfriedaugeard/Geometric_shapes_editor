package sample.Model;

import sample.Controller.Controller;
import sample.View.ShapeDrawer;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class ShapeNoJavaFX implements ShapeInter, Serializable {
	private int rotation;
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
	
	public RGB getRGB() {
		return RGB;
	}
	
	public void setRGB(RGB RGB) {
		this.RGB = RGB;
	}

	public abstract Point getRotationCenter();
    public abstract void setRotationCenter(Point rotationCenter);
	public abstract ShapeDrawer createShapeDrawer(Controller controller);

	@Override
	public void add(ShapeInter shapeInter){
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(ShapeInter shapeInter){
		throw new UnsupportedOperationException();
	}

	@Override
	public ArrayList<ShapeInter> getChildren(){
		throw new UnsupportedOperationException();
	}

	@Override
	public ShapeInter getChild(int n){
		throw new UnsupportedOperationException();
	}

}
