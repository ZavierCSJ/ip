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
                if (input.startsWith("todo")) {
                    String desc = input.substring(5).trim();
                    Todo newTodo = new Todo(desc);
                    tasks.add(newTodo);
                    System.out.println("____________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("" + newTodo);
                    System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
                    System.out.println("____________________________________");

                } else if (input.startsWith("deadline")) {
                    String taskAndDeadline = input.substring(9).trim();
                    String[] parts = taskAndDeadline.split("/by ");
                    String desc = parts[0].trim();
                    String deadline = parts[1];
                    Deadline newDeadline = new Deadline(desc, deadline);
                    tasks.add(newDeadline);
                    System.out.println("____________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("" + newDeadline);
                    System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
                    System.out.println("____________________________________");

                } else if (input.startsWith("event")) {
                    String taskAndTime = input.substring(6).trim();
                    String[] parts = taskAndTime.split("/from ");
                    String desc = parts[0].trim();
                    String time = parts[1];
                    String[] event = time.split("/to ");
                    String start = event[0].trim();
                    String end = event[1];

                    Event newEvent = new Event(desc, start, end);
                    tasks.add(newEvent);
                    System.out.println("____________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("" + newEvent);
                    System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
                    System.out.println("____________________________________");

                }
//                tasks.add(new Task(input));
//                System.out.println("____________________________________");
//                System.out.println("added: " + input);
//                System.out.println("____________________________________");
            }
        }
        System.out.println("____________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________");
        scanner.close();
    }
}
