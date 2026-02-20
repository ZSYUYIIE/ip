// AI Attribution: Stream API refactoring assisted by Claude Haiku 4.5
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
        return "Hey babe! I'm Zwee \nLet me help you stay on top of your tasks...\n" + showLine();
    }
    
    /**
     * Displays the goodbye message to the user.
     * @return A String representing the goodbye message.
     */
    public String showGoodbye() {
        return "Catch you later, love! Don't work too hard!";
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
     * Uses IntStream with functional operations for list formatting.
     * AI: Claude Haiku 4.5 helped implement Stream API pattern with mapToObj and reduce.
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
        return "Done! I've added this to your list:\n  " + 
        task + "\nYou now have " + size + " task(s). You've got this!";
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param size The new size of the task list.
     * @return A String representation of the deletion message.
     */
    public String showTaskDeleted(Task task, int size) {
        return "Gotcha! I've deleted that for you:\n  " + 
        task + "\nYou're down to " + size + " task(s) now. Keep crushing it!";
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     * @return A String representation of the marked task message.
     */
    public String showTaskMarked(Task task) {
        return "Yay! I've marked this done: " + task + " - so proud of you\n";
    }

    /**
     * Displays a message indicating that a task has been unmarked as not done.
     *
     * @param task The task that was unmarked.
     * @return A String representation of the unmarked task message.
     */
    public String showTaskUnmarked(Task task) {
        return "No problem! I'll mark it as not done:\n  " + task;
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
        return "Perfect! I've stashed this away for you:\n  " + task;
    }

    /**
     * Displays a message indicating that a task has been unarchived.
     *
     * @param task The task that was unarchived.
     * @return A String representation of the unarchived task message.
     */
    public String showTaskUnarchived(Task task) {
        return "Brought it back for you, babe:\n  " + task;
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
        return "All done! I've archived " + count + " task(s). Time for a fresh start!";
    }
    
    /**
     * Displays a message indicating that the archive has been cleared.
     *
     * @return A String representation of the archive cleared message.
     */
    public String showArchiveCleared() {
        return "Archive cleared! Wiped the slate clean for you. \nLet's keep moving forward!";
    }
}