import java.util.Scanner;
import java.util.ArrayList;

public class Teemo {

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        printWelcomeMessage();

        String input;
        while (!(input = scanner.nextLine()).equals("bye")) {
            try {
                if (input.equals("list")) {
                    System.out.println("____________________________________");
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.printf("%d.%s\n", i + 1, tasks.get(i).toString());
                    }
                    System.out.println("____________________________________");

                } else if (input.startsWith("mark")) {
                    if (input.trim().equals("mark")) {
                        throw new TeemoException("Nothing marked!");
                    }
                    int index = Integer.parseInt(input.substring(5));
                    if (index > tasks.size() || index <= 0) {
                        throw new TeemoException("Invalid task number!");
                    }
                    tasks.get(index - 1).markAsDone();
                    System.out.println("____________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(index - 1).toString());
                    System.out.println("____________________________________");
                } else if (input.startsWith("unmark")) {
                    if (input.trim().equals("unmark")) {
                        throw new TeemoException("Nothing unmarked!");
                    }
                    int index = Integer.parseInt(input.substring(7));
                    if (index > tasks.size() || index <= 0) {
                        throw new TeemoException("Invalid task number!");
                    }
                    tasks.get(index - 1).unmarkAsDone();
                    System.out.println("____________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks.get(index - 1).toString());
                    System.out.println("____________________________________");
                } else if (input.startsWith("delete")) {
                    if (input.trim().equals("delete")) {
                        throw new TeemoException("Try again!");
                    }
                    int index = Integer.parseInt(input.substring(7));
                    if (index > tasks.size() || index <= 0) {
                        throw new TeemoException("Invalid task number!");
                    }
                    System.out.println("____________________________________");
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(tasks.get(index - 1).toString());
                    System.out.println("____________________________________");
                    tasks.remove(index - 1);
                } else {
                    if (input.startsWith("todo")) {
                        if (input.trim().equals("todo")) {
                            throw new TeemoException("OOPS!!! The description of a todo cannot be empty");
                        }
                        String desc = input.substring(5).trim();
                        Todo newTodo = new Todo(desc);
                        tasks.add(newTodo);
                        System.out.println("____________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println("" + newTodo);
                        System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
                        System.out.println("____________________________________");
                    } else if (input.startsWith("deadline")) {
                        if (input.trim().equals("deadline")) {
                            throw new TeemoException("OOPS!!! The description of a deadline cannot be empty");
                        }
                        String taskAndDeadline = input.substring(9).trim();
                        if (!taskAndDeadline.contains("/by")) {
                            throw new TeemoException("Need a deadline! -> (deadline [description] /by [date])");
                        }
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
                        if (input.trim().equals("event")) {
                            throw new TeemoException("OOPS!!! The description of a event cannot be empty");
                        }
                        String taskAndTime = input.substring(6).trim();
                        if (!(taskAndTime.contains("/from") && taskAndTime.contains("/to"))) {
                            throw new TeemoException("Invalid format! -> (event [description] /from [start] /to [end])");
                        }
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

                    } else {
                        throw new TeemoException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("____________________________________");
                System.out.println("Please enter a valid task number.");
                System.out.println("____________________________________");
            } catch (TeemoException e) {
                System.out.println("____________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________");
            }
        }
        printByeMessage();
        scanner.close();
    }

    private static void printWelcomeMessage() {
        System.out.println("____________________________________");
        System.out.println("Hello! I'm Teemo");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________");
    }

    private static void printByeMessage() {
        System.out.println("____________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________");
    }
}
