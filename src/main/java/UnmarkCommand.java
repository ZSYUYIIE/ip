public class UnmarkCommand extends Command {

    private final int oneBasedIndex;

    public UnmarkCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.unmark(oneBasedIndex);
        storage.save(tasks);
        ui.showTaskUnmarked(task);
    }
}