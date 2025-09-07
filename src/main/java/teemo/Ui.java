package teemo;

import java.util.Scanner;
import java.util.ArrayList;
import teemo.task.Task;

/**
 * Handles user interactions for Teemo.
 * Provides methods to display messages for both CLI and GUI.
 */
public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    // ========== CLI METHODS (Print to console) ==========
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
        showLine();
    }

    // ========== GUI METHODS (return formatted strings) ==========
    /**
     * Returns a formatted string of all tasks for GUI display.
     *
     * @param tasks the list of tasks to display
     * @return formatted string containing all tasks
     */
    public String getTaskListString(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "No tasks in your list yet!";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Returns a formatted string for task addition confirmation.
     *
     * @param task the task that was added
     * @param count the total number of tasks
     * @return formatted confirmation message
     */
    public String getTaskAddedString(Task task, int count) {
        return "Got it. I've added this task:\n  " + task +
                "\nNow you have " + count + " tasks in the list.";
    }

    /**
     * Returns a formatted string for task marked confirmation.
     *
     * @param task the task that was marked as done
     * @return formatted confirmation message
     */
    public String getTaskMarkedString(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Returns a formatted string for task unmarked confirmation.
     *
     * @param task the task that was unmarked
     * @return formatted confirmation message
     */
    public String getTaskUnmarkedString(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Returns a formatted string for task deletion confirmation.
     *
     * @param task the task that was deleted
     * @return formatted confirmation message
     */
    public String getTaskDeletedString(Task task) {
        return "Noted. I've removed this task:\n  " + task;
    }

    /**
     * Returns a formatted string of find results for GUI display.
     *
     * @param matchingTasks the list of tasks that match the search
     * @param keyword the search keyword used
     * @return formatted string containing search results
     */
    public String getFindResultsString(ArrayList<Task> matchingTasks, String keyword) {
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found for: " + keyword;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}
