package zwee.task;

/**
 * Represents a task with a description and completion status.
 */
public abstract class Task {

    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Gets the task description.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "[X]" if done, "[ ]" otherwise.
     */
    protected String statusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns the done flag for file storage.
     *
     * @return "1" if done, "0" otherwise.
     */
    protected String doneFlag() {
        return isDone ? "1" : "0";
    }

    public abstract String toFileString();

    @Override
    public String toString() {
        return statusIcon() + " " + description;
    }
}