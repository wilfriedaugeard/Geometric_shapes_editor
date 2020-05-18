package sample.Factory;

import sample.Controller.Controller;
import sample.Controller.Events.*;

public class AbstractEventFactory {
    public static Event getEvent(String name, Controller controller){
        if(name.equals("RightClick")){
            return new RightClick(controller);
        }
        if(name.equals("DragAndDropEvent")){
            return new DragAndDropEvent(controller);
        }
        if(name.equals("CreateShapeEvent")){
            return new CreateShapeEvent(controller);
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
