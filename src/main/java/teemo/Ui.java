package teemo;

import java.util.Scanner;
import java.util.ArrayList;
import teemo.task.Task;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm Teemo");
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showBye() {
        System.out.println("____________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________");
    }

    public void showLine() {
        System.out.println("____________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showTaskList(ArrayList<Task> tasks) {
        showLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d.%s\n", i + 1, tasks.get(i).toString());
        }
        showLine();
    }

    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("" + task);
        System.out.printf("Now you have %d tasks in the list.\n", totalTasks);
        showLine();
    }

    public void showTaskMarked(Task task) {
        showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.toString());
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task.toString());
        showLine();
    }

    public void showTaskDeleted(Task task) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println(task.toString());
        showLine();
    }

    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks from file. Starting with empty task list.");
    }

    public void close() {
        scanner.close();
    }

    public void showFindResults(ArrayList<Task> matchTasks, String keyword) {
        showLine();
        if (matchTasks.isEmpty()) {
            System.out.println("No matching tasks found for: " + keyword);
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchTasks.size(); i++) {
                System.out.println((i + 1) + ". " + matchTasks.get(i));
            }
        }
    }
}
