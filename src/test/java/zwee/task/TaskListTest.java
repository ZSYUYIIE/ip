package zwee.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void mark_validIndex_taskMarked() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        Task marked = tasks.mark(1);

        assertEquals("1", marked.doneFlag());
    }

    @Test
    public void unmark_validIndex_taskUnmarked() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.mark(1);

        Task unmarked = tasks.unmark(1);

        assertEquals("0", unmarked.doneFlag());
    }

    @Test
    public void mark_indexTooLarge_throwsException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertThrows(RuntimeException.class, () -> tasks.mark(2));
    }

    @Test
    public void mark_zeroIndex_throwsException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertThrows(RuntimeException.class, () -> tasks.mark(0));
    }
}