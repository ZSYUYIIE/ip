import java.util.Scanner;

public class Zwee {
    private static TasksList taskList = new TasksList();
    private static void printTasks(Task task) {
        if (taskList.getTaskCount() == 1) {
            System.out.println("Got it. I've added this task:\n  " + task
            + "\nNow you have " + taskList.getTaskCount() + " task in the list.");
            return;
        } else {
            System.out.println("Got it. I've added this task:\n  " + task
            + "\nNow you have " + taskList.getTaskCount() + " tasks in the list.");
            return;
        }
    }
    public static void main(String[] args) {
        boolean isExit = false;
        System.out.println("Hello! I'm Zwee\n" +
                "What can I do for you?\n"
            );

        Storage storage = new Storage();
        
        taskList = storage.loadTasks();
        for (int i = 0; i < taskList.getTaskCount(); i++) {
            System.out.println(taskList.getTask(i));
        }

        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            String input = scanner.nextLine().trim();
            switch (input) {
                case "bye" -> {
                    System.out.println("Bye. Hope to see you again soon!");
                    isExit = true;
                    scanner.close();
                    return;
                }
                case "list" -> {
                    System.out.println("Here are the tasks in your list: ");
                    for (int i = 0; i < taskList.getTaskCount(); i++) {
                        System.out.println((i + 1) + ". " + taskList.getTask(i));
                    }
                    continue;
                }
            }

            switch (input.split(" ")[0]) {
                case "mark" -> {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    try {
                        if (taskNumber <= 0 || taskNumber > taskList.getTaskCount()) {
                            throw new IndexOutOfBoundsException();
                        }
                        if (taskNumber > 0 && taskNumber <= taskList.getTaskCount()) {
                            taskList.mark(taskNumber);
                            System.out.println("Nice! I've marked this task as done:\n  " + taskList.getTask(taskNumber - 1));
                            storage.saveTasks(taskList);
                        } 
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("OOPS!!! The task number you entered is invalid.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    continue;
                }

                case "unmark" -> {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    try {
                        if (taskNumber <= 0 || taskNumber > taskList.getTaskCount()) {
                            throw new IndexOutOfBoundsException();
                        }
                        if (taskNumber > 0 && taskNumber <= taskList.getTaskCount()) {
                            taskList.unmark(taskNumber);
                            System.out.println("OK, I've marked this task as not done yet:\n  " + taskList.getTask(taskNumber - 1));
                            storage.saveTasks(taskList);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("OOPS!!! The task number you entered is invalid.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    continue;
                }
                case "todo" -> {
                    if (input.length() <= 5 || input.substring(5).trim().isEmpty()) {
                        System.out.println("OOPS!!! The description of a todo cannot be empty.");
                        continue;
                    }
                    String description = input.substring(5).trim();
                    Task task = new Todo(description);
                    taskList.addTask(task);
                    storage.saveTasks(taskList);
                    printTasks(task);
                    continue;
                }
                case "deadline" -> {
                    if (input.length() <= 9 || input.substring(9).trim().isEmpty()) {
                        System.out.println("OOPS!!! The description of a deadline cannot be empty.");
                        continue;
                    }
                    if (!input.contains(" /by ")) {
                        System.out.println("OOPS!!! The deadline must have a /by date.");
                        continue;
                    }
                    String[] parts = input.substring(9).split(" /by ");
                    String description = parts[0].trim();
                    String date = parts[1].trim();
                    Task task = new Deadline(description, date);
                    taskList.addTask(task);
                    storage.saveTasks(taskList);
                    printTasks(task);
                    continue;
                }
                case "event" -> {
                    if (input.length() <= 6 || input.substring(6).trim().isEmpty()) {
                        System.out.println("OOPS!!! The description of an event cannot be empty.");
                        continue;
                    }
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        System.out.println("OOPS!!! The event must have a /from and /to date.");
                        continue;
                    }
                    if (input.indexOf(" /to ") < input.indexOf(" /from ")) {
                        System.out.println("OOPS!!! The /to date must come after the /from date.");
                        continue;
                    }
                    String[] parts = input.substring(6).split(" /from ");
                    String description = parts[0].trim();
                    String Dates = parts[1].trim();
                    String startDate = Dates.split(" /to ")[0].trim();
                    String endDate = parts[1].split(" /to ")[1].trim();
                    Task task = new Event(description, startDate, endDate);
                    taskList.addTask(task);
                    storage.saveTasks(taskList);
                    printTasks(task);
                    continue;
                }
                case "delete" -> {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    try {
                        if (taskNumber <= 0 || taskNumber > taskList.getTaskCount()) {
                            throw new IndexOutOfBoundsException();
                        } 
                        Task removedTask = taskList.getTask(taskNumber - 1);
                        taskList.deleteTask(taskNumber);
                        System.out.println("Noted. I've removed this task:\n  " + removedTask
                                + "\nNow you have " + taskList.getTaskCount() + " tasks in the list.");
                        storage.saveTasks(taskList);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("OOPS!!! The task number you entered is invalid.");
                    } catch (NumberFormatException e) {
                        System.out.println("OOPS!!! Please enter a valid task number to delete.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    continue;
                }
                default -> {
                    System.out.println("OOPS!!! Please enter a valid command among todo, deadline, event, mark, unmark, list, bye.");
                }
            }   
        }
        scanner.close();
    }
}