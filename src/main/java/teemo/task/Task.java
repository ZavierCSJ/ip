package teemo.task;

import java.lang.annotation.Documented;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "[X]": "[ ]";
    }

    public void markAsDone() {
        isDone = true;
    }

    public void unmarkAsDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getStatusIcon(), this.description);
    }

    public abstract String toSaveFormat();

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }
}
