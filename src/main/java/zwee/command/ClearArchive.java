package zwee.command;

import zwee.storage.Storage;
import zwee.task.TaskList;
import zwee.ui.Ui;

public class ClearArchive extends Command {

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        storage.clearArchive();
        return ui.showArchiveCleared();
    }
}
