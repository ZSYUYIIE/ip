package zwee;

import zwee.command.Command;
import zwee.parser.Parser;
import zwee.storage.Storage;
import zwee.task.TaskList;
import zwee.ui.Ui;
import zwee.ZweeException;
import zwee.command.Command;;

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
                ui.showLine();

                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (ZweeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Zwee(DEFAULT_FILE_PATH).run();
    }

    public String getResponse(String input) {
        /*try {
        Command c = Parser.parse(input);
            return c.execute(tasks, storage); // returns a String instead of printing
        } catch (ZweeException e) {
            return e.getMessage();
        }*/
        return "This is a placeholder response.";
    }
}