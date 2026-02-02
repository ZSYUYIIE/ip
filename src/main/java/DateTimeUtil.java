import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {

    private static final DateTimeFormatter USER_ISO_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter USER_DMY = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter USER_DMY_HHMM = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DISPLAY_DATE_TIME = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    // Storage format: <ISO_LOCAL_DATE_TIME>;<hasTime>
    private static final String STORAGE_SEPARATOR = ";";

    private DateTimeUtil() {}

    public static LocalDateTime parseUserDateTime(String raw) {
        String trimmed = raw.trim();

        try {
            return LocalDateTime.parse(trimmed, USER_DMY_HHMM);
        } catch (DateTimeParseException ignored) {
            // continue
        }

        try {
            LocalDate date = LocalDate.parse(trimmed, USER_ISO_DATE);
            return date.atStartOfDay();
        } catch (DateTimeParseException ignored) {
            // continue
        }

        try {
            LocalDate date = LocalDate.parse(trimmed, USER_DMY);
            return date.atStartOfDay();
        } catch (DateTimeParseException ignored) {
            // continue
        }

        throw new ZweeException("Invalid date/time. Use yyyy-mm-dd OR d/M/yyyy HHmm (e.g., 2/12/2019 1800).");
    }

    public static boolean userInputHasTime(String raw) {
        try {
            LocalDateTime.parse(raw.trim(), USER_DMY_HHMM);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String formatForDisplay(LocalDateTime dateTime, boolean hasTime) {
        if (hasTime) {
            return dateTime.format(DISPLAY_DATE_TIME);
        }
        return dateTime.toLocalDate().format(DISPLAY_DATE);
    }

    public static String formatForStorage(LocalDateTime dateTime, boolean hasTime) {
        return dateTime + STORAGE_SEPARATOR + hasTime;
    }

    public static LocalDateTime parseStorageDateTime(String stored) {
        return parseStorage(stored).dateTime;
    }

    public static boolean parseStorageHasTime(String stored) {
        return parseStorage(stored).hasTime;
    }

    private static ParsedStorage parseStorage(String stored) {
        String trimmed = stored.trim();
        String[] parts = trimmed.split(STORAGE_SEPARATOR, 2);

        if (parts.length == 2) {
            LocalDateTime dt = LocalDateTime.parse(parts[0].trim());
            boolean hasTime = Boolean.parseBoolean(parts[1].trim());
            return new ParsedStorage(dt, hasTime);
        }

        // Backward compatibility: old saved data might be just ISO datetime without ";<flag>"
        LocalDateTime dt = LocalDateTime.parse(trimmed);
        boolean hasTime = !dt.toLocalTime().equals(LocalTime.MIDNIGHT);
        return new ParsedStorage(dt, hasTime);
    }

    private static class ParsedStorage {
        private final LocalDateTime dateTime;
        private final boolean hasTime;

        private ParsedStorage(LocalDateTime dateTime, boolean hasTime) {
            this.dateTime = dateTime;
            this.hasTime = hasTime;
        }
    }
}