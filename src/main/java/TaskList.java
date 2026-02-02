import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private final List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int oneBasedIndex) {
        int index = toZeroBasedIndex(oneBasedIndex);
        return tasks.remove(index);
    }

    public Task mark(int oneBasedIndex) {
        int index = toZeroBasedIndex(oneBasedIndex);
        Task task = tasks.get(index);
        task.mark();
        return task;
    }

    public Task unmark(int oneBasedIndex) {
        int index = toZeroBasedIndex(oneBasedIndex);
        Task task = tasks.get(index);
        task.unmark();
        return task;
    }

    public Task get(int zeroBasedIndex) {
        return tasks.get(zeroBasedIndex);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public List<Task> getAll() {
        return new ArrayList<>(tasks);
    }

    private int toZeroBasedIndex(int oneBasedIndex) {
        int index = oneBasedIndex - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new ZweeException("Task number is out of range.");
        }
        return index;
    }
}