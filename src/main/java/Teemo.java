import java.util.Scanner;
import java.util.ArrayList;

public class Teemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("____________________________________");
        System.out.println("Hello! I'm Teemo");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________");
        String input;
        while (!(input = scanner.nextLine()).equals("bye")) {
            if (input.equals("list")) {
                System.out.println("____________________________________");
                for (int i = 0; i < list.size(); i++) {
                    System.out.printf("%d. %s\n", i+1, list.get(i));
                }
                System.out.println("____________________________________");

            } else {
                list.add(input);
                System.out.println("____________________________________");
                System.out.println("added: " + input);
                System.out.println("____________________________________");
            }
        }
        System.out.println("____________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________");
        scanner.close();
    }
}
