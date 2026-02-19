package zwee.command;

import java.util.List;
import zwee.storage.Storage;
import zwee.task.Task;
import zwee.task.TaskList;
import zwee.ui.Ui;

/**
 * Views the list of archived tasks.
 */
public class ViewArchiveCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> archived = storage.loadArchive("./data/archive.txt");
        TaskList archivedList = new TaskList(archived);
        return ui.showArchivedTasks(archivedList);
    }
}