public class TasksList {
    private Task[] tasks;
    private int taskCount;

    public TasksList() {
        this.tasks = new Task[100];
        this.taskCount = 0;
    }

    public void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }
    public Task getTask(int index) {
        return tasks[index];
    }

      public Task mark(int taskNumber) { 
        Task task = tasks[taskNumber - 1];
        task.mark();
        return task;
    }

    public Task unmark(int taskNumber) { 
        Task task = tasks[taskNumber - 1];
        task.unmark();
        return task;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void deleteTask(int taskNumber) {
        for (int i = taskNumber - 1; i < taskCount - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[taskCount - 1] = null;
        taskCount--;
    }
}
