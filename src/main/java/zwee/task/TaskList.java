package zwee.task;
import java.util.ArrayList;
import java.util.List;

import zwee.ZweeException;

/**
 * Represents a list of tasks.
 */

public class TaskList {

    private final List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list by its one-based index.
     *
     * @param oneBasedIndex The one-based index of the task to be deleted.
     * @return The deleted task.
     * @throws ZweeException If the index is out of bounds.
     */
    public Task delete(int oneBasedIndex) {
        int index = toZeroBasedIndex(oneBasedIndex);
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        return tasks.remove(index);
    }

    /**
     * Marks a task as done by its one-based index.
     *
     * @param oneBasedIndex The one-based index of the task to be marked.
     * @return The marked task.
     * @throws ZweeException If the index is out of bounds.
     */
    public Task mark(int oneBasedIndex) {
        int index = toZeroBasedIndex(oneBasedIndex);
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        Task task = tasks.get(index);
        task.mark();
        return task;
    }

    /**
    * Finds tasks that contain the given description.
    *
    * @param description The description to search for.
    * @return A new TaskList containing the found tasks.
    */
    public TaskList find(String description) {
        List<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(description)) {
                foundTasks.add(task);
            }
        }
        return new TaskList(foundTasks);
    }
    
    /**
     * Unmarks a task as not done by its one-based index.
     *
     * @param oneBasedIndex The one-based index of the task to be unmarked.
     * @return The unmarked task.
     * @throws ZweeException If the index is out of bounds.
     */
    public Task unmark(int oneBasedIndex) {
        int index = toZeroBasedIndex(oneBasedIndex);
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        Task task = tasks.get(index);
        task.unmark();
        return task;
    }
    
    public Task get(int zeroBasedIndex) {
        return tasks.get(zeroBasedIndex);
    }

    /**
     * Unarchives a task by its one-based index.
     *
     * @param oneBasedIndex The one-based index of the task to be unarchived.
     * @return The unarchived task.
     * @throws ZweeException If the index is out of bounds.
     */
    public Task archive(int oneBasedIndex) {
        int index = toZeroBasedIndex(oneBasedIndex);
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        Task task = tasks.get(index);
        task.archive();
        return task;
    }

    /**
     * Unarchives a task by its one-based index.
     *
     * @param oneBasedIndex The one-based index of the task to be unarchived.
     * @return The unarchived task.
     * @throws ZweeException If the index is out of bounds.
     */
    public Task unarchive(int oneBasedIndex) {
        int index = toZeroBasedIndex(oneBasedIndex);
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        Task task = tasks.get(index);
        task.unarchive();
        return task;
    }
    /**
     * Returns the number of tasks in the task list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the task list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public List<Task> getAll() {
        return new ArrayList<>(tasks);
    }

    /**
     * Converts a one-based index to a zero-based index and checks bounds.
     *
     * @param oneBasedIndex The one-based index to convert.
     * @return The corresponding zero-based index.
     * @throws ZweeException If the index is out of bounds.
     */
    private int toZeroBasedIndex(int oneBasedIndex) {
        int index = oneBasedIndex - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new ZweeException("Please enter a valid task number between 1 and " + 
            tasks.size() + ".");
        }
        return index;
    }
}