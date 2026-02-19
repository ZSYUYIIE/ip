package zwee.ui;
import java.util.Scanner;
import java.util.stream.IntStream;

import zwee.task.Task;
import zwee.task.TaskList;

/**
 * Handles all interactions with the user.
 * Responsible for reading input and displaying messages to the console.
 */
public class Ui {

    private static final String LINE = "________________________________________";

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the initial greeting message to the user.
     * @return A String representing the welcome message.
     */
    public String showWelcome() {
        return "Hello! I'm Zwee\nWhat can I do for you?\n" + showLine();
    }
    
    /**
     * Displays the goodbye message to the user.
     * @return A String representing the goodbye message.
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Reads a line of input from the user.
     *
     * @return The user's input as a String.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a horizontal line for better readability.
     * @return A String representing the horizontal line.
     */
    public String showLine() {
        return LINE;
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public String showError(String message) {
        return message;
    }

    /**
     * Displays the list of tasks to the user.
     *
     * @param tasks The TaskList containing the tasks to be displayed.
     * @return A String representation of the task list.
     */
    public String showList(TaskList tasks) {
        if (tasks.isEmpty()) {
            return "OOPS, Your task list is empty.";
        }
        return IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                .reduce((line1, line2) -> line1 + "\n" + line2)
                .orElse("") + "\n";
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param task The task that was added.
     * @param size The new size of the task list.
     */
    public String showTaskAdded(Task task, int size) {
        return "Got it. I've added this task:\n  " + 
        task + "\nNow you have " + size + " task(s) in your list.";
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param size The new size of the task list.
     * @return A String representation of the deletion message.
     */
    public String showTaskDeleted(Task task, int size) {
        return "Got it. I've removed this task:\n  " + 
        task + "\nNow you have " + size + " task(s) in your list.";
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     * @return A String representation of the marked task message.
     */
    public String showTaskMarked(Task task) {
        return "Got it. I've marked this task as done:\n  " + task;
    }

    /**
     * Displays a message indicating that a task has been unmarked as not done.
     *
     * @param task The task that was unmarked.
     * @return A String representation of the unmarked task message.
     */
    public String showTaskUnmarked(Task task) {
        return "Got it. I've marked this task as not done:\n  " + task;
    }

    /**
     * Displays the list of archived tasks to the user.
     *
     * @param archivedTasks The TaskList containing the archived tasks to be displayed.
     * @return A String representation of the archived task list.
     */
    public String showArchivedTasks(TaskList archivedTasks) {
        if (archivedTasks.isEmpty()) {
            return "Your archive is empty.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are your archived tasks:\n");
        for (int i = 0; i < archivedTasks.size(); i++) {
            sb.append((i + 1) + "." + archivedTasks.get(i) + "\n");
        }
        return sb.toString();
    }

    /**
     * Displays a message indicating that a task has been archived.
     *
     * @param task The task that was archived.
     * @return A String representation of the archived task message.
     */
    public String showTaskArchived(Task task) {
        return "Got it. I've archived this task:\n  " + task;
    }

    /**
     * Displays a message indicating that a task has been unarchived.
     *
     * @param task The task that was unarchived.
     * @return A String representation of the unarchived task message.
     */
    public String showTaskUnarchived(Task task) {
        return "Got it. I've restored this task:\n  " + task;
    }
    /**
     * Displays the list of unarchived tasks to the user.
     *
     * @param unarchivedTasks The TaskList containing the unarchived tasks to be displayed.
     * @return A String representation of the unarchived task list.
     */
    public String showUnarchivedTasks(TaskList unarchivedTasks) {
        if (unarchivedTasks.isEmpty()) {
            return "No tasks were restored.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are your restored tasks:\n");
        for (int i = 0; i < unarchivedTasks.size(); i++) {
            sb.append((i + 1) + "." + unarchivedTasks.get(i) + "\n");
        }
        return sb.toString();
    }
    /**
     * Displays a message indicating that the archive process was successful.
     *
     * @param count The number of tasks that were archived.
     * @return A String representation of the archive success message.
     */    
    public String showArchiveSuccess(int count) {
        return "Done! I've archived " + count + " task(s). You now have a fresh start!";
    }
    
    /**
     * Displays a message indicating that the archive has been cleared.
     *
     * @return A String representation of the archive cleared message.
     */
    public String showArchiveCleared() {
        return "Done! I've cleared your archive.";
    }
}