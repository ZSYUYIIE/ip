package zwee.command;

import storage.Storage;
import task.TaskList;
import zwee.ui.Ui;

/**
 * Exits the Zwee application.
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}