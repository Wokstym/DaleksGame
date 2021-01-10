package pl.edu.agh.ki.to.theoffice.common.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CommandExecutorTest {

    @Test
    void testExecute() {
        // given
        CommandExecutor commandExecutor = new CommandExecutor();
        MovePlayerCommand movePlayerCommand = mock(MovePlayerCommand.class);

        // when
        commandExecutor.execute(movePlayerCommand);

        // then
        assertFalse(commandExecutor.getCommandStack().empty());
        assertTrue(commandExecutor.getCommandStack().contains(movePlayerCommand));
    }

    @Test
    void testUndo() {
        // given
        CommandExecutor commandExecutor = new CommandExecutor();
        MovePlayerCommand movePlayerCommand = mock(MovePlayerCommand.class);
        commandExecutor.execute(movePlayerCommand);

        // when
        commandExecutor.undo();

        // then
        assertTrue(commandExecutor.getCommandStack().empty());
        assertFalse(commandExecutor.getCommandStack().contains(movePlayerCommand));
    }

    @Test
    void testClearPreviousCommands() {
        // given
        CommandExecutor commandExecutor = new CommandExecutor();
        MovePlayerCommand movePlayerCommand = mock(MovePlayerCommand.class);
        commandExecutor.execute(movePlayerCommand);

        // when
        commandExecutor.clearPreviousCommands();

        // then
        assertTrue(commandExecutor.getCommandStack().empty());
    }
}