package zwee;

import zwee.command.Command;
import zwee.parser.Parser;
import zwee.storage.Storage;
import zwee.task.TaskList;
import zwee.ui.Ui;

/**
 * The main class for the Zwee application.
 */
public class Zwee {

    private static final String DEFAULT_FILE_PATH = "data/zwee.txt";

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    public Zwee(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    public String showStartup() {
        return ui.showWelcome() + "\n\n" + ui.showList(tasks);
    }

    /**
     * Runs the main loop of the application.
     */
    public void run() {
        ui.showWelcome();
        ui.showList(tasks);
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command command = Parser.parse(fullCommand);

                String output = command.execute(tasks, ui, storage);

            isExit = command.isExit();
            } catch (ZweeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Zwee(DEFAULT_FILE_PATH).run();
    }

    /**
     * Gets the response for a given user input command.
     *
     * @param input The user input command.
     * @return The response to be shown to the user.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            return c.execute(tasks, ui, storage); // returns a String instead of printing
        } catch (ZweeException e) {
            return e.getMessage();
        }
    }
}