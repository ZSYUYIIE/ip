import java.time.LocalDateTime;

public class Deadline extends Task {

    private final LocalDateTime by;
    private final boolean hasTime;

    public Deadline(String description, LocalDateTime by, boolean hasTime) {
        super(description);
        this.by = by;
        this.hasTime = hasTime;
    }

    public static Deadline fromStorage(String description, String storedDateTime) {
        LocalDateTime dateTime = DateTimeUtil.parseStorageDateTime(storedDateTime);
        boolean hasTime = DateTimeUtil.parseStorageHasTime(storedDateTime);
        return new Deadline(description, dateTime, hasTime);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.formatForDisplay(by, hasTime) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + doneFlag() + " | " + getDescription() + " | "
                + DateTimeUtil.formatForStorage(by, hasTime);
    }
}