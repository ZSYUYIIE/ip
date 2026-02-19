package zwee.ui;
import java.util.Scanner;

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
            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1) + "." + tasks.get(i) + "\n");
        }
        return sb.toString();
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param task The task that was added.
     * @param size The new size of the task list.
     */
    public String showTaskAdded(Task task, int size) {
        return "Got it. I've added this task:\n  " + 
        task + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param size The new size of the task list.
     * @return A String representation of the deletion message.
     */
    public String showTaskDeleted(Task task, int size) {
        return "Noted. I've removed this task: " + 
        task + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     * @return A String representation of the marked task message.
     */
    public String showTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Displays a message indicating that a task has been unmarked as not done.
     *
     * @param task The task that was unmarked.
     * @return A String representation of the unmarked task message.
     */
    public String showTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
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
        sb.append("Here are the archived tasks:\n");
        for (int i = 0; i < archivedTasks.size(); i++) {
            sb.append((i + 1) + "." + archivedTasks.get(i) + "\n");
        }
        return sb.toString();
    }

    /**
     * Displays a message indicating that the archive process was successful.
     *
     * @param count The number of tasks that were archived.
     * @param path The file path where the tasks were archived.
     * @return A String representation of the archive success message.
     */    
    public String getArchiveSuccessMessage(int count, String path) {
        return "Success! " + count + " tasks moved to " + path + 
        "\nYou now have a clean slate.";
    }
    
    /**
     * Displays a message indicating that the task list is already empty when attempting to archive.
     *
     * @return A String representation of the empty archive message.
     */
    public String showEmptyArchiveMessage() {
        return "The task list is already empty. Nothing to archive.";
    }
}