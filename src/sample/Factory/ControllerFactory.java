package sample.Factory;

import sample.Controller.Controller;
import sample.View.View;

public class ControllerFactory {

    private View view;

    public ControllerFactory() {
        view = new View();
    }

    public Controller createController(){
        return new Controller(view);
    }

}
