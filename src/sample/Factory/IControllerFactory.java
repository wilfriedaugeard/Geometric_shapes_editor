package sample.Factory;

import sample.Controller.IController;

public interface IControllerFactory {
    /**
     * @return Controller
     */
    public IController createController();
}
