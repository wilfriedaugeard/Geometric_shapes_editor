package sample.Controller.Command;

public interface Command {
    /**
     * @brief Execute an action as an object Command, so we can undo or redo this action.
     * @param
     * @return
     */
    public void execute();

    /**
     * @brief Undo the modifications caused by execute, so this will go back to the previous state of the objects changed by execute.
     * @param
     * @return
     */
    public void undo();

    /**
     * @brief Redo the modifications with call to execute if an undo action occurred previously.
     * @param
     * @return
     */
    public void redo();
}
