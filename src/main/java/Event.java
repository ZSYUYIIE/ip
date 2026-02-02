import java.time.LocalDate;

public class Event extends Task {

    private final LocalDate start;
    private final LocalDate end;

    public Event(String description, LocalDate start, LocalDate end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public static Event fromStorage(String description, String storedStart, String storedEnd) {
        LocalDate start = DateTimeUtil.parseUserDate(storedStart);
        LocalDate end = DateTimeUtil.parseUserDate(storedEnd);
        return new Event(description, start, end);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + DateTimeUtil.formatForDisplay(start)
                + " to: " + DateTimeUtil.formatForDisplay(end) + ")";
    }

    @Override
    public String toFileString() {
        // Keep exactly your format
        return "E | " + doneFlag() + " | " + getDescription() + " | " + start + " | " + end;
    }
}