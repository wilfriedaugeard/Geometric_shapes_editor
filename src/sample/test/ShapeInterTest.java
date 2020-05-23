package sample.test;

import sample.Controller.IController;
import sample.Factory.IControllerFactory;
import sample.Factory.ControllerJavaFXFactory;
import sample.Factory.PointFactory;
import sample.Model.*;
import sample.Model.Decorator.BornedRotate;
import sample.Model.Decorator.DecoratorShapeInter;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IShapeInterTest {

    @org.junit.jupiter.api.Test
    void testClone() {
        IShapeInter rec1 = new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(247, 220, 111));
        IShapeInter poly1 = new PolygonJavaFX(5, 25, new Point(0, 20), new RGB(133, 193, 233));

        IShapeInter cloneRec1 = rec1.clone();
        IShapeInter clonePoly1 = poly1.clone();

        //Checking references
        assertNotEquals(rec1, cloneRec1);
        assertNotEquals(poly1, clonePoly1);

        //Checking pos
        assertEquals(rec1.getPos().getX(), cloneRec1.getPos().getX());
        assertEquals(poly1.getPos().getX(), clonePoly1.getPos().getX());
        assertEquals(rec1.getPos().getY(), cloneRec1.getPos().getY());
        assertEquals(poly1.getPos().getY(), clonePoly1.getPos().getY());

        //Checking size
        assertEquals(rec1.getVector().get(0), cloneRec1.getVector().get(0));
        assertEquals(rec1.getVector().get(1), cloneRec1.getVector().get(1));
        assertEquals(poly1.getVector().get(0), clonePoly1.getVector().get(0));

        //Checking RGB
        assertEquals(rec1.getRGB().getR(), cloneRec1.getRGB().getR());
        assertEquals(rec1.getRGB().getG(), cloneRec1.getRGB().getG());
        assertEquals(rec1.getRGB().getB(), cloneRec1.getRGB().getB());
        assertEquals(poly1.getRGB().getR(), clonePoly1.getRGB().getR());
        assertEquals(poly1.getRGB().getG(), clonePoly1.getRGB().getG());
        assertEquals(poly1.getRGB().getB(), clonePoly1.getRGB().getB());
    }

    @org.junit.jupiter.api.Test
    void add() {
        IShapeInter shapeGroup = new ShapeGroup();
        IShapeInter rec1 = new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(247, 220, 111));

        shapeGroup.add(rec1);

        //Should work on shape groups
        assertEquals(rec1, shapeGroup.getChild(0));

        IShapeInter poly1 = new PolygonJavaFX(5, 25, new Point(0, 20), new RGB(133, 193, 233));

        //Should not work because it's not a shape group
        assertThrows(UnsupportedOperationException.class, () -> rec1.add(poly1));

    }

    @org.junit.jupiter.api.Test
    void remove() {
        IShapeInter shapeGroup = new ShapeGroup();
        IShapeInter rec1 = new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(247, 220, 111));

        shapeGroup.add(rec1);

        //Should work on shape groups
        shapeGroup.remove(rec1);
        assertEquals(0, shapeGroup.getChildren().size());

        IShapeInter poly1 = new PolygonJavaFX(5, 25, new Point(0, 20), new RGB(133, 193, 233));

        //Should not work because it's not a shape group
        assertThrows(UnsupportedOperationException.class, () -> rec1.remove(poly1));
    }

    @org.junit.jupiter.api.Test
    void getChildren() {
        IShapeInter shapeGroup = new ShapeGroup();
        IShapeInter rec1 = new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(247, 220, 111));

        shapeGroup.add(rec1);

        ArrayList<IShapeInter> testList = new ArrayList<>();
        testList.add(rec1);

        //Should work on shape groups
        assertEquals(testList.get(0), shapeGroup.getChildren().get(0));

        IShapeInter poly1 = new PolygonJavaFX(5, 25, new Point(0, 20), new RGB(133, 193, 233));

        assert(poly1.getChildren() != null);
        assertEquals(0, poly1.getChildren().size());
    }

    @org.junit.jupiter.api.Test
    void getChild() {
        IShapeInter shapeGroup = new ShapeGroup();
        IShapeInter rec1 = new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(247, 220, 111));

        shapeGroup.add(rec1);

        //Should work on shape groups
        assertEquals(rec1, shapeGroup.getChild(0));

        IShapeInter poly1 = new PolygonJavaFX(5, 25, new Point(0, 20), new RGB(133, 193, 233));

        //Should not work because it's not a shape group
        assertThrows(UnsupportedOperationException.class, () -> poly1.getChild(0));

    }

    @org.junit.jupiter.api.Test
    void translate() {
        IShapeInter rec1 = new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(247, 220, 111));
        rec1.translate(20, 20);
        assertEquals(20, rec1.getPos().getX());
        assertEquals(20, rec1.getPos().getY());
    }

    @org.junit.jupiter.api.Test
    void getAndSetRotation() {
        IShapeInter rec1 = new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(247, 220, 111));
        rec1.setRotation(20);
        assertEquals(20, rec1.getRotation());

        rec1.setRotation(-20);
        assertEquals(-20, rec1.getRotation());

        rec1.setRotation(4500);
        assertEquals(4500, rec1.getRotation());

        rec1.setRotation(-4500);
        assertEquals(-4500, rec1.getRotation());

        // Borned rotation
        DecoratorShapeInter rec2 = new BornedRotate(new RectangleJavaFX(50, 25, new Point(0, 0), new RGB(247, 220, 111)));
        rec2.setRotation(20);
        assertEquals(20, rec2.getRotation());

        rec2.setRotation(-20);
        assertEquals(-20, rec2.getRotation());

        rec2.setRotation(4500);
        assertEquals(360, rec2.getRotation());

        rec2.setRotation(-4500);
        assertEquals(-360, rec2.getRotation());
    }

}