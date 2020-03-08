package sample.Factory;

import sample.Controller.Events;

public abstract class AbstractEventFactory {
    abstract Events getEvent(Events name);
}
