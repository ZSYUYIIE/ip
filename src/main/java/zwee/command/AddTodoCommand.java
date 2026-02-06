package zwee.command;

import zwee.storage.Storage;
import zwee.task.Task;
import zwee.task.TaskList;
import zwee.task.Todo;
import zwee.ui.Ui;

/**
 * Adds a todo task to the task list.
 */
public class AddTodoCommand extends Command {

    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = new Todo(description);
        tasks.add(task);
        storage.save(tasks);
        return ui.showTaskAdded(task, tasks.size());
    }
}