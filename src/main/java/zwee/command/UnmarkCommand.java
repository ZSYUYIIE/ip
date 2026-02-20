package zwee.command;

import zwee.storage.Storage;
import zwee.task.Task;
import zwee.task.TaskList;
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
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert oneBasedIndex > 0 : "Index should be positive luv";
        Task task = tasks.unmark(oneBasedIndex);
        storage.save(tasks);
        return ui.showTaskUnmarked(task);
    }
}