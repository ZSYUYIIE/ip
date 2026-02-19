package zwee.command;

import zwee.storage.Storage;
import zwee.task.Task;
import zwee.task.TaskList;
import zwee.ui.Ui;

/**
 * Commands the application to archive a specific task by its index.
 */
public class ArchiveCommand extends Command {
    private final int targetIndex;
    private static final String ARCHIVE_PATH = "./data/archive.txt";

    public ArchiveCommand(int index) {
        this.targetIndex = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task taskToArchive = tasks.delete(targetIndex);
        storage.saveToArchive(taskToArchive, ARCHIVE_PATH);
        storage.save(tasks);

        return "Archived: " + taskToArchive;
    }
}