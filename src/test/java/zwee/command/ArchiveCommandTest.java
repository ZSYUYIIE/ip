package zwee.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import zwee.storage.Storage;
import zwee.task.Deadline;
import zwee.task.TaskList;
import zwee.task.Todo;
import zwee.ui.Ui;
import java.time.LocalDate;

public class ArchiveCommandTest {
    @Test
    public void execute_nonEmptyList_tasksArchivedAndCleared() {
        // Setup
        LocalDate deadlineDate = LocalDate.of(2024, 12, 1);
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.add(new Deadline("submit report", deadlineDate));
        Ui ui = new Ui();
        Storage storage = new Storage("data/archive.txt");
        ArchiveCommand archiveCommand = new ArchiveCommand();

        // Execute
        String result = archiveCommand.execute(tasks, ui, storage).trim();

        // Verify
        assertEquals(0, tasks.size(), "Task list should be empty after archiving.");
        assertEquals("Success! 2 tasks moved to ./data/archive.txt" 
        + "\nYou now have a clean slate.", result);
    }
}
