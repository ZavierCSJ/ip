package teemo;

import java.util.ArrayList;
import teemo.task.Task;
import teemo.task.Todo;
import teemo.task.Deadline;
import teemo.task.Event;


public class Teemo {

    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("ip/data/teemo.txt");
        ArrayList<Task> loadedTasks = storage.loadTasks();
        TaskList tasks = new TaskList(loadedTasks);

        ui.showWelcome();

        String input;
        while (!(input = ui.readCommand()).equals("bye")) {
            try {
                if (input.equals("list")) {
                    ui.showTaskList(tasks.getTasks());
                } else if (input.startsWith("mark")) {
                    if (input.trim().equals("mark")) {
                        throw new TeemoException("Nothing marked!");
                    }
                    int index = Integer.parseInt(input.substring(5));
                    if (!tasks.isValidIndex(index)) {
                        throw new TeemoException("Invalid task number!");
                    }
                    tasks.markTask(index);
                    storage.saveTasks(tasks.getTasks());
                    ui.showTaskMarked(tasks.get(index));
                } else if (input.startsWith("unmark")) {
                    if (input.trim().equals("unmark")) {
                        throw new TeemoException("Nothing unmarked!");
                    }
                    int index = Integer.parseInt(input.substring(7));
                    if (!tasks.isValidIndex(index)) {
                        throw new TeemoException("Invalid task number!");
                    }
                    tasks.unmarkTask(index);
                    storage.saveTasks(tasks.getTasks());
                    ui.showTaskUnmarked(tasks.get(index));
                } else if (input.startsWith("delete")) {
                    if (input.trim().equals("delete")) {
                        throw new TeemoException("Try again!");
                    }
                    int index = Integer.parseInt(input.substring(7));
                    if (!tasks.isValidIndex(index)) {
                        throw new TeemoException("Invalid task number!");
                    }
                    Task deletedTask = tasks.get(index);
                    tasks.delete(index);
                    storage.saveTasks(tasks.getTasks());
                    ui.showTaskDeleted(deletedTask);
                } else {
                    if (input.startsWith("todo")) {
                        if (input.trim().equals("todo")) {
                            throw new TeemoException("OOPS!!! The description of a todo cannot be empty");
                        }
                        String desc = input.substring(5).trim();
                        Todo newTodo = new Todo(desc);
                        tasks.add(newTodo);
                        storage.saveTasks(tasks.getTasks());
                        ui.showTaskAdded(newTodo, tasks.size());
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
                        String deadline = parts[1].trim();
                        try {
                            Deadline newDeadline = new Deadline(desc, deadline);
                            tasks.add(newDeadline);
                            storage.saveTasks(tasks.getTasks());
                            ui.showTaskAdded(newDeadline, tasks.size());
                        } catch (RuntimeException e) {
                            throw new TeemoException(e.getMessage());
                        }

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
                        try {
                            Event newEvent = new Event(desc, start, end);
                            tasks.add(newEvent);
                            storage.saveTasks(tasks.getTasks());
                            ui.showTaskAdded(newEvent, tasks.size());
                        } catch (RuntimeException e) {
                            throw new TeemoException(e.getMessage());
                        }
                    } else {
                        throw new TeemoException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }
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
}
