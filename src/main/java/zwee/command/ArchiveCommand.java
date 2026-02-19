package zwee.command;

import zwee.storage.Storage;
import zwee.task.TaskList;
import zwee.ui.Ui;

/**
 * Commands the application to move all current tasks to an archive file
 * and clear the active task list.
 */
public class ArchiveCommand extends Command {

    private static final String ARCHIVE_PATH = "./data/archive.txt";

    /**
     * Executes the archive process by saving current tasks to an archive file,
     * clearing the TaskList, and updating the primary storage.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying feedback.
     * @param storage The storage handler for file operations.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            return ui.showEmptyArchiveMessage();
        }

        storage.archiveTasks(tasks, ARCHIVE_PATH);

        int count = tasks.size();
        while (!tasks.isEmpty()) {
            tasks.delete(1); 
        }

        storage.save(tasks);

        ui.showLine();
        return ui.getArchiveSuccessMessage(count, ARCHIVE_PATH);
    }
}