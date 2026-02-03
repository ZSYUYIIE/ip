package zwee.command;

import zwee.storage.Storage;
import zwee.task.Task;
import zwee.task.TaskList;
import zwee.ui.Ui;

/**
 * Deletes a task from the task list.
 */
public class DeleteCommand extends Command {

    private final int oneBasedIndex;

    public DeleteCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task removed = tasks.delete(oneBasedIndex);
        storage.save(tasks);
        ui.showTaskDeleted(removed, tasks.size());
    }
}