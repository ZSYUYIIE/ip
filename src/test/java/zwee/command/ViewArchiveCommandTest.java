package zwee.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import zwee.storage.Storage;
import zwee.task.Deadline;
import zwee.task.TaskList;
import zwee.task.Todo;
import zwee.ui.Ui;
import java.time.LocalDate;
import java.io.File;

public class ViewArchiveCommandTest {

    @Test
    public void execute_nonEmptyArchive_tasksDisplayed() {
        // Setup
        LocalDate deadlineDate = LocalDate.of(2024, 12, 1);
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        
        // FIX: Use a dummy file for the "active" list so we don't overwrite our archive
        Storage storage = new Storage("data/test_slate_dummy.txt");
        
        // Ensure we start with a clean state
        storage.clearArchive(); 
        
        // Add tasks to the "slate"
        tasks.add(new Todo("read book"));
        tasks.add(new Deadline("submit report", deadlineDate));

        // Archive the first task (index 1)
        // Note: ArchiveCommand internally uses "./data/archive.txt"
        new ArchiveCommand(1).execute(tasks, ui, storage); 
        
        // Archive the (new) first task
        new ArchiveCommand(1).execute(tasks, ui, storage); 

        ViewArchiveCommand viewArchiveCommand = new ViewArchiveCommand();

        // Execute
        String result = viewArchiveCommand.execute(tasks, ui, storage).trim();

        // Verify
        // Note: Formatting must match your showArchivedTasks output exactly
        String expectedOutput = "Here are your archived tasks:\n" +
                "1.[T][ ] read book\n" +
                "2.[D][ ] submit report (by: Dec 01 2024)";
        assertEquals(expectedOutput, result);
        
        // Cleanup dummy file
        new File("data/test_slate_dummy.txt").delete();
    }

    @Test
    public void execute_emptyArchive_messageShown() {
        // Setup
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        
        // FIX: Use dummy storage again
        Storage storage = new Storage("data/test_slate_dummy.txt");
        storage.clearArchive(); 
        
        ViewArchiveCommand viewArchiveCommand = new ViewArchiveCommand();

        // Execute
        String result = viewArchiveCommand.execute(tasks, ui, storage).trim();

        // Verify
        String expectedOutput = "Your archive is empty.";
        assertEquals(expectedOutput, result);
        
        // Cleanup
        new File("data/test_slate_dummy.txt").delete();
    }
}