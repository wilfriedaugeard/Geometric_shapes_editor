package sample.Controller;

import sample.Controller.Command.ICommand;
import sample.Controller.Events.Event;
import sample.Model.IShapeInter;
import sample.View.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public interface IController extends Serializable {

    /*Init and update for View*/
    public void initializeView();
    public void updateViewColor();
    public void updateViewTranslate(IShapeInter shape, double dragX, double dragY, boolean isShapeGroup);
    public void removeShape(IShapeInter shapeModel, Object shapeView); //ToAdapt
    public void updateViewRemove(IShapeInter shape);
    public void updateViewRotate(IShapeInter shape, double value, boolean isShapeGroup);
    public void updateViewResize(IShapeInter shape);
    public void addShapeInToolbar(IShapeInter shape, IController controller, int itemPos, int shapePos);

    /*Events*/
    public void initEvents();
    public void updateEvents();
    public ArrayList<Event> getEvents();

    /*Pattern Composite*/
    public IShapeInter getShapeGroupTmp();
    public void setShapeGroupTmp(IShapeInter shapeGroupTmp);

    /*View Links*/
    public Object getScene(); //ToAdapt
    public IView getView(); //ToAdapt

    /*Get shapes*/
    public ArrayList<IShapeInter> getShapesInToolBar();
    public ArrayList<IShapeInter> getShapesInCanvas();
    public ArrayList<IShapeInter> getShapeGroups();
    public ArrayList<IShapeInter> getShapeGroupsInToolBar();

    /*Pattern Command*/
    public LinkedList<ICommand> getCommands();
    public void addLastCommand(ICommand command);
    public int getNbCommands();
    public int getCurrentPosInCommands();
    public void setCurrentPosInCommands(int currentPosInCommands);

    /* Controller state */
    public void saveState();
    public void loadState(IController controller);
    public boolean isExistState();
    public void setExistState(boolean existState);
}
