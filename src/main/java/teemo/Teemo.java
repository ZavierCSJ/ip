package teemo;

import java.util.ArrayList;
import teemo.task.Task;
import teemo.task.Todo;
import teemo.task.Deadline;
import teemo.task.Event;


public class Teemo {

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

    private static void handleEventCommand(String input, TaskList tasks, Storage storage, Ui ui) throws TeemoException {
        Event event = Parser.parseEvent(input);
        tasks.add(event);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskAdded(event, tasks.size());
    }

    private static void handleDeadlineCommand(String input, TaskList tasks, Storage storage, Ui ui) throws TeemoException {
        Deadline deadline = Parser.parseDeadline(input);
        tasks.add(deadline);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskAdded(deadline, tasks.size());

    }

    private static void handleTodoCommand(String input, TaskList tasks, Storage storage, Ui ui) throws TeemoException {
        Todo todo = Parser.parseTodo(input);
        tasks.add(todo);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskAdded(todo, tasks.size());
    }

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

    private static void handleUnmarkCommand(String input, TaskList tasks, Storage storage, Ui ui) throws TeemoException {
        int index = Parser.parseTaskIndex(input, "unmark");
        if (!tasks.isValidIndex(index)) {
            throw new TeemoException("Invalid task number!");
        }
        tasks.unmarkTask(index);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskUnmarked(tasks.get(index));
    }

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
