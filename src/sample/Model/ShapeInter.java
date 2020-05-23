package sample.Model;

import sample.Controller.Controller;
import sample.View.Drawer.IShapeDrawer;

import java.util.ArrayList;

public interface ShapeInter extends Cloneable {

    /**
     * @return The clone of the ShapeInter
     */
    public ShapeInter clone();

    /* Pattern Composite methods */

    /**
     * Works only for a ShapeGroup. Throws an exception otherwise.
     * @param shapeInter The ShapeInter to add in the children's list
     */
    void add(ShapeInter shapeInter);

    /**
     * Works only for a ShapeGroup. Throws an exception otherwise.
     * @param shapeInter The ShapeInter to remove in the children's list
     */
    void remove(ShapeInter shapeInter);

    /**
     * Works only for a ShapeGroup. Throws an exception otherwise.
     * @return The children of a ShapeInter group
     */
    ArrayList<ShapeInter> getChildren();

    /**
     * Works only for a ShapeGroup. Throws an exception otherwise.
     * @param n Index in the list of children
     * @return The ShapeInter child at index n
     */
    ShapeInter getChild(int n);

    /* Bridge */

    /**
     * @param controller Controller used to get the View
     * @return An object IShapeDrawer
     */
    public IShapeDrawer createShapeDrawer(Controller controller);

    /* Simple methods */

    /**
     * @return An object Point, with x and y positions of the ShapeInter
     */
    public Point getPos();

    /**
     * @param p Point object to replace the current position
     */
    public void setPos(Point p);

    /**
     * @param dx Value used to modify x position
     * @param dy Value used to modify y position
     */
    public void translate(double dx, double dy);

    /**
     * @return The current value of rotation
     */
    public double getRotation();

    /**
     * @param r Value used for replacing current value of rotation
     */
    public void setRotation(double r);

    /**
     * @return The rotation center of the ShapeInter
     */
    public Point getRotationCenter();

    /**
     * @param p Point object to replace the current rotation center
     */
    public void setRotationCenter(Point p);

    /**
     * @return The object RGB used for the colors in the ShapeInter
     */
    public RGB getRGB();

    /**
     * @param rgb RGB object to replace the current RGB in the ShapeInter
     */
    public void setRGB(RGB rgb);

    /**
     * @return The information about the size of the ShapeInter
     */
    public ArrayList<Double> getVector();

    /**
     * @param vector Vector to replace the current size values
     */
    public void setVector(ArrayList<Double> vector);

    /**
     * Throws an exception if the ShapeInter is not a polygon.
     * @param n The number of edges
     * @param l The value of the length
     * @return A list of points only for the particular shape polygon
     */
    public Double[] getPoints(int n, double l);

    /**
     * @return The width of the ShapeInter
     */
    public double getWidth();

    /**
     * @return The height of the ShapeInter
     */
    public double getHeight();

    /**
     * @param coeff The coefficient size of the ShapeInter
     */
    public void setCoeff(double coeff);

    /**
     * @return The coefficient size of the ShapeInter
     */
    public double getCoeff();

    public double getDeltaX();

    public void setDeltaX(double d);

    public double getDeltaY();

    public void setDeltaY(double d);
}
