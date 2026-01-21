import java.util.Scanner;

public class Zwee {
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;
    public static void main(String[] args) {

        System.out.println("Hello! I'm Zwee\n" +
                "What can I do for you?\n"
            );

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                scanner.close();
                break;
            }

            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list: ");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                continue;

            }

            if (input.split(" ")[0].equals("mark")) {
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                if (taskNumber > 0 && taskNumber <= taskCount) {
                    tasks[taskNumber - 1].mark();
                    System.out.println("Nice! I've marked this task as done:\n  " + tasks[taskNumber - 1]);
                } 
                continue;
            }

            if (input.split(" ")[0].equals("unmark")) {
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                if (taskNumber > 0 && taskNumber <= taskCount) {
                    tasks[taskNumber - 1].unmark();
                    System.out.println("OK, I've marked this task as not done yet:\n  " + tasks[taskNumber - 1]);
                } 
                continue;
            }

            tasks[taskCount] = new Task(input);
            taskCount++;
            System.out.println("added: " + input);
        }
    }
}
