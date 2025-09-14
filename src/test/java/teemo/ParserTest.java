package teemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import teemo.task.Todo;
import teemo.task.Deadline;
import teemo.task.Event;

public class ParserTest {

    @Test
    @DisplayName("Test parsing todo command")
    public void testParseTodo() {
        // Test valid todo
        Todo todo = assertDoesNotThrow(() -> Parser.parseTodo("todo read book"));
        assertEquals("read book", todo.getDescription());

        // Test empty todo (should throw exception)
        TeemoException exception = assertThrows(TeemoException.class,
                () -> Parser.parseTodo("todo"));
        assertEquals("OOPS!!! The description of a todo cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Test parsing deadline command")
    public void testParseDeadline() {
        // Test valid deadline
        Deadline deadline = assertDoesNotThrow(() ->
                Parser.parseDeadline("deadline homework /by 2024-12-01"));
        assertEquals("homework", deadline.getDescription());

        // Test deadline without /by (should throw exception)
        TeemoException exception = assertThrows(TeemoException.class,
                () -> Parser.parseDeadline("deadline homework"));
        assertTrue(exception.getMessage().contains("Need a deadline!"));

        // Test empty deadline
        assertThrows(TeemoException.class,
                () -> Parser.parseDeadline("deadline"));
    }

    @Test
    @DisplayName("Test parsing task index")
    public void testParseTaskIndex() {
        // Test valid index
        assertEquals(5, assertDoesNotThrow(() -> Parser.parseTaskIndex("mark 5", "mark")));
        assertEquals(10, assertDoesNotThrow(() -> Parser.parseTaskIndex("delete 10", "delete")));

        // Test invalid formats
        assertThrows(TeemoException.class, () -> Parser.parseTaskIndex("mark", "mark"));
        assertThrows(TeemoException.class, () -> Parser.parseTaskIndex("mark abc", "mark"));
    }
}
