package sample.Factory;

import sample.Controller.IController;
import sample.Controller.ControllerJavaFX;
import sample.Controller.ControllerJavaFXAdaptee;
import sample.View.ViewJavaFX;
import sample.View.ViewJavaFXAdaptee;

public class ControllerJavaFXFactory implements IControllerFactory {

    private ViewJavaFXAdaptee view;

    public ControllerJavaFXFactory() {
        view = new ViewJavaFXAdaptee(new ViewJavaFX());
    }

    @Override
    public IController createController(){
        return new ControllerJavaFXAdaptee(new ControllerJavaFX(view));
    }

}
