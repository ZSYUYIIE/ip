import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
    private static final Path FILE_PATH = Paths.get(
        System.getProperty("user.home"), "data", "duke.txt");

    public void saveTasks(TasksList tasksList) {
        try {
            Files.createDirectories(FILE_PATH.getParent());
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH.toFile()));
            try (writer) {
                for (int i = 0; i < tasksList.getTaskCount(); i++) {
                    Task task = tasksList.getTask(i);
                    writer.write(task.toFileString());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("An error occurred while saving tasks: " + e.getMessage());       
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating directories: " + e.getMessage());
        }
    }

    public TasksList loadTasks() {
        TasksList tasksList = new TasksList();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                switch (parts[0].charAt(0)) {
                    case 'T':
                        tasksList.addTask(new Todo(parts[2]));  
                        break;
                    case 'D':
                        tasksList.addTask(new Deadline(parts[2], parts[3]));
                        break;
                    case 'E':
                        tasksList.addTask(new Event(parts[2], parts[3], parts[4]));
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading tasks: " + e.getMessage());
        } 
        return tasksList;
    }
}