package zwee.storage;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import zwee.ZweeException;
import zwee.parser.Parser;
import zwee.task.Task;
import zwee.task.TaskList;

/**
 * Handles loading and saving of tasks to a file.
 */
public class Storage {

    private final File file;

    public Storage(String path) {
        this.file = new File(path);
        ensureParentDirectoryExists();
    }

    /**
     * Ensures that the storage file and its parent directories exist.
     */
    private void ensureParentDirectoryExists() {
        try {
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            throw new ZweeException("Failed to initialize storage.");
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of tasks loaded from the file.
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        assert tasks != null : "Tasks should not be null";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    tasks.add(Parser.parseStoredTask(line));
                }
            }
        } catch (IOException e) {
            throw new ZweeException("Error loading tasks.");
        }
        return tasks;
    }

    /**
     * Saves the given task list to the storage file.
     *
     * @param taskList The task list to be saved.
     */
    public void save(TaskList taskList) {
        assert taskList != null : "TaskList should not be null";
        try (FileWriter fw = new FileWriter(file)) {
            for (Task task : taskList.getAll()) {
                fw.write(task.toFileString());
                fw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new ZweeException("Error saving tasks.");
        }
    }
}