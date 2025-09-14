package teemo;

import java.util.ArrayList;
import teemo.task.Task;
import teemo.Ui;

public class TaskList {

    @FunctionalInterface
    public static interface ActionHandler {
        Task handle(TaskList taskList, int index);
    }

    @FunctionalInterface
    public static interface ActionDisplay {
        void show(Ui ui, Task task);
    }

    @FunctionalInterface
    public static interface StringDisplay {
        String getString(Ui ui, Task task);
    }
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void delete(int index) {
        tasks.remove(index - 1);
    }

    public Task get(int index) {
        return tasks.get(index - 1);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public boolean isValidIndex(int index) {
        return index > 0 && index <= tasks.size();
    }

    public void markTask(int index) {
        if (isValidIndex(index)) {
            tasks.get(index - 1).markAsDone();
        }
    }

    public void unmarkTask(int index) {
        if (isValidIndex(index)) {
            tasks.get(index - 1).unmarkAsDone();
        }
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
