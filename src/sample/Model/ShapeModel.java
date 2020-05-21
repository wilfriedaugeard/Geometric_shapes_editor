package sample.Model;

import sample.Controller.Controller;
import sample.Controller.ControllerJavaFX;
import sample.View.IShapeDrawer;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class ShapeModel implements ShapeInter, Serializable {
	private double rotation;
    private Point pos;
    private RGB RGB;
    
	public ShapeModel(Point pos, RGB RGB) {
		super();
		this.pos = pos;
		this.RGB = RGB;
	}

	public ShapeModel clone() {
		try {
			return (ShapeModel) super.clone();
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

	public double getRotation() {
		return rotation;
	}
	
	public void setRotation(double rotation) {
		if(rotation < -360.0){
			this.rotation = -360.0;
		}
		if(rotation > 360.0){
			this.rotation = 360.0;
		}
		if(rotation >= -360.0 && rotation <= 360.0) {
			this.rotation = rotation;
		}
	}
	
	public RGB getRGB() {
		return RGB;
	}
	
	public void setRGB(RGB RGB) {
		this.RGB = RGB;
	}

	public abstract Point getRotationCenter();
    public abstract void setRotationCenter(Point rotationCenter);
	public abstract IShapeDrawer createShapeDrawer(Controller controller);
	public abstract ArrayList<Double> getVector();
	public abstract void setVector(ArrayList<Double> vector);
	public abstract Double[] getPoints(int n, double l);
	public abstract double getWidth();
	public abstract double getHeight();
	public abstract void setCoeff(double coeff);
	public abstract double getCoeff();

	/* Pattern composite methods*/

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
		return new ArrayList<>();
	}

	@Override
	public ShapeInter getChild(int n){
		throw new UnsupportedOperationException();
	}

}
