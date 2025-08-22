import java.util.Scanner;

public class Teemo {
    public static void main(String[] args) {
        int x = 1;
        System.out.println("____________________________________");
        System.out.println("Hello! I'm Teemo");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________");
        while (x != 0) {
            Scanner scanner = new Scanner(System.in);
            String text = scanner.nextLine();
            if (text.equals("bye")) {
                x = 0;
                System.out.println("____________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________");
                scanner.close();
            } else {
                System.out.println("____________________________________");
                System.out.println(text);
                System.out.println("____________________________________");
            }
        }
    }
}
