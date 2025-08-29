package teemo.task;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FMT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private LocalDate by;

    public Deadline(String description, String byString) {
        super(description);
        try {
            this.by = LocalDate.parse(byString.trim());
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format! Use yyyy-MM-dd (e.g. 2019-12-04)");
        }
    }

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), by.format(DISPLAY_FMT));
    }

    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1": "0") + " | " + description + " | " + by;
    }

    public LocalDate getBy() {
        return by;
    }
}
