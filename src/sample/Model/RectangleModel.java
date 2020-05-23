package sample.Model;

import sample.Controller.IController;
import sample.Factory.PointFactory;
import sample.View.Drawer.IShapeDrawer;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class RectangleModel extends ShapeModel implements Serializable {
	private double width;
    private double height;
    private double arrondi;
	private Point rotationCenter;
	private ArrayList<Double> vector;
	private ArrayList<Double> tmpWidth;
	private double coeff;
	private double deltaX;
	private double deltaY;

	public RectangleModel(double width, double height, Point pos, RGB rgb) {
		super(pos, rgb);
		this.width = width;
		this.height = height;
		rotationCenter = PointFactory.getPoint(0,0);
		this.arrondi = 0.0;
		vector = new ArrayList<>();
		this.tmpWidth = getVector();
		this.coeff = 1;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getArrondi() {
		return arrondi;
	}

	public void setArrondi(int arrondi) {
		this.arrondi = arrondi;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
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
	public abstract IShapeDrawer createShapeDrawer(IController controller);

	@Override
	public ArrayList<Double> getVector(){
		vector.clear();
		vector.add(width);
		vector.add(height);
		return vector;
	}

	@Override
	public void setVector(ArrayList<Double> vector){
		if(vector.size() == 2){
			width = vector.get(0);
			height = vector.get(1);
		}
	}

	@Override
	public Double[] getPoints(int n, double l){
		throw new UnsupportedOperationException();
	}

	@Override
	public double getCoeff() {
		return coeff;
	}

	@Override
	public void setCoeff(double coeff) {
		this.coeff = coeff;
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


