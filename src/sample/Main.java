package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Controller.Controller;
import sample.Factory.ControllerFactory;

public class Main extends Application {

    private final ControllerFactory controllerFactory = new ControllerFactory();

    @Override
    public void start(Stage primaryStage) {
        Controller controller = controllerFactory.createController();
        controller.initializeView();
        controller.initEvents();

        primaryStage.setTitle("Projet architecture logicielle");
        primaryStage.setScene(controller.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
