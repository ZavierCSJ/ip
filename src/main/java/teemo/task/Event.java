package teemo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import teemo.NaturalDateParser;
import teemo.TeemoException;

public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FMT =
            DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String description, String fromStr, String toStr) throws TeemoException {
        this(description,
                NaturalDateParser.parseNaturalDateTime(fromStr.trim()),
                NaturalDateParser.parseNaturalDateTime(toStr.trim()));
    }

    public Event(String description, LocalDateTime from, LocalDateTime to) throws TeemoException {
        super(description);
        if (from == null || to == null) {
            throw new TeemoException("Event times cannot be null.");
        }
        if (!to.isAfter(from)) {
            throw new TeemoException("Event end must be after start.");
        }
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

}
