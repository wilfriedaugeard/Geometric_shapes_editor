package sample.Model;

import sample.Controller.Controller;
import sample.View.PolygoneDrawerJavaFX;
import sample.View.ShapeDrawer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Polygon extends ShapeNoJavaFX {
	private int nbEdges;
    private double length;
	private Point rotationCenter;

	public Polygon(int nbEdges, double length, Point pos, RGB rgb) {
		super(pos, rgb);
		this.nbEdges = nbEdges;
		this.length = length;
		rotationCenter = new Point();
	}

	public int getNbEdges() {
		return nbEdges;
	}

	public void setNbEdges(int nbEdges) {
		this.nbEdges = nbEdges;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

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

		// Calcul des coordonées des points
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

	public ShapeDrawer createShapeDrawer(Controller controller) {
		RGB rgb = this.getRGB();
		double red = rgb.getR();
		double green = rgb.getG();
		double blue = rgb.getB();

		Double[] rgbTab = new Double[3];
		rgbTab[0] = red; rgbTab[1] = green; rgbTab[2] = blue;

		Double[] points = this.getPoints(this.getNbEdges(), this.getLength());

		return new PolygoneDrawerJavaFX(points, controller.getView(), rgbTab);
	}
    
    
}
