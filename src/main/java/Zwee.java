import java.util.Scanner;

public class Zwee {
    private static String[] tasks = new String[100];
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
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                continue;

            }
            tasks[taskCount] = input;
            taskCount++;
            System.out.println("added: " + input);
        }
    }
}
