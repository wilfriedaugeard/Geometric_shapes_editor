package sample.Model;

import java.io.Serializable;

public class Point implements Serializable {

	    private double x;
	    private double y;

	    public Point() {
	           x = 0;
	           y = 0;
	    }
	    
	    public Point(double x, double y) {
	    	this.x = x;
	    	this.y = y;
	    }

	    public double getX() { 
	    	return x; 
	    }
	    public void setX(double n) {
	    	this.x = n; 
	    }

	    public double getY() { 
	    	return y; 
	    }
	    public void setY(double n) { 
	    	this.y = n; 
	    }

}
