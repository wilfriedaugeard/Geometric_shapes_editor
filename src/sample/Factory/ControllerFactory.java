package sample.Factory;

import sample.Controller.Controller;
import sample.View.View;
import sample.View.ViewJavaFXAdaptee;

public class ControllerFactory {

    private ViewJavaFXAdaptee view;

    public ControllerFactory() {
        view = new ViewJavaFXAdaptee(new View());
    }

    public Controller createController(){
        return new Controller(view);
    }

}
