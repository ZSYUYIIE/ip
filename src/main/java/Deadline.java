import java.time.LocalDate;

/**
 * Represents a deadline task with a due date.
 */
public class Deadline extends Task {

    private final LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Creates a Deadline from stored file data.
     *
     * @param description Task description.
     * @param storedDate Stored date in yyyy-MM-dd format.
     * @return Deadline created from storage.
     */
    public static Deadline fromStorage(String description, String storedDate) {
        LocalDate by = DateTimeUtil.parseUserDate(storedDate);
        return new Deadline(description, by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.formatForDisplay(by) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + doneFlag() + " | " + getDescription() + " | " + by;
    }
}