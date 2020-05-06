package sample.View;

import javafx.scene.shape.Shape;

import java.util.ArrayList;

public interface IViewGetters {
    ArrayList<Shape> getShapesInToolBar();
    ArrayList<Shape> getShapesInCanvas();
    Object getScene();
    Object getCanvas();
    Object getMenuBar();
    Object getToolBar();
    Object getRoot();
    Object getShapeMenu();
    Object getGroup() ;
    Object getDeGroup();
    Object getColor();
    Object getColorPicker();
    Object getSave_as();
    Object getLoad();
    Object getUndo();
    Object getRedo();
    Object getTrash();
}
