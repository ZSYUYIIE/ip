import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private String dateString;
    private LocalDate date;

    public Deadline(String description, String date) {
        super(description);
        try {
            this.date = LocalDate.parse(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
        }
        this.dateString = this.date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    public String toFileString() {
        return "D | " + super.toFileString() + " | " + date;
    } 
    
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dateString + ")";
    }
}
