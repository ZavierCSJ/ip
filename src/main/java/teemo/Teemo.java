package teemo;

import java.util.ArrayList;
import teemo.task.Task;
import teemo.task.Todo;
import teemo.task.Deadline;
import teemo.task.Event;

/**
 * Main class for the Teemo task management application.
 *
 * <p>Teemo provides a command-line interface for managing personal tasks including
 * todos, deadlines, and events. The application supports persistent storage,
 * allowing tasks to be saved and loaded between sessions.
 *
 * <p>Key features include:
 * <ul>
 *   <li>Creating and managing todo tasks</li>
 *   <li>Setting deadlines with due dates</li>
 *   <li>Scheduling events with time periods</li>
 *   <li>Marking tasks as completed or incomplete</li>
 *   <li>Deleting unwanted tasks</li>
 *   <li>Persistent file-based storage</li>
 * </ul>
 *
 * @author ZavierCSJ
 * @version 0.1
 */
public class Teemo {

    /**
     * Entry point of the Teemo application.
     *
     * <p>Initializes the user interface, storage system, and task list, then
     * starts the main command processing loop. The application continues to
     * process user commands until the user enters "bye" to exit.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("data/teemo.txt");
        ArrayList<Task> loadedTasks = storage.loadTasks();
        TaskList tasks = new TaskList(loadedTasks);

        ui.showWelcome();

        String input;
        while (!(input = ui.readCommand()).equals("bye")) {
            try {
                String command = Parser.parseCommand(input);
                switch (command) {
                case "list":
                    ui.showTaskList(tasks.getTasks());
                    break;
                case "mark":
                    handleMarkCommand(input, tasks, storage, ui);
                    break;
                case "unmark":
                    handleUnmarkCommand(input, tasks, storage, ui);
                    break;
                case "delete":
                    handleDeleteCommand(input, tasks, storage, ui);
                    break;
                case "todo":
                    handleTodoCommand(input, tasks, storage, ui);
                    break;
                case "deadline":
                    handleDeadlineCommand(input, tasks, storage, ui);
                    break;
                case "event":
                    handleEventCommand(input, tasks, storage, ui);
                    break;
                default:
                    throw new TeemoException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (NumberFormatException e) {
                ui.showError("Please enter a valid task number.");
            } catch (TeemoException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.showBye();
        ui.close();
    }

    /**
     * Handles the creation of event tasks from user input.
     *
     * <p>Parses the event command, creates a new Event task, adds it to the
     * task list, persists the updated task list, and displays confirmation
     * to the user.
     *
     * @param input the user input string containing the event command
     * @param tasks the TaskList to add the event to
     * @param storage the Storage instance for persisting tasks
     * @param ui the Ui instance for user interaction
     * @throws TeemoException if the event command format is invalid
     */
    private static void handleEventCommand(String input, TaskList tasks, Storage storage, Ui ui) throws TeemoException {
        Event event = Parser.parseEvent(input);
        tasks.add(event);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskAdded(event, tasks.size());
    }

    /**
     * Handles the creation of deadline tasks from user input.
     *
     * <p>Parses the deadline command, creates a new Deadline task, adds it to the
     * task list, persists the updated task list, and displays confirmation
     * to the user.
     *
     * @param input the user input string containing the deadline command
     * @param tasks the TaskList to add the deadline to
     * @param storage the Storage instance for persisting tasks
     * @param ui the Ui instance for user interaction
     * @throws TeemoException if the deadline command format is invalid
     */
    private static void handleDeadlineCommand(String input, TaskList tasks, Storage storage, Ui ui) throws TeemoException {
        Deadline deadline = Parser.parseDeadline(input);
        tasks.add(deadline);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskAdded(deadline, tasks.size());

    }

    /**
     * Handles the creation of todo tasks from user input.
     *
     * <p>Parses the todo command, creates a new Todo task, adds it to the
     * task list, persists the updated task list, and displays confirmation
     * to the user.
     *
     * @param input the user input string containing the todo command
     * @param tasks the TaskList to add the todo to
     * @param storage the Storage instance for persisting tasks
     * @param ui the Ui instance for user interaction
     * @throws TeemoException if the todo command format is invalid
     */
    private static void handleTodoCommand(String input, TaskList tasks, Storage storage, Ui ui) throws TeemoException {
        Todo todo = Parser.parseTodo(input);
        tasks.add(todo);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskAdded(todo, tasks.size());
    }

    /**
     * Handles the deletion of tasks from user input.
     *
     * <p>Parses the delete command to extract the task index, validates the index,
     * removes the task from the task list, persists the updated list, and
     * displays confirmation to the user.
     *
     * @param input the user input string containing the delete command
     * @param tasks the TaskList to delete the task from
     * @param storage the Storage instance for persisting tasks
     * @param ui the Ui instance for user interaction
     * @throws TeemoException if the task index is invalid or out of range
     */
    private static void handleDeleteCommand(String input, TaskList tasks, Storage storage, Ui ui) throws TeemoException {
        int index = Parser.parseTaskIndex(input, "delete");
        if (!tasks.isValidIndex(index)) {
            throw new TeemoException("Invalid task number!");
        }
        Task deletedTask = tasks.get(index);
        tasks.delete(index);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskDeleted(deletedTask);
    }

    /**
     * Handles unmarking tasks as incomplete from user input.
     *
     * <p>Parses the unmark command to extract the task index, validates the index,
     * marks the specified task as incomplete, persists the updated list, and
     * displays confirmation to the user.
     *
     * @param input the user input string containing the unmark command
     * @param tasks the TaskList containing the task to unmark
     * @param storage the Storage instance for persisting tasks
     * @param ui the Ui instance for user interaction
     * @throws TeemoException if the task index is invalid or out of range
     */
    private static void handleUnmarkCommand(String input, TaskList tasks, Storage storage, Ui ui) throws TeemoException {
        int index = Parser.parseTaskIndex(input, "unmark");
        if (!tasks.isValidIndex(index)) {
            throw new TeemoException("Invalid task number!");
        }
        tasks.unmarkTask(index);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskUnmarked(tasks.get(index));
    }

    /**
     * Handles marking tasks as completed from user input.
     *
     * <p>Parses the mark command to extract the task index, validates the index,
     * marks the specified task as completed, persists the updated list, and
     * displays confirmation to the user.
     *
     * @param input the user input string containing the mark command
     * @param tasks the TaskList containing the task to mark
     * @param storage the Storage instance for persisting tasks
     * @param ui the Ui instance for user interaction
     * @throws TeemoException if the task index is invalid or out of range
     */
    private static void handleMarkCommand(String input, TaskList tasks, Storage storage, Ui ui) throws TeemoException {
        int index = Parser.parseTaskIndex(input, "mark");
        if (!tasks.isValidIndex(index)) {
            throw new TeemoException("Invalid task number!");
        }
        tasks.markTask(index);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskMarked(tasks.get(index));
    }
}
