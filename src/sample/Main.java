package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.IController;
import sample.Factory.ControllerFactory.IControllerFactory;
import sample.Factory.ControllerFactory.ControllerJavaFXFactory;

public class Main extends Application {

    private final IControllerFactory controllerFactory = new ControllerJavaFXFactory();

    @Override
    public void start(Stage primaryStage) {
        IController controller = controllerFactory.createController();
        controller.initializeView();
        controller.initEvents();

        primaryStage.setTitle("Projet architecture logicielle");
        primaryStage.setScene((Scene) controller.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
