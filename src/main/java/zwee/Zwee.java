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
            return "Zwee heard: " + input;
    }
}