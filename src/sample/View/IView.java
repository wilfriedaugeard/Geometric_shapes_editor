package sample.View;
import sample.Model.Point;

public interface IView {
    /* UI */
    void addMenuBar();
    void addCanvas();
    void addTrash();
    void addShapeMenu();

    /* Position */
    double getShapeXPositionInToolBar(Object shape);
    double getShapeYPositionInToolBar(Object shape);
    double getXPosition(Object node);
    double getYPosition(Object node);
    boolean isOn(Object node, Point point);

    /* Events */
    void launch_overToolbar(Object event);
    void launch_finalShapeToCanvas(Object event);
    void launch_moveShapeOnPressingMouse(Object event);
    void launch_getShapeOnMousePressed(Object event);
    void launch_createShapeInToolBarOnClick(Object event);
    void launch_createSelectionRectangleOnClick(Object event);
    void launch_rectangleSelectionReleased(Object event);
    void launch_selectionRectangleDraggedInCanvas(Object event);
    void launch_rightClick(Object event);
    void launch_colorPickerHandler(Object event);
    void launch_groupShape(Object event);
    void launch_deGroupShape(Object event);
    void launch_CTRLAndMouseOnClickShapeGroup(Object event);
    void launch_undoEvent(Object event);
    void launch_redoEvent(Object event);
    void launch_saveEvent(Object event);
    void launch_loadEvent(Object event);
    void launch_editShape(Object event);

    /* Other */
    void onTrashInfo(Point point);

}
