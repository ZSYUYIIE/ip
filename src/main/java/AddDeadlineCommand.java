import java.time.LocalDateTime;

public class AddDeadlineCommand extends Command {

    private final String description;
    private final LocalDateTime by;
    private final boolean hasTime;

    public AddDeadlineCommand(String description, LocalDateTime by, boolean hasTime) {
        this.description = description;
        this.by = by;
        this.hasTime = hasTime;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = new Deadline(description, by, hasTime);
        tasks.add(task);
        storage.save(tasks);
        ui.showTaskAdded(task, tasks.size());
    }
}