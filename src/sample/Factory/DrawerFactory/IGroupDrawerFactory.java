package sample.Factory.DrawerFactory;

import sample.View.Drawer.IShapeDrawer;
import sample.View.IView;

import java.util.ArrayList;

/**
 * Group drawer factory
 */
public interface IGroupDrawerFactory {
    public IShapeDrawer createGroupDrawer(IView view, double x, double y, double width, double height, ArrayList<IShapeDrawer> shapeDrawerList);
}
