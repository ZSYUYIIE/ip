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
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert oneBasedIndex > 0 : "Index should be positive";
        Task task = tasks.mark(oneBasedIndex);
        storage.save(tasks);
        return ui.showTaskMarked(task);
    }
}