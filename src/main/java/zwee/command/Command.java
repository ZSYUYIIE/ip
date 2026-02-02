package zwee.command;

import storage.Storage;
import task.TaskList;
import zwee.ui.Ui;

/**
 * Represents a command that can be executed in the Zwee application.
 */
public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);
    public boolean isExit() {
        return false;
    }
}