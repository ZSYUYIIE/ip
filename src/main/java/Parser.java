import java.time.LocalDateTime;

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

    private static final String TAG_BY = "/by";
    private static final String TAG_AT = "/at";

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

        switch (keyword) {
        case CMD_TODO:
            return parseTodo(args);
        case CMD_DEADLINE:
            return parseDeadline(args);
        case CMD_EVENT:
            return parseEvent(args);
        case CMD_MARK:
            return new MarkCommand(parseIndex(args, "mark"));
        case CMD_UNMARK:
            return new UnmarkCommand(parseIndex(args, "unmark"));
        case CMD_DELETE:
            return new DeleteCommand(parseIndex(args, "delete"));
        default:
            throw new ZweeException("Unknown command: " + keyword);
        }
    }

    private static String[] splitKeywordAndArgs(String trimmed) {
        String[] parts = trimmed.split(DELIMITER_SPACE, SPLIT_LIMIT_TWO);
        String keyword = parts[0];
        String args = (parts.length < 2) ? "" : parts[1].trim();
        return new String[]{keyword, args};
    }

    private static Command parseTodo(String args) {
        if (args.isEmpty()) {
            throw new ZweeException("todo format: todo <description>");
        }
        return new AddTodoCommand(args);
    }

    private static Command parseDeadline(String args) {
        String[] parts = splitOnTag(args, TAG_BY, "deadline format: deadline <description> /by <date>");
        String description = parts[0];
        String byRaw = parts[1];

        LocalDateTime by = DateTimeUtil.parseUserDateTime(byRaw);
        boolean hasTime = DateTimeUtil.userInputHasTime(byRaw);
        return new AddDeadlineCommand(description, by, hasTime);
    }

    private static Command parseEvent(String args) {
        String[] parts = splitOnTag(args, TAG_AT, "event format: event <description> /at <date/time>");
        String description = parts[0];
        String atRaw = parts[1];

        LocalDateTime at = DateTimeUtil.parseUserDateTime(atRaw);
        boolean hasTime = DateTimeUtil.userInputHasTime(atRaw);
        return new AddEventCommand(description, at, hasTime);
    }

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

    private static int parseIndex(String args, String commandWord) {
        if (args.isEmpty()) {
            throw new ZweeException(commandWord + " format: " + commandWord + " <task number>");
        }
        try {
            return Integer.parseInt(args.trim());
        } catch (NumberFormatException e) {
            throw new ZweeException("Task number must be an integer.");
        }
    }

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

    private static Task createTaskFromStoredParts(String type, String description, String[] parts) {
        switch (type) {
        case "T":
            return new Todo(description);
        case "D":
            if (parts.length < 4) {
                throw new ZweeException("Corrupted deadline entry.");
            }
            return Deadline.fromStorage(description, parts[3].trim());
        case "E":
            if (parts.length < 4) {
                throw new ZweeException("Corrupted event entry.");
            }
            return Event.fromStorage(description, parts[3].trim());
        default:
            throw new ZweeException("Unknown task type in storage: " + type);
        }
    }

    private static void applyDoneFlag(Task task, String doneFlag) {
        if ("1".equals(doneFlag)) {
            task.mark();
        } else if (!"0".equals(doneFlag)) {
            throw new ZweeException("Invalid done flag in storage: " + doneFlag);
        }
    }
}