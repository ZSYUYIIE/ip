package zwee.command;

import zwee.storage.Storage;
import zwee.task.TaskList;
import zwee.ui.Ui;

/**
 * Lists all tasks in the task list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}