package zwee.command;

import storage.Storage;
import task.TaskList;
import zwee.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);
    public boolean isExit() {
        return false;
    }
}