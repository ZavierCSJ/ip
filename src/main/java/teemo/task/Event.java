package teemo.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private static final DateTimeFormatter INPUT_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DISPLAY_FMT =
            DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, String fromStr, String toStr) {
        super(description);
        try {
            this.from = LocalDateTime.parse(fromStr.trim(), INPUT_FMT);
            this.to = LocalDateTime.parse(toStr.trim(), INPUT_FMT);

            if (this.from.isAfter(this.to)) {
                throw new IllegalArgumentException("Event start time cannot be after end time!");
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format! Use yyyy-MM-dd HHmm format, e.g 2019-12-12 2359");
        }
    }

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), from.format(DISPLAY_FMT), to.format(DISPLAY_FMT));
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
