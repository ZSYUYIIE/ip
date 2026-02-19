package zwee.command;

import java.util.List;
import zwee.ZweeException;
import zwee.storage.Storage;
import zwee.task.Task;
import zwee.task.TaskList;
import zwee.ui.Ui;

public class UnarchiveCommand extends Command {
    private final int oneBasedIndex;
    private static final String ARCHIVE_PATH = "./data/archive.txt";

    public UnarchiveCommand(int oneBasedIndex) {
        this.oneBasedIndex = oneBasedIndex;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> archivedItems = storage.loadArchive(ARCHIVE_PATH);
        
        if (oneBasedIndex < 1 || oneBasedIndex > archivedItems.size()) {
            throw new ZweeException("Invalid archive index. Use 'viewarchive' to see available tasks.");
        }

        Task taskToRestore = archivedItems.remove(oneBasedIndex - 1);
        tasks.add(taskToRestore);
        storage.save(tasks); 
        storage.overwriteArchive(archivedItems, ARCHIVE_PATH); 

        return ui.showTaskUnarchived(taskToRestore);
    }
}