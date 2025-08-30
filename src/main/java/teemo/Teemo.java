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
        ArrayList<Task> tasks = storage.loadTasks();

        ui.showWelcome();

        String input;
        while (!(input = ui.readCommand()).equals("bye")) {
            try {
                if (input.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (input.startsWith("mark")) {
                    if (input.trim().equals("mark")) {
                        throw new TeemoException("Nothing marked!");
                    }
                    int index = Integer.parseInt(input.substring(5));
                    if (index > tasks.size() || index <= 0) {
                        throw new TeemoException("Invalid task number!");
                    }
                    tasks.get(index - 1).markAsDone();
                    storage.saveTasks(tasks);
                    ui.showTaskMarked(tasks.get(index - 1));
                } else if (input.startsWith("unmark")) {
                    if (input.trim().equals("unmark")) {
                        throw new TeemoException("Nothing unmarked!");
                    }
                    int index = Integer.parseInt(input.substring(7));
                    if (index > tasks.size() || index <= 0) {
                        throw new TeemoException("Invalid task number!");
                    }
                    tasks.get(index - 1).unmarkAsDone();
                    storage.saveTasks(tasks);
                    ui.showTaskUnmarked(tasks.get(index - 1));
                } else if (input.startsWith("delete")) {
                    if (input.trim().equals("delete")) {
                        throw new TeemoException("Try again!");
                    }
                    int index = Integer.parseInt(input.substring(7));
                    if (index > tasks.size() || index <= 0) {
                        throw new TeemoException("Invalid task number!");
                    }
                    Task deletedTask = tasks.get(index - 1);
                    tasks.remove(index - 1);
                    storage.saveTasks(tasks);
                    ui.showTaskDeleted(deletedTask);
                } else {
                    if (input.startsWith("todo")) {
                        if (input.trim().equals("todo")) {
                            throw new TeemoException("OOPS!!! The description of a todo cannot be empty");
                        }
                        String desc = input.substring(5).trim();
                        Todo newTodo = new Todo(desc);
                        tasks.add(newTodo);
                        storage.saveTasks(tasks);
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
                            storage.saveTasks(tasks);
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
                            storage.saveTasks(tasks);
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
