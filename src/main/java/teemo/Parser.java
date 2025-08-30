package teemo;

import teemo.task.Todo;
import teemo.task.Deadline;
import teemo.task.Event;

/**
 * Handles parsing of user input commands into executable actions.
 * Supports parsing of todo, deadline, event, mark, unmark, delete, list, and find commands.
 */
public class Parser {

    public static String parseCommand(String input) {
        return input.trim().split("\\s+")[0];
    }

    public static int parseTaskIndex(String input, String command) throws TeemoException {
        if (input.trim().equals(command)) {
            throw new TeemoException("Please specify a task number!");
        }
        try {
            return Integer.parseInt(input.substring(command.length()).trim());
        } catch (NumberFormatException e) {
            throw new TeemoException("Please enter a valid task number.");
        }
    }

    /**
     * Parses a todo command and creates a Todo task.
     *
     * @param input the user input string containing the todo command
     * @return a Todo object with the specified description
     * @throws TeemoException if the todo description is empty
     */
    public static Todo parseTodo(String input) throws TeemoException {
        if (input.trim().equals("todo")) {
            throw new TeemoException("OOPS!!! The description of a todo cannot be empty");
        }
        String description = input.substring(5).trim();
        return new Todo(description);
    }

    /**
     * Parses a deadline command and creates a Deadline task.
     *
     * @param input the user input string containing the deadline command and due date
     * @return a Deadline object with the specified description and due date
     * @throws TeemoException if the deadline format is invalid or missing /by
     */
    public static Deadline parseDeadline(String input) throws TeemoException {
        if (input.trim().equals("deadline")) {
            throw new TeemoException("OOPS!!! The description of a deadline cannot be empty");
        }
        String taskAndDeadline = input.substring(9).trim();
        if (!taskAndDeadline.contains("/by")) {
            throw new TeemoException("Need a deadline! -> (deadline [description] /by [date])");
        }
        String[] parts = taskAndDeadline.split("/by ");
        String description = parts[0].trim();
        String deadline = parts[1].trim();

        try {
            return new Deadline(description, deadline);
        } catch (RuntimeException e) {
            throw new TeemoException(e.getMessage());
        }
    }

    /**
     * Parses a event command and creates a Event task.
     *
     * @param input the user input string containing the event command and start to end date
     * @return a Event object with the specified description and event information
     * @throws TeemoException if the deadline format is invalid or missing /from or /to
     */
    public static Event parseEvent(String input) throws TeemoException {
        if (input.trim().equals("event")) {
            throw new TeemoException("OOPS!!! The description of an event cannot be empty");
        }
        String taskAndTime = input.substring(6).trim();
        if (!(taskAndTime.contains("/from") && taskAndTime.contains("/to"))) {
            throw new TeemoException("Invalid format! -> (event [description] /from [start] /to [end])");
        }
        String[] parts = taskAndTime.split("/from ");
        String description = parts[0].trim();
        String time = parts[1];
        String[] eventTimes = time.split("/to ");
        String start = eventTimes[0].trim();
        String end = eventTimes[1].trim();

        try {
            return new Event(description, start, end);
        } catch (RuntimeException e) {
            throw new TeemoException(e.getMessage());
        }
    }
}
