import java.util.Scanner;

public class Zwee {
    private static TasksList tasklist = new TasksList();
    private static void printTasks(Task task) {
        if (tasklist.getTaskCount() == 1) {
            System.out.println("Got it. I've added this task:\n  " + task
            + "\nNow you have " + tasklist.getTaskCount() + " task in the list.");
            return;
        } else {
            System.out.println("Got it. I've added this task:\n  " + task
            + "\nNow you have " + tasklist.getTaskCount() + " tasks in the list.");
            return;
        }
    }
    public static void main(String[] args) {
        boolean isExit = false;
        System.out.println("Hello! I'm Zwee\n" +
                "What can I do for you?\n"
            );

        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            String input = scanner.nextLine();
            switch (input) {
                case "bye" -> {
                    System.out.println("Bye. Hope to see you again soon!");
                    isExit = true;
                    return;
                }
                case "list" -> {
                    System.out.println("Here are the tasks in your list: ");
                    for (int i = 0; i < tasklist.getTaskCount(); i++) {
                        System.out.println((i + 1) + ". " + tasklist.getTask(i));
                    }
                    continue;
                }
            }

            switch (input.split(" ")[0]) {
                case "mark" -> {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= tasklist.getTaskCount()) {
                        tasklist.mark(taskNumber);
                        System.out.println("Nice! I've marked this task as done:\n  " + tasklist.getTask(taskNumber - 1));
                    } 
                    continue;
                }

                case "unmark" -> {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= tasklist.getTaskCount()) {
                        tasklist.unmark(taskNumber);
                        System.out.println("OK, I've marked this task as not done yet:\n  " + tasklist.getTask(taskNumber - 1));
                    } 
                    continue;
                }
                case "todo" -> {
                    String description = input.substring(5).trim();
                    Task task = new Todo(description);
                    tasklist.addTask(task);
                    printTasks(task);
                    continue;
                }
                case "deadline" -> {
                    String[] parts = input.substring(9).split(" /by ");
                    String description = parts[0].trim();
                    String date = parts[1].trim();
                    Task task = new Deadline(description, date);
                    tasklist.addTask(task);
                    printTasks(task);
                    continue;
                }
                case "event" -> {
                    String[] parts = input.substring(6).split(" /from ");
                    String description = parts[0].trim();
                    String startDate = parts[1].trim();
                    String endDate = parts[1].split(" /to ")[1].trim();
                    Task task = new Event(description, startDate, endDate);
                    tasklist.addTask(task);
                    printTasks(task);
                    continue;
                }
            }
            tasklist.addTask(new Task(input));
            System.out.println("added: " + input);
        }
        scanner.close();
    }
}