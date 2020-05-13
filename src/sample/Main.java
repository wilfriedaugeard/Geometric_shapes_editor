package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.Controller;
import sample.Factory.ControllerFactory;
import sample.Factory.ControllerJavaFXFactory;

public class Main extends Application {

    private final ControllerFactory controllerFactory = new ControllerJavaFXFactory();

    @Override
    public void start(Stage primaryStage) {
        Controller controller = controllerFactory.createController();
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
