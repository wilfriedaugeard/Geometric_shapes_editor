package sample.Factory;

import sample.View.IShapeDrawer;
import sample.View.IView;

import java.util.ArrayList;

public interface IGroupDrawerFactory {
    public IShapeDrawer createGroupDrawer(IView view, double x, double y, double width, double height, ArrayList<IShapeDrawer> shapeDrawerList);
}
