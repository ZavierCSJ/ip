package teemo;

import java.util.ArrayList;
import teemo.task.Task;

/**
 * A list of tasks using 1-based indexing for public methods.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        assert task != null : "Cannot add null task to list";
        tasks.add(task);
    }

    public void delete(int index) {
        assert index >= 1 : "Delete index cannot be negative";
        assert index <= tasks.size() : "Delete index out of bounds";
        tasks.remove(index - 1);
    }

    public Task get(int index) {
        assert index >= 1 && index <= tasks.size() : "Invalid task index: " + index;
        return tasks.get(index - 1);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public boolean isValidIndex(int index) {
        return index >= 1 && index <= tasks.size();
    }

    public void markTask(int index) {
        assert index >= 1 : "Task index cannot be negative";
        assert index <= tasks.size() : "Task index " + index + " is out of bounds, list size: " + tasks.size();
        tasks.get(index - 1).markAsDone();
    }

    public void unmarkTask(int index) {
        assert index >= 1 : "Task index cannot be negative";
        assert index <= tasks.size() : "Task index out of bounds";
        tasks.get(index - 1).unmarkAsDone();
    }

    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchTasks.add(task);
            }
        }
        return matchTasks;
    }
}
