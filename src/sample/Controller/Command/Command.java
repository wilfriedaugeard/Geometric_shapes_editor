package sample.Controller.Command;

public interface Command {
    /**
     * Execute an action as an object Command, so we can undo or redo this action.
     */
    public void execute();

    /**
     * Undo the modifications caused by execute, so this will go back to the previous state of the objects changed by execute.
     */
    public void undo();

    /**
     * Redo the modifications if an undo action occurred previously.
     */
    public void redo();
}
