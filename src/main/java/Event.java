import java.time.LocalDateTime;

public class Event extends Task {

    private final LocalDateTime at;
    private final boolean hasTime;

    public Event(String description, LocalDateTime at, boolean hasTime) {
        super(description);
        this.at = at;
        this.hasTime = hasTime;
    }

    public static Event fromStorage(String description, String storedDateTime) {
        LocalDateTime dateTime = DateTimeUtil.parseStorageDateTime(storedDateTime);
        boolean hasTime = DateTimeUtil.parseStorageHasTime(storedDateTime);
        return new Event(description, dateTime, hasTime);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + DateTimeUtil.formatForDisplay(at, hasTime) + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + doneFlag() + " | " + getDescription() + " | "
                + DateTimeUtil.formatForStorage(at, hasTime);
    }
}