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

    // Tactical personality responses arrays for variety
    private static final String[] GREETINGS = {
            "Captain on deck! Teemo reporting for duty!",
            "Hut, two, three, four! Ready for tactical operations!",
            "Alpha Team ready! What's the mission today?",
            "Scout's honor - I'm here to help organize your objectives!"
    };

    private static final String[] TASK_ADDED_CONFIRMATIONS = {
            "Roger that! Mission logged and ready for execution:",
            "Copy that, commander! Target acquired and filed:",
            "Acknowledged! New objective added to tactical roster:",
            "Affirmative! Task registered in the field manual:"
    };

    private static final String[] TASK_COMPLETED_CONFIRMATIONS = {
            "Mission accomplished! Outstanding work, commander:",
            "Target eliminated! Well done on completing:",
            "Objective secured! Excellent execution of:",
            "Victory achieved! Successfully completed:"
    };

    private static final String[] TASK_DELETION_CONFIRMATIONS = {
            "Target neutralized! Removing from active roster:",
            "Mission aborted! Clearing from tactical database:",
            "Objective cancelled! Purging from the system:",
            "Standing down from mission:"
    };

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    // ========== CLI METHODS (Print to console) ==========
    public void showWelcome() {
        showLine();
        System.out.println(getRandomGreeting());
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showBye() {
        System.out.println("____________________________________");
        System.out.println("Mission complete! Teemo signing off.");
        System.out.println("Stay tactical, commander! Over and out!");
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
        if (tasks.isEmpty()) {
            System.out.println("No active missions on the roster, commander!");
            System.out.println("Standing by for new objectives...");
        } else {
            System.out.println("Current tactical overview - Active missions:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, tasks.get(i).toString());
            }
        }
        showLine();
    }

    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        System.out.println(getRandomTaskAddedConfirmation());
        System.out.printf("%s\n", task);
        System.out.printf("Total active missions: %d. All systems operational!\n", totalTasks);
        showLine();
    }

    public void showTaskMarked(Task task) {
        showLine();
        System.out.println(getRandomTaskCompletedConfirmation());
        System.out.printf("%s\n", task.toString());
        System.out.println("Ready for next assignment, commander!");
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println("Mission status updated! Returning to active duty:");
        System.out.printf("%s\n", task.toString());
        System.out.println("Task reactivated and back in the field!");
        showLine();
    }

    public void showTaskDeleted(Task task) {
        showLine();
        System.out.println(getRandomTaskDeletionConfirmation());
        System.out.printf("%s\n", task.toString());
        System.out.println("Area secured. What's the next objective?");
        showLine();
    }

    public void showError(String message) {
        showLine();
        System.out.println("TACTICAL ERROR DETECTED:");
        System.out.println(processErrorMessage(message));
        System.out.println("Please review and resubmit your command, commander!");
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
            System.out.printf("Reconnaissance complete! No missions match '%s'.\n", keyword);
            System.out.println("Try different search parameters, commander.");
        } else {
            System.out.printf("Intel gathered! Found %d mission(s) matching '%s':\n", matchTasks.size(), keyword);
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
            return "No active missions on the roster, commander!\n" +
                    "Standing by for new objectives...";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Current tactical overview - Active missions:\n");
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
        return String.format("%s\n%s\nTotal active missions: %d. All systems operational!",
                getRandomTaskAddedConfirmation(), task, count);
    }

    /**
     * Returns a formatted string for task marked confirmation.
     *
     * @param task the task that was marked as done
     * @return formatted confirmation message
     */
    public String getTaskMarkedString(Task task) {
        return String.format("%s\n%s\nReady for next assignment, commander!",
                getRandomTaskCompletedConfirmation(), task);
    }
    /**
     * Returns a formatted string for task unmarked confirmation.
     *
     * @param task the task that was unmarked
     * @return formatted confirmation message
     */
    public String getTaskUnmarkedString(Task task) {
        return String.format("Mission status updated! Returning to active duty:\n%s\nTask reactivated and back in the field!",
                task);
    }
    /**
     * Returns a formatted string for task deletion confirmation.
     *
     * @param task the task that was deleted
     * @return formatted confirmation message
     */
    public String getTaskDeletedString(Task task) {
        return String.format("%s\n%s\nArea secured. What's the next objective?",
                getRandomTaskDeletionConfirmation(), task);
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

    // ========== PERSONALITY HELPER METHODS ==========
    private String getRandomGreeting() {
        return GREETINGS[(int) (Math.random() * GREETINGS.length)];
    }

    private String getRandomTaskAddedConfirmation() {
        return TASK_ADDED_CONFIRMATIONS[(int) (Math.random() * TASK_ADDED_CONFIRMATIONS.length)];
    }

    private String getRandomTaskCompletedConfirmation() {
        return TASK_COMPLETED_CONFIRMATIONS[(int) (Math.random() * TASK_COMPLETED_CONFIRMATIONS.length)];
    }

    private String getRandomTaskDeletionConfirmation() {
        return TASK_DELETION_CONFIRMATIONS[(int) (Math.random() * TASK_DELETION_CONFIRMATIONS.length)];
    }

    private String processErrorMessage(String message) {
        // Convert generic error messages to tactical language
        if (message.contains("don't know what that means")) {
            return "Command not recognized! Please check tactical manual for valid operations.";
        } else if (message.contains("description of a todo cannot be empty")) {
            return "Mission briefing incomplete! Todo requires tactical description.";
        } else if (message.contains("description of a deadline cannot be empty")) {
            return "Deadline mission briefing incomplete! Specify objectives and timeline.";
        } else if (message.contains("description of an event cannot be empty")) {
            return "Event operation briefing incomplete! Provide mission details and timing.";
        } else if (message.contains("Invalid task number")) {
            return "Target ID not found in tactical database! Check mission roster.";
        } else if (message.contains("Please enter a valid task number")) {
            return "Invalid target designation! Use numerical mission IDs only.";
        } else if (message.contains("Please specify a keyword to search")) {
            return "Search parameters missing! Specify reconnaissance target keywords.";
        }
        return message; // Return original message if no conversion needed
    }
}
