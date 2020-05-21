package sample.Factory;

import sample.Controller.Controller;

public interface ControllerFactory {
    /**
     * @return Controller
     */
    public Controller createController();
}
