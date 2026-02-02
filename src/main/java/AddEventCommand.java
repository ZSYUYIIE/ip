import java.time.LocalDateTime;

public class AddEventCommand extends Command {

    private final String description;
    private final LocalDateTime at;
    private final boolean hasTime;

    public AddEventCommand(String description, LocalDateTime at, boolean hasTime) {
        this.description = description;
        this.at = at;
        this.hasTime = hasTime;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = new Event(description, at, hasTime);
        tasks.add(task);
        storage.save(tasks);
        ui.showTaskAdded(task, tasks.size());
    }
}