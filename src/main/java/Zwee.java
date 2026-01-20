import java.util.Scanner;

public class Zwee {
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
            System.out.println(input);
        }
    }
}
