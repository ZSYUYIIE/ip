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
        Ui ui = new Ui();
        Storage storage = new Storage("data/archive.txt");
        storage.clearArchive(); // Ensure archive is empty before test
        tasks.add(new Todo("read book"));
        tasks.add(new Deadline("submit report", deadlineDate));
        ArchiveCommand archiveCommand = new ArchiveCommand(1); // Archive the first task

        // Execute
        String result = archiveCommand.execute(tasks, ui, storage).trim();

        // Verify
        String expectedOutput = "Archived: [T][ ] read book";
        assertEquals(expectedOutput, result);
        assertEquals(1, tasks.size()); // Only one task should remain
    }
}
