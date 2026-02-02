package zwee.command;

import storage.Storage;
import task.Task;
import task.TaskList;
import zwee.ui.Ui;

/**
 * Unmarks a task as not completed in the task list.
 */
public class UnmarkCommand extends Command {

    private final int oneBasedIndex;

    public UnmarkCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.unmark(oneBasedIndex);
        storage.save(tasks);
        ui.showTaskUnmarked(task);
    }
}