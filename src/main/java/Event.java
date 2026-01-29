import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private String startDateString;
    private String endDateString;
    private LocalDate startDate;
    private LocalDate endDate;

    public Event(String description, String startDate, String endDate) {
        super(description);
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
        this.startDateString = this.startDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        this.endDateString = this.endDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDateString + " to: " + endDateString + ")";
    }

    public String toFileString() {
        return "E | " + super.toFileString() + " | " + startDate + " | " + endDate;
    }
}
