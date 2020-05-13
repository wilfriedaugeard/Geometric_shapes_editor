package sample.Factory;

import sample.Controller.Controller;
import sample.Controller.ControllerJavaFX;
import sample.Controller.ControllerJavaFXAdaptee;
import sample.View.ViewJavaFX;
import sample.View.ViewJavaFXAdaptee;

public class ControllerJavaFXFactory implements ControllerFactory{

    private ViewJavaFXAdaptee view;

    public ControllerJavaFXFactory() {
        view = new ViewJavaFXAdaptee(new ViewJavaFX());
    }

    @Override
    public Controller createController(){
        return new ControllerJavaFXAdaptee(new ControllerJavaFX(view));
    }

}
