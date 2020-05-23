package sample.Model;

import sample.Controller.IController;
import sample.Factory.PointFactory;
import sample.View.Drawer.IShapeDrawer;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public abstract class PolygonModel extends ShapeModel implements Serializable {
	private int nbEdges;
    private double length;
	private Point rotationCenter;
	private ArrayList<Double> vector;
	private ArrayList<Double> tmpWidth;
	private double coeff;


	public PolygonModel(int nbEdges, double length, Point pos, RGB rgb) {
		super(pos, rgb);
		this.nbEdges = nbEdges;
		this.length = length;
		rotationCenter = PointFactory.getPoint(0,0);
		vector = new ArrayList<>();
		this.tmpWidth = getVector();
		this.coeff = 1;
	}

	/**
	 * @return The number of edges of the polygon
	 */
	public int getNbEdges() {
		return nbEdges;
	}

	/**
	 * @param nbEdges Value to replace the current number of edges of the polygon
	 */
	public void setNbEdges(int nbEdges) {
		this.nbEdges = nbEdges;
	}

	/**
	 * @return The length of the polygon
	 */
	public double getLength() {
		return length;
	}

	/**
	 * @param length Value to replace the current length of the polygon
	 */
	public void setLength(double length) {
		this.length = length;
	}

	@Override
	public Double[] getPoints(int n, double l) {
		Double[] tmp = new Double[n*2];
		if(this.getPos() == null) {
			tmp[0] = 0.0;
			tmp[1] = 0.0;
		}
		else {
			tmp[0] = this.getPos().getX(); tmp[1] = this.getPos().getY();
		}

		// angle intérieur en radian
		double angle =  (n - 2) * 3.14 / n;

		// Calcul des coordonnées des points
		for (int i = 2; i < n * 2; i+=2) {
			double tmp_angle = angle - ((i/2 * 360/n) * 3.14 / 180);
			tmp[i] = tmp[i - 2] + l * cos(tmp_angle);
			tmp[i+1] = tmp[i - 1] + l * sin(tmp_angle);
		}

		return tmp;
	}

	@Override
	public Point getRotationCenter() {
		Double[] points = this.getPoints(getNbEdges(),getLength());
		double sumX = 0.0; double sumY = 0.0;
		for(int i = 0; i < getNbEdges()*2; i+=2){
			sumX += points[i];
		}
		for(int j = 1; j < getNbEdges()*2; j+=2){
			sumY += points[j];
		}
		double CenterX = sumX/getNbEdges();
		double CenterY = sumY/getNbEdges();
		rotationCenter.setX(CenterX);
		rotationCenter.setY(CenterY);
		return rotationCenter;
	}

	@Override
	public void setRotationCenter(Point center) {
		this.rotationCenter.setX(center.getX());
		this.rotationCenter.setY(center.getY());
	}

	public abstract IShapeDrawer createShapeDrawer(IController controller);

	@Override
	public ArrayList<Double> getVector(){
		vector.clear();
		vector.add(length);
		return vector;
	}

	@Override
	public void setVector(ArrayList<Double> vector){
		if(vector.size() == 1){
			length = vector.get(0);
		}
	}
	@Override
	public double getWidth(){
		double rayon = this.length/(2*Math.sin(Math.PI/this.nbEdges));
		return 2*rayon;
	}

	@Override
	public double getHeight() {
		return getWidth();
	}

	@Override
	public void setCoeff(double coeff) {
		this.coeff = coeff;
	}

	@Override
	public double getCoeff() {
		return this.coeff;
	}

}
