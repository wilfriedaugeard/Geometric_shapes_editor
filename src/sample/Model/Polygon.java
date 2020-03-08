package sample.Model;

import javafx.scene.paint.Color;
import sample.Controller.Controller;
import sample.View.PolygoneDrawerJavaFX;
import sample.View.ShapeDrawer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Polygon extends ShapeNoJavaFX {
	private int nbEdges;
    private double length;
    
	public Polygon(int nbEdges, double length, Point pos, RGB RGB) {
		super(pos, RGB);
		this.nbEdges = nbEdges;
		this.length = length;
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

	public ShapeDrawer createShapeDrawer(Controller controller) {
		RGB rgb = this.getRGB();
		int red = rgb.getR();
		int blue = rgb.getB();
		int green = rgb.getG();
		Double[] points = this.getPoints(this.getNbEdges(), this.getLength());

		return new PolygoneDrawerJavaFX(points, controller.getView(), Color.rgb(red, green, blue));
	}
    
    
}
