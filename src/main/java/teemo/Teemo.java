package teemo;

import java.util.ArrayList;
import teemo.task.Task;
import teemo.task.Todo;
import teemo.task.Deadline;
import teemo.task.Event;

/**
 * Main class for the Teemo task management application.
 *
 * <p>Teemo provides both CLI and GUI for managing personal tasks including
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

    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    /**
     * Initializes Teemo with the given storage file path.
     *
     * @param filePath the path to the data file for task storage
     */
    public Teemo(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        ArrayList<Task> loadedTasks = storage.loadTasks();
        tasks = new TaskList(loadedTasks);
    }

    /**
     * Teemo CLI application.
     *
     * <p>Initializes the user interface, storage system, and task list, then
     * starts the main command processing loop. The application continues to
     * process user commands until the user enters "bye" to exit.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Teemo teemo = new Teemo("data/teemo.txt");
        teemo.ui.showWelcome();

        String input;
        while (!(input = teemo.ui.readCommand()).equals("bye")) {
            try {
                String command = Parser.parseCommand(input);
                switch (command) {
                case "list":
                    teemo.ui.showTaskList(teemo.tasks.getTasks());
                    break;
                case "mark":
                    teemo.handleMarkCommand(input);
                    break;
                case "unmark":
                    teemo.handleUnmarkCommand(input);
                    break;
                case "delete":
                    teemo.handleDeleteCommand(input);
                    break;
                case "todo":
                    teemo.handleTodoCommand(input);
                    break;
                case "deadline":
                    teemo.handleDeadlineCommand(input);
                    break;
                case "event":
                    teemo.handleEventCommand(input);
                    break;
                case "find":
                    teemo.handleFindCommand(input);
                    break;
                default:
                    throw new TeemoException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (NumberFormatException e) {
                teemo.ui.showError("Please enter a valid task number.");
            } catch (TeemoException e) {
                teemo.ui.showError(e.getMessage());
            }
        }
        teemo.ui.showBye();
        teemo.ui.close();
    }

    /**
     * Handles the creation of event tasks from user input.
     *
     * <p>Parses the event command, creates a new Event task, adds it to the
     * task list, persists the updated task list, and displays confirmation
     * to the user.
     *
     * @param input the user input string containing the event command
     * @throws TeemoException if the event command format is invalid
     */
    private void handleEventCommand(String input) throws TeemoException {
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
     * @throws TeemoException if the deadline command format is invalid
     */
    private void handleDeadlineCommand(String input) throws TeemoException {
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
     * @throws TeemoException if the todo command format is invalid
     */
    private void handleTodoCommand(String input) throws TeemoException {
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
     * @throws TeemoException if the task index is invalid or out of range
     */
    private void handleDeleteCommand(String input) throws TeemoException {
        performTaskAction(input, "delete",
                (tl, idx) -> {
                    Task t = tl.get(idx);
                    tl.delete(idx);
                    return t;
                },
                (u, t) -> u.showTaskDeleted(t)
        );
    }

    /**
     * Handles unmarking tasks as incomplete from user input.
     *
     * <p>Parses the unmark command to extract the task index, validates the index,
     * marks the specified task as incomplete, persists the updated list, and
     * displays confirmation to the user.
     *
     * @param input the user input string containing the unmark command
     * @throws TeemoException if the task index is invalid or out of range
     */
    private void handleUnmarkCommand(String input) throws TeemoException {
        performTaskAction(input, "unmark",
                (tl, idx) -> {
                    tl.unmarkTask(idx);
                    return null;
                },
                (u, t) -> u.showTaskUnmarked(t)
        );
    }

    /**
     * Handles marking tasks as completed from user input.
     *
     * <p>Parses the mark command to extract the task index, validates the index,
     * marks the specified task as completed, persists the updated list, and
     * displays confirmation to the user.
     *
     * @param input the user input string containing the mark command
     * @throws TeemoException if the task index is invalid or out of range
     */
    private void handleMarkCommand(String input) throws TeemoException {
        performTaskAction(input, "mark",
                (tl, idx) -> {
                    tl.markTask(idx);
                    return null;
                },
                (u, t) -> u.showTaskMarked(t)
        );
    }

    private void handleFindCommand(String input) throws TeemoException {
        String keyword = Parser.parseFind(input);
        ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
        ui.showFindResults(matchingTasks, keyword);
    }

    /**
     * Processes user input and returns the response string for GUI interaction.
     * This method handles all commands (list, mark, unmark, delete, todo, deadline, event, find)
     * and returns formatted responses for display in the GUI.
     *
     * @param input the raw user input string
     * @return a formatted response string to be displayed, or an error message
     * @throws NumberFormatException if a task index is not a valid number
     * @throws TeemoException if the command format is invalid or task index out of bounds
     */
    public String getResponse(String input) {
        try {
            String command = Parser.parseCommand(input);

            switch (command) {
            case "list":
                return ui.getTaskListString(tasks.getTasks());

            case "find":
                String keyword = Parser.parseFind(input);
                ArrayList<Task> foundTasks = tasks.findTasks(keyword);
                return ui.getFindResultsString(foundTasks, keyword);

            case "todo":
                Todo todo = Parser.parseTodo(input);
                tasks.add(todo);
                storage.saveTasks(tasks.getTasks());
                return ui.getTaskAddedString(todo, tasks.size());

            case "deadline":
                Deadline deadline = Parser.parseDeadline(input);
                tasks.add(deadline);
                storage.saveTasks(tasks.getTasks());
                return ui.getTaskAddedString(deadline, tasks.size());

            case "event":
                Event event = Parser.parseEvent(input);
                tasks.add(event);
                storage.saveTasks(tasks.getTasks());
                return ui.getTaskAddedString(event, tasks.size());

            case "mark":
                return handleTaskAction(input, "mark",
                        (tl, idx) -> {
                            tl.markTask(idx);
                            return null;
                        },
                        (u, t) -> u.getTaskMarkedString(t)
                );

            case "unmark":
                return handleTaskAction(input, "unmark",
                        (tl, idx) -> {
                            tl.unmarkTask(idx);
                            return null;
                        },
                        (u, t) -> u.getTaskUnmarkedString(t)
                );

            case "delete":
                return handleTaskAction(input, "delete",
                        (tl, idx) -> {
                            Task t = tl.get(idx);
                            tl.delete(idx);
                            return t;
                        },
                        (u, t) -> u.getTaskDeletedString(t)
                );

            default:
                return "OOPS!!! I'm sorry, but I don't know what that means :-(";
            }
        } catch (NumberFormatException e) {
            return "Please enter a valid task number.";
        } catch (TeemoException e) {
            return e.getMessage();
        }
    }

    /**
     * Helper method to safely perform actions on a task by index.
     * Reusable for mark, unmark, delete operations.
     *
     * @param input          raw user input line
     * @param command        the command name (e.g., "mark", "delete")
     * @param actionHandler  function to apply to the task list (e.g., markTask, delete)
     * @param displayAction  function to display result to UI (e.g., showTaskMarked)
     * @throws TeemoException if task index is invalid or command format incorrect
     */
    private void performTaskAction(String input, String command,
                                   TaskList.ActionHandler actionHandler,
                                   TaskList.ActionDisplay displayAction) throws TeemoException {
        int index = Parser.parseTaskIndex(input, command);
        if (!tasks.isValidIndex(index)) {
            throw new TeemoException("Invalid task number!");
        }

        Task task = actionHandler.handle(tasks, index);
        storage.saveTasks(tasks.getTasks());

        displayAction.show(ui, task != null ? task : tasks.get(index));
    }

    /**
     * Helper method for GUI mode to handle task actions and return a string response.
     * Wrapper around performTaskAction() for use with getResponse().
     *
     * @param input          raw user input line
     * @param command        the command name (e.g., "mark", "delete")
     * @param actionHandler  function to apply to the task list
     * @param displayAction  function to return a formatted string response
     * @return formatted response string
     * @throws TeemoException if task index is invalid or command format incorrect
     */
    private String handleTaskAction(String input, String command,
                                    TaskList.ActionHandler actionHandler,
                                    TaskList.StringDisplay displayAction) throws TeemoException {
        int index = Parser.parseTaskIndex(input, command);
        if (!tasks.isValidIndex(index)) {
            throw new TeemoException("Invalid task number!");
        }

        Task task = actionHandler.handle(tasks, index);
        storage.saveTasks(tasks.getTasks());

        return displayAction.getString(ui, task != null ? task : tasks.get(index));
    }
}

