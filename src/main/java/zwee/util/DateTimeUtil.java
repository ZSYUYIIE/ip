package zwee.util;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Optional;

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

        // Try to parse with time format using Stream API
        Optional<LocalDateTime> result = Arrays.stream(new DateTimeFormatter[]{USER_DMY_HHMM})
                .map(formatter -> parseWithFormatter(trimmed, formatter, LocalDateTime.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        if (result.isPresent()) {
            return result.get();
        }

        LocalDate date = parseUserDate(trimmed);
        return date.atStartOfDay();
    }

    /**
     * Helper method to safely parse with a formatter.
     */
    @SuppressWarnings("unchecked")
    private static <T> Optional<T> parseWithFormatter(String input, DateTimeFormatter formatter, Class<T> type) {
        try {
            if (type == LocalDateTime.class) {
                return (Optional<T>) Optional.of(LocalDateTime.parse(input, formatter));
            }
        } catch (DateTimeParseException ignored) {
            // continue
        }
        return Optional.empty();
    }

    /**
     * Parses user-provided date input (no time).
     * Supported:
     * - yyyy-MM-dd
     * - d/M/yyyy
     */
    public static LocalDate parseUserDate(String raw) {
        String trimmed = raw.trim();

        Optional<LocalDate> result = Arrays.stream(new DateTimeFormatter[]{USER_ISO_DATE, USER_DMY})
                .map(formatter -> tryParseDate(trimmed, formatter))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        return result.orElseThrow(() -> 
            new ZweeException("Invalid date. Use yyyy-mm-dd (e.g., 2021-01-05) or d/M/yyyy."));
    }

    /**
     * Helper method to safely parse a date with a formatter.
     */
    private static Optional<LocalDate> tryParseDate(String input, DateTimeFormatter formatter) {
        try {
            return Optional.of(LocalDate.parse(input, formatter));
        } catch (DateTimeParseException ignored) {
            return Optional.empty();
        }
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