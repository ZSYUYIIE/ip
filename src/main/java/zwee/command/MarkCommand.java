package zwee.command;

import storage.Storage;
import task.Task;
import task.TaskList;
import zwee.ui.Ui;

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