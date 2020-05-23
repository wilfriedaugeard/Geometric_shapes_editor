package sample.Factory.DrawerFactory;

import sample.View.Drawer.GroupDrawerJavaFX;
import sample.View.Drawer.IShapeDrawer;
import sample.View.IView;

import java.util.ArrayList;

public class GroupDrawerJavaFXFactory implements IGroupDrawerFactory{

    @Override
    public IShapeDrawer createGroupDrawer(IView view, double x, double y, double width, double height, ArrayList<IShapeDrawer> shapeDrawerList) {
        return new GroupDrawerJavaFX(view,x,y,width,height,shapeDrawerList);
    }
}
