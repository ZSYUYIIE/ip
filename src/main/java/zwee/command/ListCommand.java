package zwee.command;

import storage.Storage;
import task.TaskList;
import zwee.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}