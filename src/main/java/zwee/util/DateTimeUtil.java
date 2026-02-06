package zwee.util;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import zwee.ZweeException;

/**
 * Utility class for handling date and time parsing and formatting.
 */
public class DateTimeUtil {

    private static final DateTimeFormatter USER_ISO_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter USER_DMY = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter USER_DMY_HHMM = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DISPLAY_DATE_TIME = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    private DateTimeUtil() {}

    /**
     * Parses user-provided date/time input.
     * Supported:
     * - yyyy-MM-dd
     * - d/M/yyyy
     * - d/M/yyyy HHmm (e.g., 2/12/2019 1800)
     */
    public static LocalDateTime parseUserDateTime(String raw) {
        String trimmed = raw.trim();

        try {
            return LocalDateTime.parse(trimmed, USER_DMY_HHMM);
        } catch (DateTimeParseException ignored) {
            // continue
        }

        LocalDate date = parseUserDate(trimmed);
        return date.atStartOfDay();
    }

    /**
     * Parses user-provided date input (no time).
     * Supported:
     * - yyyy-MM-dd
     * - d/M/yyyy
     */
    public static LocalDate parseUserDate(String raw) {
        String trimmed = raw.trim();

        try {
            return LocalDate.parse(trimmed, USER_ISO_DATE);
        } catch (DateTimeParseException ignored) {
            // continue
        }

        try {
            return LocalDate.parse(trimmed, USER_DMY);
        } catch (DateTimeParseException ignored) {
            // continue
        }

        throw new ZweeException("Invalid date. Use yyyy-mm-dd (e.g., 2021-01-05) or d/M/yyyy.");
    }

    /**
     * Formats a LocalDate for display as MMM dd yyyy.
     */
    public static String formatForDisplay(LocalDate date) {
        return date.format(DISPLAY_DATE);
    }

    /**
     * Formats a LocalDateTime for display. If time is midnight, prints only date.
     */
    public static String formatForDisplay(LocalDateTime dateTime) {
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.toLocalDate().format(DISPLAY_DATE);
        }
        return dateTime.format(DISPLAY_DATE_TIME);
    }
}