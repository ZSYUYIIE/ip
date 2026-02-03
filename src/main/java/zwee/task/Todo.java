package zwee.task;

/**
 * Represents a todo task with a description and no dates.
 */

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T | " + doneFlag() + " | " + getDescription();
    }
}