package teemo.task;

public class Deadline extends Task {
    private String date;

    public Deadline(String description, String date) {
        super(description);
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.date);
    }

    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1": "0") + " | " + description + " | " + date;
    }
}
