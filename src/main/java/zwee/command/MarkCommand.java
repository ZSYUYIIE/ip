package zwee.command;

import zwee.storage.Storage;
import zwee.task.Task;
import zwee.task.TaskList;
import zwee.ui.Ui;

/**
 * Marks a task as completed in the task list.
 */
public class MarkCommand extends Command {

    private final int oneBasedIndex;

    public MarkCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.mark(oneBasedIndex);
        storage.save(tasks);
        ui.showTaskMarked(task);
    }
}