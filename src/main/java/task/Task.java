package task;
public abstract class Task {

    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    public String getDescription() {
        return description;
    }

    protected String statusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    protected String doneFlag() {
        return isDone ? "1" : "0";
    }

    public abstract String toFileString();

    @Override
    public String toString() {
        return statusIcon() + " " + description;
    }
}