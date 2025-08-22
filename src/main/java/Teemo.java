import java.util.Scanner;
import java.util.ArrayList;

public class Teemo {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("____________________________________");
        System.out.println("Hello! I'm Teemo");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________");
        String input;
        while (!(input = scanner.nextLine()).equals("bye")) {
            if (input.equals("list")) {
                System.out.println("____________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.printf("%d.%s\n", i+1, tasks.get(i).toString());
                }
                System.out.println("____________________________________");

            } else if (input.startsWith("mark")) {
                try {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    tasks.get(index).markAsDone();
                    System.out.println("____________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(index).toString());
                    System.out.println("____________________________________");
                } catch (Exception e) {
                    System.out.println("Invalid task number!");
                }
            } else if (input.startsWith("unmark")) {
                try {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    tasks.get(index).unmarkAsDone();
                    System.out.println("____________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks.get(index).toString());
                    System.out.println("____________________________________");
                } catch (Exception e) {
                    System.out.println("Invalid task number!");
                }
            } else {
                tasks.add(new Task(input));
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
