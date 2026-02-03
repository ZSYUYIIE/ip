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

public class Storage {

    private final File file;

    public Storage(String path) {
        this.file = new File(path);
        ensureExists();
    }

    private void ensureExists() {
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

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();

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

    public void save(TaskList taskList) {
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