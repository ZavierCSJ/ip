package teemo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaturalDateParser {

    /**
     * Parses natural date strings into LocalDate objects.
     *
     * @param dateString the natural date string (e.g., "Mon", "today", "tomorrow")
     * @return LocalDate object representing the parsed date
     * @throws TeemoException if the date format is not recognized
     */
    public static LocalDate parseNaturalDate(String dateString) throws TeemoException {
        String input = dateString.trim().toLowerCase();
        LocalDate today = LocalDate.now();

        // Handle exact date formats first (yyyy-mm-dd)
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            // Continue to natural date parsing
        }

        // Handle common natural dates
        switch (input) {
        case "today":
            return today;
        case "tomorrow":
            return today.plusDays(1);
        case "yesterday":
            return today.minusDays(1);
        }

        // Handle day names (Mon, Tue, Wed, etc.)
        DayOfWeek targetDay = parseDayName(input);
        if (targetDay != null) {
            return getNextOccurrenceOfDay(today, targetDay);
        }

        // Handle "next [day]" format
        if (input.startsWith("next ")) {
            String dayPart = input.substring(5);
            DayOfWeek nextDay = parseDayName(dayPart);
            if (nextDay != null) {
                return getNextOccurrenceOfDay(today.plusDays(1), nextDay);
            }
        }

        // Handle "in X days" format
        Pattern inDaysPattern = Pattern.compile("in (\\d+) days?");
        Matcher matcher = inDaysPattern.matcher(input);
        if (matcher.matches()) {
            int days = Integer.parseInt(matcher.group(1));
            return today.plusDays(days);
        }

        // If nothing matches, throw exception
        throw new TeemoException("Unable to parse date: " + dateString +
                "\nSupported formats: yyyy-mm-dd, today, tomorrow, Mon, next friday, in 3 days");
    }

    /**
     * Parses natural date/time strings for events.
     */
    public static LocalDateTime parseNaturalDateTime(String dateTimeString) throws TeemoException {
        String[] parts = dateTimeString.trim().split("\\s+");

        if (parts.length == 1) {
            // Just a date, default time to 00:00
            LocalDate date = parseNaturalDate(parts[0]);
            return date.atTime(0, 0);
        } else if (parts.length == 2) {
            // Date and time
            LocalDate date = parseNaturalDate(parts[0]);
            // Simple time parsing (you can enhance this)
            try {
                String timeStr = parts[1];
                if (timeStr.contains("pm") || timeStr.contains("am")) {
                    // 12-hour format like "1pm"
                    // Clean up am/pm: remove them, trim whitespace
                    String cleanTime = timeStr.toLowerCase().replace("am", "").replace("pm", "").trim();

                    String[] timeParts = cleanTime.split(":");
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = timeParts.length > 1 ? Integer.parseInt(timeParts[1]) : 0;

                    // Convert 12-hour to 24-hour
                    if (timeStr.toLowerCase().contains("pm") && hour != 12) {
                        hour += 12;
                    } else if (timeStr.toLowerCase().contains("am") && hour == 12) {
                        hour = 0; // 12am = 00:00
                    }

                    return date.atTime(hour, minute);
                } else {
                    // 24-hour format like "14:30"
                    String[] timeParts = timeStr.split(":");
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = timeParts.length > 1 ? Integer.parseInt(timeParts[1]) : 0;
                    return date.atTime(hour, minute);
                }
            } catch (Exception e) {
                throw new TeemoException("Unable to parse time: " + dateTimeString);
            }
        }

        throw new TeemoException("Unable to parse date/time: " + dateTimeString);
    }

    private static DayOfWeek parseDayName(String dayName) {
        switch (dayName.toLowerCase()) {
        case "mon": case "monday":
            return DayOfWeek.MONDAY;
        case "tue": case "tuesday":
            return DayOfWeek.TUESDAY;
        case "wed": case "wednesday":
            return DayOfWeek.WEDNESDAY;
        case "thu": case "thursday":
            return DayOfWeek.THURSDAY;
        case "fri": case "friday":
            return DayOfWeek.FRIDAY;
        case "sat": case "saturday":
            return DayOfWeek.SATURDAY;
        case "sun": case "sunday":
            return DayOfWeek.SUNDAY;
        default:
            return null;
        }
    }

    private static LocalDate getNextOccurrenceOfDay(LocalDate fromDate, DayOfWeek targetDay) {
        DayOfWeek currentDay = fromDate.getDayOfWeek();
        int daysUntilTarget = (targetDay.getValue() - currentDay.getValue() + 7) % 7;
        if (daysUntilTarget == 0) {
            daysUntilTarget = 7; // If it's the same day, get next week's occurrence
        }
        return fromDate.plusDays(daysUntilTarget);
    }
}
