// AI Attribution: Stream API refactoring assisted by Claude Haiku 4.5
package zwee.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * Saves a single task to the archive file.
     *
     * @param task The task to be archived.
     * @param archivePath The file path where the task will be stored.
     * @throws ZweeException If an error occurs during the archiving process.
     */
    public void saveToArchive(Task task, String archivePath) {
        File archiveFile = new File(archivePath);
        try (FileWriter fw = new FileWriter(archiveFile, true)) {
            fw.write(task.toFileString());
            fw.write(System.lineSeparator());
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
     * Uses Stream API for functional file reading and task parsing.
     * AI: Claude Haiku 4.5 helped implement Stream API pattern with br.lines() and Collectors.
     *
     * @return A list of tasks loaded from the file.
     */
    public List<Task> load() {
        assert this.file != null : "File should not be null";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Stream API: br.lines() + filter() + map() + collect() for functional file processing
            List<Task> tasks = br.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .map(Parser::parseStoredTask)
                    .collect(Collectors.toList());
            return tasks;
        } catch (IOException e) {
            throw new ZweeException("Error loading archived tasks.");
        }
    }

    /**
     * Saves the given task list to the storage file.
     * Uses Stream API for functional task serialization.
     * AI: Claude Haiku 4.5 helped implement Stream API pattern with Collectors.joining().
     *
     * @param taskList The task list to be saved.
     */
    public void save(TaskList taskList) {
        assert taskList != null : "TaskList should not be null";
        try (FileWriter fw = new FileWriter(file)) {
            // Stream API: stream() + map() + Collectors.joining() for functional file writing
            String output = taskList.getAll().stream()
                    .map(Task::toFileString)
                    .collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator()));
            fw.write(output);
        } catch (IOException e) {
            throw new ZweeException("Error saving tasks.");
        }
    }

    public File getFile() {
        return file;
    }

    /**
     * Loads tasks from the archive file.
     * Uses Stream API for functional file reading and task parsing.
     * AI: Claude Haiku 4.5 helped implement Stream API pattern with br.lines() and Collectors.
     *
     * @param archivePath The path to the archive file.
     * @return A list of archived tasks.
     */
    public List<Task> loadArchive(String archivePath) {
        File archiveFile = new File(archivePath);
        if (!archiveFile.exists()) {
            return new ArrayList<>();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archiveFile))) {
            List<Task> tasks = br.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .map(Parser::parseStoredTask)
                    .collect(Collectors.toList());
            return tasks;
        } catch (IOException e) {
            throw new ZweeException("Error loading archived tasks.");
        }
    }

    /**
     * Overwrites the archive file with the given list of tasks.
     * Uses Stream API for functional task serialization.
     * AI: Claude Haiku 4.5 helped implement Stream API pattern with Collectors.joining().
     *
     * @param tasks The list of tasks to overwrite the archive with.
     * @param archivePath The path to the archive file.
     */
    public void overwriteArchive(List<Task> tasks, String archivePath) {
        File archiveFile = new File(archivePath);
        try (FileWriter fw = new FileWriter(archiveFile)) {
            // Stream API: stream() + map() + Collectors.joining() for functional archive overwrite
            String output = tasks.stream()
                    .map(Task::toFileString)
                    .collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator()));
            fw.write(output);
        } catch (IOException e) {
            throw new ZweeException("Error saving tasks to archive.");
        }
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