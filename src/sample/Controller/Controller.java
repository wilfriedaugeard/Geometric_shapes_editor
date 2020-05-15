package sample.Controller;

import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import sample.Model.ShapeInter;
import sample.View.IView;
import sample.View.ViewJavaFXAdaptee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public interface Controller extends Serializable {

    /*Init and update for View*/
    public void initializeView();
    public void updateViewColor();
    public void updateViewTranslate(ShapeInter shape, double dragX, double dragY, boolean isShapeGroup);
    public void removeShape(ShapeInter shapeModel, Object shapeView); //ToAdapt
    public void updateViewRemove(ShapeInter shape);
    public void updateViewRotate(ShapeInter shape, double value, boolean isShapeGroup);

    /*Events*/
    public void initEvents();
    public void updateEvents();
    public ArrayList<Events> getEvents();

    /*Pattern Composite*/
    public ShapeInter getShapeGroupTmp();
    public void setShapeGroupTmp(ShapeInter shapeGroupTmp);

    /*View Links*/
    public Object getScene(); //ToAdapt
    public IView getView(); //ToAdapt

    /*Get shapes*/
    public ArrayList<ShapeInter> getShapesInToolBar();
    public ArrayList<ShapeInter> getShapesInCanvas();
    public ArrayList<ShapeInter> getShapeGroups();

    /*Pattern Command*/
    public LinkedList<Command> getCommands();
    public void addLastCommand(Command command);
    public int getNbCommands();
    public int getCurrentPosInCommands();
    public void setCurrentPosInCommands(int currentPosInCommands);

}
