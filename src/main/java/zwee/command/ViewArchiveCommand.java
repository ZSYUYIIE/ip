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
        if (archived.isEmpty()) {
            return "Archive is empty.";
        }
        
        StringBuilder sb = new StringBuilder("Archived Tasks:\n");
        for (int i = 0; i < archived.size(); i++) { 
            sb.append(String.format("%d. %s\n", i + 1, archived.get(i)));
        }
        return sb.toString();
    }
}