package zwee.parser;
import java.time.LocalDate;

import zwee.ZweeException;
import zwee.command.AddDeadlineCommand;
import zwee.command.AddEventCommand;
import zwee.command.AddTodoCommand;
import zwee.command.Command;
import zwee.command.DeleteCommand;
import zwee.command.ExitCommand;
import zwee.command.ListCommand;
import zwee.command.MarkCommand;
import zwee.command.UnmarkCommand;
import zwee.command.FindCommand;
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

    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_FIND = "find";

    private static final String TAG_BY = "/by";
    private static final String TAG_FROM = "/from";
    private static final String TAG_TO = "/to";

    /**
     * Parses user input into a Command.
     *
     * @param input Full user input string.
     * @return Corresponding Command object.
     */
    public static Command parse(String input) {
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

        assert !keyword.isEmpty() : "Keyword should not be empty";
        assert args != null : "Args should not be null";
        assert !args.isEmpty() || keyword.equals(CMD_MARK)
            || keyword.equals(CMD_UNMARK) || keyword.equals(CMD_DELETE)
            || keyword.equals(CMD_FIND) : "Args should not be empty for this command";
        
        switch (keyword) {
        case CMD_TODO:
            return parseTodo(args);
        case CMD_DEADLINE:
            return parseDeadline(args);
        case CMD_EVENT:
            return parseEvent(args);
        case CMD_MARK:
            return new MarkCommand(parseIndex(args, CMD_MARK));
        case CMD_UNMARK:
            return new UnmarkCommand(parseIndex(args, CMD_UNMARK));
        case CMD_DELETE:
            return new DeleteCommand(parseIndex(args, CMD_DELETE));
        case CMD_FIND:
            return new FindCommand(args);
        default:
            throw new ZweeException("OOPS, please type a valid command: " + keyword);
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
            throw new ZweeException("Please enter todo format: todo <description>");
        }
        return new AddTodoCommand(args);
    }

    /**
     * Parses deadline in the form: deadline <desc> /by <date>
     */
    private static Command parseDeadline(String args) {
        String[] parts = splitOnTag(args, TAG_BY,
                "Please enter deadline format: deadline <description> /by <date>");
        String description = parts[0];
        String byRaw = parts[1];

        LocalDate by = DateTimeUtil.parseUserDate(byRaw);
        return new AddDeadlineCommand(description, by);
    }

    /**
     * Parses event in the form: event <desc> /at <startDate> | <endDate>
     */
    private static Command parseEvent(String args) {
        String[] firstSplit = splitOnTag(args, TAG_FROM,
                "Please enter event format: event <description> /from <startDate> /to <endDate>");
        String description = firstSplit[0];
        String afterFrom = firstSplit[1];

        String[] secondSplit = splitOnTag(afterFrom, TAG_TO,
                "Please enter event format: event <description> /from <startDate> /to <endDate>");
        String startRaw = secondSplit[0];
        String endRaw = secondSplit[1];

        LocalDate start = DateTimeUtil.parseUserDate(startRaw);
        LocalDate end = DateTimeUtil.parseUserDate(endRaw);

        if (end.isBefore(start)) {
            throw new ZweeException("Event end date cannot be before start date.");
        }
        return new AddEventCommand(description, start, end);
    }

    /**
     * Splits the args on a given tag and validates the result.
     *
     * @param args The arguments string to split.
     * @param tag The tag to split on.
     * @param errorMessage The error message to throw if split fails.
     * @return An array with description and date part.
     * @throws ZweeException If the split fails or parts are empty.
     */
    private static String[] splitOnTag(String args, String tag, String errorMessage) {
        String[] parts = args.split(tag, SPLIT_LIMIT_TWO);
        if (parts.length < 2) {
            throw new ZweeException(errorMessage);
        }

        String description = parts[0].trim();
        String datePart = parts[1].trim();

        if (description.isEmpty() || datePart.isEmpty()) {
            throw new ZweeException(errorMessage);
        }
        return new String[]{description, datePart};
    }

    /**
     * Parses a one-based index from the args.
     *
     * @param args The arguments string containing the index.
     * @param commandWord The command word for error messages.
     * @return The parsed one-based index.
     * @throws ZweeException If parsing fails.
     */
    private static int parseIndex(String args, String commandWord) {
        if (args.isEmpty()) {
            throw new ZweeException("Please enter " + commandWord + " format: " + commandWord + " <task number>");
        }
        try {
            return Integer.parseInt(args.trim());
        } catch (NumberFormatException e) {
            throw new ZweeException("Please enter an integer.");
        }
    }

    /**
     * Parses a stored line (e.g., "D | 0 | return book | 2021-01-05") into a Task.
     *
     * @param line One line from the save file.
     * @return Parsed Task.
     */
    public static Task parseStoredTask(String line) {
        String[] parts = line.split(SECTION_SEPARATOR);
        if (parts.length < 3) {
            throw new ZweeException("Corrupted data file line: " + line);
        }

        String type = parts[0].trim();
        String doneFlag = parts[1].trim();
        String description = parts[2].trim();

        Task task = createTaskFromStoredParts(type, description, parts);
        applyDoneFlag(task, doneFlag);
        return task;
    }

    /**
     * Creates a Task from its stored parts.
     * @param type
     * @param description
     * @param parts
     * @return
     */
    private static Task createTaskFromStoredParts(String type, String description, String[] parts) {
        switch (type) {
        case "T":
            return new Todo(description);
        case "D":
            if (parts.length != 4) {
                throw new ZweeException("Corrupted deadline entry.");
            }
            return Deadline.fromStorage(description, parts[3].trim());
        case "E":
            if (parts.length != 5) {
                throw new ZweeException("Corrupted event entry.");
            }
            return Event.fromStorage(description, parts[3].trim(), parts[4].trim());
        default:
            throw new ZweeException("Unknown task type in storage: " + type);
        }
    }

    /**
     * Applies the done flag to the task.
     * @param task
     * @param doneFlag
     */
    private static void applyDoneFlag(Task task, String doneFlag) {
        if ("1".equals(doneFlag)) {
            task.mark();
        } else if (!"0".equals(doneFlag)) {
            throw new ZweeException("Invalid done flag in storage: " + doneFlag);
        }
    }
}