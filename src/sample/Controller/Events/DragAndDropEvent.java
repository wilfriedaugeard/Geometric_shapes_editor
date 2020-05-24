package sample.Controller.Events;

import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import sample.Controller.Command.*;
import sample.Controller.IController;
import sample.Factory.ModelFactory.PointFactory;
import sample.Model.Point;
import sample.Model.IShapeInter;

import java.util.ArrayList;

/**
 * Drag and drop event. Move, delete or create a shape / group of shapes.
 */
public class DragAndDropEvent implements Event {
    /*No shape group case*/
    private IShapeInter shapeToTranslate;

    /*Shape group case*/
    private IShapeInter shapeGroupToModify;
    private boolean isInShapeGroup;

    /*General cases*/
    private Shape shapeInView;
    private Point MousePos;
    protected IController controller;

    /*Tempory array*/
    private ArrayList<IShapeInter> shapeInToolBarTmp;
    private IShapeInter shapeSavedInToolbar;
    private int posInTolbar;
    private boolean toolbarShapeMove;

    private Point BeginPos;

    public DragAndDropEvent(IController controller) {
        this.controller = controller;
    }

    /**
     * Moving a shape
     */
    EventHandler<MouseEvent> finalShapeToCanvas = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                mouseEvent.consume();
                return;
            }
            int indexOfShape = controller.getView().getShapesInCanvas().indexOf(shapeInView);
            // shape in toolbar bar selected
            if(indexOfShape < 0){
                MousePos.setX(mouseEvent.getSceneX());
                MousePos.setY(mouseEvent.getSceneY());
                mouseEvent.consume();
                return;
            }

            double dragX = mouseEvent.getSceneX() - MousePos.getX();
            double dragY = mouseEvent.getSceneY() - MousePos.getY();

            ICommand translateCommand;

            if (isInShapeGroup) {
                translateCommand = new TranslateCommand(dragX, dragY, shapeGroupToModify, controller, isInShapeGroup);
            } else {
                translateCommand = new TranslateCommand(dragX, dragY, shapeToTranslate, controller, isInShapeGroup);
            }

            controller.addLastCommand(translateCommand);
            controller.setCurrentPosInCommands(controller.getNbCommands());
            translateCommand.execute();

            MousePos.setX(mouseEvent.getSceneX());
            MousePos.setY(mouseEvent.getSceneY());

            mouseEvent.consume();
        }
    };

    /**
     * Place a shape in the fontground
     */
    EventHandler<MouseEvent> moveShapeOnPressingMouse = mouseEvent -> {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) {
            mouseEvent.consume();
            return;
        }
        Shape shape = (Shape) mouseEvent.getSource();
        shape.toFront();
    };


    /**
     * A shape in the toolbar is moving
     * @param shapeInToolbar The moving toolbar shape
     */
    public void moveShapeInToolbar(IShapeInter shapeInToolbar){
        double x = controller.getView().getShapeXPositionInToolBar(shapeInView);
        double y = controller.getView().getShapeYPositionInToolBar(shapeInView);
        // Save the toolbar shape
        shapeSavedInToolbar = shapeInToolbar;
        posInTolbar = controller.getShapesInToolBar().indexOf(shapeInToolbar);
        shapeToTranslate = shapeInToolbar;
        //shapeGroupToModify = shapeToTranslate;
        isInShapeGroup = false;
        for(IShapeInter group : controller.getShapeGroupsInToolBar()){
            if (!shapeToTranslate.getChildren().isEmpty() && group.getChildren().containsAll(shapeToTranslate.getChildren())){
                isInShapeGroup = true;
                shapeGroupToModify = group;
            }
        }
        toolbarShapeMove = true;
        controller.updateEvents();
    }


    /**
     * Get the shape while mouse is pressed
     */
    EventHandler<MouseEvent> getShapeOnMousePressed = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton() != MouseButton.PRIMARY){
                mouseEvent.consume();
                return;
            }
            toolbarShapeMove = false;
            shapeInView = (Shape) mouseEvent.getSource();
            isInShapeGroup = false;
            int indexOfShape = controller.getView().getShapesInCanvas().indexOf(shapeInView);
            // shape in toolbar bar selected
            if(indexOfShape < 0){
                shapeInToolBarTmp = new ArrayList<>();
                shapeInToolBarTmp.addAll(controller.getShapesInToolBar());

                int indexToolbar = controller.getView().getShapesInToolBar().indexOf(shapeInView);
                moveShapeInToolbar(controller.getShapesInToolBar().get(indexToolbar));

                MousePos = PointFactory.getPoint(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                BeginPos = PointFactory.getPoint(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                mouseEvent.consume();
                return;
            }
            shapeToTranslate = controller.getShapesInCanvas().get(indexOfShape);
            //Get the shape's group if it is in a ShapeGroup
            for(IShapeInter shapeGroup : controller.getShapeGroups()){
                if(shapeGroup.getChildren().containsAll(shapeToTranslate.getChildren())){
                    shapeGroupToModify = shapeGroup;
                    isInShapeGroup = true;
                }
            }
            MousePos = PointFactory.getPoint(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            BeginPos = PointFactory.getPoint(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            mouseEvent.consume();
        }
    };

    /**
     * The shape is on trash. Call remove command.
     */
    private void onTrash(){
        ICommand removeCommand;

        if(isInShapeGroup){
            removeCommand = new RemoveCommand(shapeGroupToModify, controller);
        }else {
            removeCommand = new RemoveCommand(shapeToTranslate, controller);
        }
        controller.addLastCommand(removeCommand);
        controller.setCurrentPosInCommands(controller.getNbCommands());
        removeCommand.execute();
    }


    /**
     * When the shape is released over the toolbar
     */
    EventHandler<MouseEvent> overToolbar = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                mouseEvent.consume();
                return;
            }
            double x = MousePos.getX();
            double y = MousePos.getY();

            double shapeY = controller.getView().getShapeYPositionInToolBar(shapeInView);
            double shapeX = controller.getView().getShapeXPositionInToolBar(shapeInView);
            shapeToTranslate.setPos(PointFactory.getPoint(shapeX, shapeY));

            // Test if the released shape is on the trash
            if (controller.getView().isOn(controller.getView().getTrash(), PointFactory.getPoint(x, y))){
                if(toolbarShapeMove){
                    ToolBar toolBar = (ToolBar) controller.getView().getToolBar();
                    if(isInShapeGroup){
                        for(IShapeInter child : shapeGroupToModify.getChildren()){
                            // View delete
                            int i = controller.getShapesInToolBar().indexOf(child);
                            System.out.println(controller.getShapesInToolBar());
                            System.out.println(child);
                            shapeInView = controller.getView().getShapesInToolBar().get(i);
                            toolBar.getItems().remove(shapeInView);
                            controller.getView().getShapesInToolBar().remove(shapeInView);
                            controller.getShapesInToolBar().remove(child);
                        }
                    }else{
                        controller.getShapesInToolBar().remove(shapeToTranslate);
                        toolBar.getItems().remove(shapeInView);
                        controller.getView().getShapesInToolBar().remove(shapeInView);
                    }
                    mouseEvent.consume();
                    controller.saveState();
                    return;
                }
                onTrash();
            }
            // Test if the released shape is on Toolbar and if has been modify
            else {
                if (controller.getView().isOn(controller.getView().getToolBar(), PointFactory.getPoint(x, y))){
                    if(!sameShapeInToolBar(shapeToTranslate) && !toolbarShapeMove) {
                        ICommand addToToolbar = new AddToToolbarCommand(shapeToTranslate, isInShapeGroup, controller);
                        controller.addLastCommand(addToToolbar);
                        controller.setCurrentPosInCommands(controller.getNbCommands());
                        addToToolbar.execute();
                    }
                }else{
                    int shapeIndex = controller.getView().getShapesInToolBar().indexOf(shapeInView);
                    if(shapeIndex >= 0){
                        ICommand createShapeCommand = new CreateShapeCommand(shapeToTranslate, x, y, controller);
                        controller.addLastCommand(createShapeCommand);
                        controller.setCurrentPosInCommands(controller.getNbCommands());
                        createShapeCommand.execute();
                    }

                }
                mouseEvent.consume();
                return;
            }
            mouseEvent.consume();
        }
    };

    /**
     * Check if a group of shapes exist in the toolbar
     * @param shapeGroupInToolbar The shape to ckeck
     */
    public boolean sameShapeGroupInToolBar(IShapeInter shapeGroupInToolbar){
        for (IShapeInter group : controller.getShapeGroups()){
            if(group.getChildren().contains(shapeGroupInToolbar)){
                for (IShapeInter shape : group.getChildren()){
                    if(!shapeExisted(shape)){
                        return false;
                    }
                }
                if(group.getWidth()/group.getCoeff() != shapeGroupInToolbar.getWidth()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if a shape exist in the toolbar
     * @param shapeExisted The shape to check
     */
    public boolean shapeExisted(IShapeInter shapeExisted){
        for (IShapeInter shapeInToolBar : controller.getShapesInToolBar()) {
            if (shapeExisted.getRGB() == shapeInToolBar.getRGB()
                    && shapeExisted.getRotation() == shapeInToolBar.getRotation()
                    && shapeExisted.getWidth() == shapeInToolBar.getWidth()/shapeInToolBar.getCoeff()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the a shape has a same shape in the toolbar
     * @param shapeInCanvas The shape
     */
    public boolean sameShapeInToolBar(IShapeInter shapeInCanvas) {
        if(isInShapeGroup){
            return sameShapeGroupInToolBar(shapeInCanvas);
        }
        else{
            return  shapeExisted(shapeInCanvas);
        }
    }

    @Override
    public void launchEvent() {
        controller.getView().launch_finalShapeToCanvas(finalShapeToCanvas);
        controller.getView().launch_moveShapeOnPressingMouse(moveShapeOnPressingMouse);
        controller.getView().launch_getShapeOnMousePressed(getShapeOnMousePressed);
        controller.getView().launch_overToolbar(overToolbar);
    }

}
