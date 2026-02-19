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
     * Appends a list of tasks to the specified archive file.
     *
     * @param tasks The list of tasks to be archived.
     * @param archivePath The file path where tasks will be stored.
     * @throws ZweeException If an error occurs during the archiving process.
     */
    public void archiveTasks(TaskList tasks, String archivePath) {
        File archiveFile = new File(archivePath);
        try (FileWriter fw = new FileWriter(archiveFile, true)) { 
            for (Task task : tasks.getAll()) {
                fw.write(task.toFileString());
                fw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new ZweeException("Failed to write to archive file.");
        }
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
     * Appends a single task to the archive file.
     * Note: Uses String path to ensure we don't accidentally write to the main storage.
     */
    public void saveToArchive(Task task, String archivePath) {
        File archiveFile = new File(archivePath);
        // Ensure archive file exists
        if (archiveFile.getParentFile() != null) {
            archiveFile.getParentFile().mkdirs();
        }
        try {
            archiveFile.createNewFile();
            try (FileWriter fw = new FileWriter(archiveFile, true)) { // true = append
                fw.write(task.toFileString());
                fw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new ZweeException("Error saving to archive.");
        }
    }

    /**
     * Overwrites the archive file with a new list (used when an item is removed/unarchived).
     */
    public void overwriteArchive(List<Task> tasks, String archivePath) {
        File archiveFile = new File(archivePath);
        try (FileWriter fw = new FileWriter(archiveFile)) { // No 'true' = overwrite
            for (Task task : tasks) {
                fw.write(task.toFileString());
                fw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new ZweeException("Error updating archive file.");
        }
    }

    /**
     * Loads tasks from the archive file.
     */
    public List<Task> loadArchive(String archivePath) {
        List<Task> tasks = new ArrayList<>();
        File archiveFile = new File(archivePath);
        if (!archiveFile.exists()) {
            return tasks;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archiveFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    tasks.add(Parser.parseStoredTask(line));
                }
            }
        } catch (IOException e) {
            throw new ZweeException("Error loading archived tasks.");
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

    public File getFile() {
        return file;
    }

    /**
     * Clears all tasks from the archive file.
     */
    public void clearArchive() {
        File archiveFile = new File("./data/archive.txt");
        try (FileWriter fw = new FileWriter(archiveFile)) {
            fw.write("");
        } catch (IOException e) {
            throw new ZweeException("Error clearing archive.");
        }
    }
}