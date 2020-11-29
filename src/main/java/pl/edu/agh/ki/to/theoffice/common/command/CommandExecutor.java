package pl.edu.agh.ki.to.theoffice.common.command;

import java.util.EmptyStackException;
import java.util.Stack;

public class CommandExecutor {

    private final Stack<Command> commandStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    public void execute(Command command) {
        command.execute();
        commandStack.push(command);
    }

    public boolean redo() {
        try {
            Command command = redoStack.pop();
            commandStack.push(command);

            command.redo();
        } catch (EmptyStackException e) {
            return false;
        }
        return true;
    }

    public boolean undo() {
        try {
            Command command = commandStack.pop();
            redoStack.push(command);

            command.undo();
        } catch (EmptyStackException e) {
            return false;
        }
        return true;
    }

}
