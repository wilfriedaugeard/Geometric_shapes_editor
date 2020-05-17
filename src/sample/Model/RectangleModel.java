package sample.Model;

import sample.Controller.Controller;
import sample.Controller.ControllerJavaFX;
import sample.Factory.PointFactory;
import sample.View.IShapeDrawer;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class RectangleModel extends ShapeModel implements Serializable {
	private double width;
    private double height;
    private double arrondi;
	private Point rotationCenter;
	private ArrayList<Double> vector;

	public RectangleModel(double width, double height, Point pos, RGB rgb) {
		super(pos, rgb);
		this.width = width;
		this.height = height;
		rotationCenter = PointFactory.getPoint(0,0);
		this.arrondi = 0.0;
		vector = new ArrayList<>();
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

	public double getArrondi() {
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
	public abstract IShapeDrawer createShapeDrawer(Controller controller);

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

}


