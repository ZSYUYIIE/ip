package zwee.command;
import java.time.LocalDate;

import zwee.storage.Storage;
import zwee.task.Event;
import zwee.task.Task;
import zwee.task.TaskList;
import zwee.ui.Ui;

/**
 * Adds an event task to the task list.
 */
public class AddEventCommand extends Command {

    private final String description;
    private final LocalDate start;
    private final LocalDate end;

    public AddEventCommand(String description, LocalDate start, LocalDate end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = new Event(description, start, end);
        tasks.add(task);
        storage.save(tasks);
        return ui.showTaskAdded(task, tasks.size());
    }
}