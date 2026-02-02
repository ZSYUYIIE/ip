package zwee.command;
import java.time.LocalDate;

import storage.Storage;
import task.Deadline;
import task.Task;
import task.TaskList;
import zwee.ui.Ui;

/**
 * Adds a deadline task to the task list.
 */
public class AddDeadlineCommand extends Command {

    private final String description;
    private final LocalDate by;

    public AddDeadlineCommand(String description, LocalDate by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = new Deadline(description, by);
        tasks.add(task);
        storage.save(tasks);
        ui.showTaskAdded(task, tasks.size());
    }
}