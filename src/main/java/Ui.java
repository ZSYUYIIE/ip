import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("Hello! I'm Zwee");
        System.out.println("What can I do for you?");
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }
    
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
