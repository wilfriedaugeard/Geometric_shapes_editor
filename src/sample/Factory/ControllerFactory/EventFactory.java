package sample.Factory.ControllerFactory;

import sample.Controller.IController;
import sample.Controller.Events.*;

public class EventFactory {
    /**
     * @param name The class name to create
     * @param controller Controller used as parameter to create event object
     * @return Event object
     */
    public static Event getEvent(String name, IController controller){
        if(name.equals("RightClick")){
            return new RightClick(controller);
        }
        if(name.equals("DragAndDropEvent")){
            return new DragAndDropEvent(controller);
        }
        if(name.equals("SelectionShapeEvent")){
            return new SelectionShapeEvent(controller);
        }
        if(name.equals("GroupShapeEvent")){
            return new GroupShapeEvent(controller);
        }
        if(name.equals("RedoEvent")){
            return new RedoEvent(controller);
        }
        if(name.equals("UndoEvent")){
            return new UndoEvent(controller);
        }
        if(name.equals("SaveEvent")){
            return new SaveEvent(controller);
        }
        if(name.equals("LoadEvent")){
            return new LoadEvent(controller);
        }
        return null;
    }
}
