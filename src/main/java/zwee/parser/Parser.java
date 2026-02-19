package zwee.parser;
import java.time.LocalDate;

import zwee.ZweeException;
import zwee.command.AddDeadlineCommand;
import zwee.command.AddEventCommand;
import zwee.command.AddTodoCommand;
import zwee.command.ArchiveCommand;
import zwee.command.Command;
import zwee.command.DeleteCommand;
import zwee.command.ExitCommand;
import zwee.command.ListCommand;
import zwee.command.MarkCommand;
import zwee.command.UnmarkCommand;
import zwee.command.FindCommand;
import zwee.command.UnarchiveCommand;
import zwee.command.ViewArchiveCommand;
import zwee.command.ClearArchive;
import zwee.task.Deadline;
import zwee.task.Event;
import zwee.task.Task;
import zwee.task.Todo;
import zwee.util.DateTimeUtil;
/**
 * Parses user commands and stored task lines.
 */
public class Parser {

    private static final String DELIMITER_SPACE = " ";
    private static final String SECTION_SEPARATOR = " \\| ";
    private static final int SPLIT_LIMIT_TWO = 2;

    private static final String CMD_ARCHIVE = "archive";
    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_FIND = "find";
    private static final String CMD_VIEW_ARCHIVE = "viewarchive";
    private static final String CMD_CLEAR_ARCHIVE = "cleararchive";
    private static final String CMD_UNARCHIVE = "unarchive";

    private static final String TAG_BY = "/by";
    private static final String TAG_FROM = "/from";
    private static final String TAG_TO = "/to";

    /**
     * Parses user input into a Command with comprehensive error checking.
     *
     * @param input Full user input string.
     * @return Corresponding Command object.
     * @throws ZweeException If parsing fails with detailed error messages.
     */
    public static Command parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new ZweeException("No command entered. Try 'list' to see all tasks.");
        }
        
        String trimmed = input.trim();

        if (trimmed.equals(CMD_BYE)) {
            return new ExitCommand();
        }
        if (trimmed.equals(CMD_LIST)) {
            return new ListCommand();
        }

        String[] parts = splitKeywordAndArgs(trimmed);
        String keyword = parts[0];
        String args = parts[1];

        if (keyword.isEmpty()) {
            throw new ZweeException("Invalid command format. Please enter a valid command.");
        }
        
        switch (keyword) {
        case CMD_TODO:
            return parseTodo(args);
        case CMD_DEADLINE:
            return parseDeadline(args);
        case CMD_EVENT:
            return parseEvent(args);
        case CMD_MARK:
            return new MarkCommand(parseIndex(args, CMD_MARK, "mark"));
        case CMD_UNMARK:
            return new UnmarkCommand(parseIndex(args, CMD_UNMARK, "unmark"));
        case CMD_DELETE:
            return new DeleteCommand(parseIndex(args, CMD_DELETE, "delete"));
        case CMD_FIND:
            return parseSafely(() -> new FindCommand(args), "find");
        case CMD_ARCHIVE:
            return new ArchiveCommand(parseIndex(args, CMD_ARCHIVE, "archive"));
        case CMD_VIEW_ARCHIVE:
            return new ViewArchiveCommand();
        case CMD_UNARCHIVE:
            return new UnarchiveCommand(parseIndex(args, CMD_UNARCHIVE, "unarchive"));
        case CMD_CLEAR_ARCHIVE:
            return new ClearArchive();
        default:
            StringBuilder validCommands = new StringBuilder();
            validCommands.append("Unknown command: '").append(keyword).append("'.\n");
            validCommands.append("Valid commands are: bye, list, todo, deadline, event, mark, unmark, delete, find, archive, viewarchive, unarchive, cleararchive");
            throw new ZweeException(validCommands.toString());
        }
    }
    
    /**
     * Safely parses command with error handling.
     */
    @FunctionalInterface
    private interface CommandParser {
        Command parse() throws ZweeException;
    }
    
    /**
     * Wraps command parsing with error handling.
     */
    private static Command parseSafely(CommandParser parser, String commandName) {
        try {
            return parser.parse();
        } catch (ZweeException e) {
            throw e; // Re-throw ZweeException
        } catch (Exception e) {
            throw new ZweeException("Error parsing " + commandName + " command: " + e.getMessage());
        }
    }

    /**
     * Splits the input into a command keyword and its arguments.
     * @param trimmed
     * @return
     */
    private static String[] splitKeywordAndArgs(String trimmed) {
        String[] parts = trimmed.split(DELIMITER_SPACE, SPLIT_LIMIT_TWO);
        String keyword = parts[0];
        String args = (parts.length < 2) ? "" : parts[1].trim();
        return new String[] {keyword, args};
    }

    /**
     * Parses todo in the form: todo <description>
     */
    private static Command parseTodo(String args) {
        if (args.isEmpty()) {
            throw new ZweeException("Error: Todo requires a description.\nFormat: todo <description>\nExample: todo Buy groceries");
        }
        if (args.length() > 100) {
            throw new ZweeException("Error: Todo description is too long (max 100 characters).");
        }
        return new AddTodoCommand(args.trim());
    }

    /**
     * Parses deadline in the form: deadline <desc> /by <date>
     */
    private static Command parseDeadline(String args) {
        String[] parts = splitOnTag(args, TAG_BY,
                "Error: Deadline format incorrect.\nFormat: deadline <description> /by <date>\nExample: deadline Submit report /by 2026-03-01");
        String description = parts[0];
        String byRaw = parts[1];
        
        if (description.length() > 100) {
            throw new ZweeException("Error: Deadline description is too long (max 100 characters).");
        }

        LocalDate by = DateTimeUtil.parseUserDate(byRaw);
        return new AddDeadlineCommand(description, by);
    }

    /**
     * Parses event in the form: event <desc> /from <startDate> /to <endDate>
     */
    private static Command parseEvent(String args) {
        String[] firstSplit = splitOnTag(args, TAG_FROM,
                "Error: Event format incorrect.\nFormat: event <description> /from <startDate> /to <endDate>\nExample: event Team meeting /from 2026-03-01 /to 2026-03-01");
        String description = firstSplit[0];
        String afterFrom = firstSplit[1];

        String[] secondSplit = splitOnTag(afterFrom, TAG_TO,
                "Error: Event format incorrect. Missing /to tag.\nFormat: event <description> /from <startDate> /to <endDate>");
        String startRaw = secondSplit[0];
        String endRaw = secondSplit[1];

        if (description.length() > 100) {
            throw new ZweeException("Error: Event description is too long (max 100 characters).");
        }

        LocalDate start = DateTimeUtil.parseUserDate(startRaw);
        LocalDate end = DateTimeUtil.parseUserDate(endRaw);

        if (end.isBefore(start)) {
            throw new ZweeException("Error: Event end date cannot be before start date.\nStart: " + start + ", End: " + end);
        }
        
        if (end.equals(start)) {
            // This is allowed for same-day events
        }
        
        return new AddEventCommand(description, start, end);
    }

    /**
     * Splits the args on a given tag and validates the result with detailed error checking.
     *
     * @param args The arguments string to split.
     * @param tag The tag to split on.
     * @param errorMessage The error message to throw if split fails.
     * @return An array with description and date part.
     * @throws ZweeException If the split fails or parts are empty with detailed info.
     */
    private static String[] splitOnTag(String args, String tag, String errorMessage) {
        if (args == null || args.isEmpty()) {
            throw new ZweeException(errorMessage);
        }
        
        String[] parts = args.split("\\" + tag, SPLIT_LIMIT_TWO);
        if (parts.length < 2) {
            throw new ZweeException(errorMessage + "\nMissing tag: " + tag);
        }

        String description = parts[0].trim();
        String datePart = parts[1].trim();

        if (description.isEmpty()) {
            throw new ZweeException(errorMessage + "\nDescription is empty.");
        }
        
        if (datePart.isEmpty()) {
            throw new ZweeException(errorMessage + "\nMissing date/time after " + tag);
        }
        
        return new String[]{description, datePart};
    }

    /**
     * Parses a one-based index from the args.
     *
     * @param args The arguments string containing the index.
     * @param commandWord The command word for error messages.
     * @param displayName The human-readable name for error messages.
     * @return The parsed one-based index.
     * @throws ZweeException If parsing fails with detailed error message.
     */
    private static int parseIndex(String args, String commandWord, String displayName) {
        if (args == null || args.isEmpty()) {
            throw new ZweeException("Error: " + displayName + " requires a task number.\nFormat: " + commandWord + " <task number>\nExample: " + commandWord + " 1");
        }
        
        String trimmedArgs = args.trim();
        
        if (trimmedArgs.contains(" ")) {
            throw new ZweeException("Error: Invalid " + displayName + " command. Expected single number, got: " + trimmedArgs);
        }
        
        try {
            int index = Integer.parseInt(trimmedArgs);
            if (index <= 0) {
                throw new ZweeException("Error: Task number must be a positive integer (1 or greater).");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new ZweeException("Error: Invalid task number. Please enter a positive integer.\nGot: " + trimmedArgs);
        }
    }

    /**
     * Parses a stored line (e.g., "D | 0 | return book | 2021-01-05") into a Task with error recovery.
     *
     * @param line One line from the save file.
     * @return Parsed Task.
     * @throws ZweeException If line format is invalid.
     */
    public static Task parseStoredTask(String line) {
        if (line == null || line.trim().isEmpty()) {
            throw new ZweeException("Empty task line in data file.");
        }
        
        String[] parts = line.split(SECTION_SEPARATOR);
        if (parts.length < 3) {
            throw new ZweeException("Corrupted data file line (insufficient fields): " + line);
        }

        String type = parts[0].trim();
        String doneFlag = parts[1].trim();
        String description = parts[2].trim();

        if (type.isEmpty()) {
            throw new ZweeException("Corrupted task type in data file: " + line);
        }
        
        if (description.isEmpty()) {
            throw new ZweeException("Corrupted task - empty description: " + line);
        }

        Task task = createTaskFromStoredParts(type, description, parts, line);
        applyDoneFlag(task, doneFlag, line);
        return task;
    }

    
    /**
     * Creates a Task from stored parts with validation.
     */
    private static Task createTaskFromStoredParts(String type, String description, String[] parts, String line) {
        type = type.toUpperCase();
        
        switch (type) {
            case "T":
                if (parts.length != 3) {
                    throw new ZweeException("Corrupted todo task - expected 3 fields: " + line);
                }
                return new Todo(description);
                
            case "D":
                if (parts.length != 4) {
                    throw new ZweeException("Corrupted deadline task - expected 4 fields (has " + parts.length + "): " + line);
                }
                String byDate = parts[3].trim();
                if (byDate.isEmpty()) {
                    throw new ZweeException("Corrupted deadline - missing due date: " + line);
                }
                try {
                    LocalDate deadline = LocalDate.parse(byDate);
                    return new Deadline(description, deadline);
                } catch (Exception e) {
                    throw new ZweeException("Corrupted deadline - invalid date format '" + byDate + "': " + line);
                }
                
            case "E":
                if (parts.length != 5) {
                    throw new ZweeException("Corrupted event task - expected 5 fields (has " + parts.length + "): " + line);
                }
                String fromDate = parts[3].trim();
                String toDate = parts[4].trim();
                if (fromDate.isEmpty() || toDate.isEmpty()) {
                    throw new ZweeException("Corrupted event - missing start or end date: " + line);
                }
                try {
                    LocalDate start = LocalDate.parse(fromDate);
                    LocalDate end = LocalDate.parse(toDate);
                    if (start.isAfter(end)) {
                        throw new ZweeException("Corrupted event - start date is after end date: " + line);
                    }
                    return new Event(description, start, end);
                } catch (ZweeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ZweeException("Corrupted event - invalid date format in '" + line + "'");
                }
                
            default:
                throw new ZweeException("Unknown task type in data file: '" + type + "'. Line: " + line);
        }
    }
    
    /**
     * Applies the done flag to the task with validation.
     */
    private static void applyDoneFlag(Task task, String doneFlag, String line) {
        if ("1".equals(doneFlag)) {
            task.mark();
        } else if ("0".equals(doneFlag)) {
            // Task is not done
        } else {
            throw new ZweeException("Invalid done flag in storage: '" + doneFlag + "' (expected 0 or 1). Line: " + line);
        }
    }
}