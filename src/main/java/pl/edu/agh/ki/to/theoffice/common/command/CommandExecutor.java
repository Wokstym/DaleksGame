package pl.edu.agh.ki.to.theoffice.common.command;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.EmptyStackException;
import java.util.Stack;

@Slf4j
@Getter
public class CommandExecutor {

    private final Stack<Command> commandStack = new Stack<>();

    public void execute(Command command) {
        log.debug("executed command: {}", command);
        log.debug("Commands in history: {}", commandStack.size());
        command.execute();
        commandStack.push(command);
    }

    public boolean undo() {
        try {
            Command command = commandStack.pop();
            command.undo();
        } catch (EmptyStackException e) {
            return false;
        }
        return true;
    }

    public void clearPreviousCommands() {
        commandStack.clear();
    }

}
