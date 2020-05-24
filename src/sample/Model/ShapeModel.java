package sample.Model;

import sample.Controller.IController;
import sample.View.Drawer.IShapeDrawer;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class ShapeModel implements IShapeInter, Serializable {
	private double rotation;
    private Point pos;
    private RGB RGB;
    private double deltaX;
    private double deltaY;
    private ArrayList<IShapeInter> group;
    
	public ShapeModel(Point pos, RGB RGB) {
		super();
		this.pos = pos;
		this.RGB = RGB;
		this.deltaX = 0;
		this.deltaY = 0;
		this.group = new ArrayList<>();
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

	/**
	 * Get the position of the shapeModel
	 * @return the position
	 */
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
	public abstract IShapeDrawer createShapeDrawer(IController controller);
	public abstract ArrayList<Double> getVector();
	public abstract void setVector(ArrayList<Double> vector);
	public abstract Double[] getPoints(int n, double l);
	public abstract double getWidth();
	public abstract double getHeight();
	public abstract void setCoeff(double coeff);
	public abstract double getCoeff();

	/* Pattern composite methods*/

	@Override
	public void add(IShapeInter shapeInter){
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(IShapeInter shapeInter){
		throw new UnsupportedOperationException();
	}

	@Override
	public ArrayList<IShapeInter> getChildren(){
		return new ArrayList<>();
	}

	@Override
	public IShapeInter getChild(int n){
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDeltaX() {
		return deltaX;
	}

	@Override
	public void setDeltaX(double deltaX) {
		this.deltaX = deltaX;
	}

	@Override
	public double getDeltaY() {
		return deltaY;
	}

	@Override
	public void setDeltaY(double deltaY) {
		this.deltaY = deltaY;
	}

}
