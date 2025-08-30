package teemo.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {

    @Test
    @DisplayName("Test Todo creation and string representation")
    public void testTodoCreation() {
        Todo todo = new Todo("read book");

        assertEquals("read book", todo.getDescription());
        assertFalse(todo.isDone());
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    @DisplayName("Test marking todo as done")
    public void testMarkAsDone() {
        Todo todo = new Todo("finish homework");

        // Initially not done
        assertFalse(todo.isDone());
        assertEquals("[T][ ] finish homework", todo.toString());

        // Mark as done
        todo.markAsDone();
        assertTrue(todo.isDone());
        assertEquals("[T][X] finish homework", todo.toString());

        // Unmark
        todo.unmarkAsDone();
        assertFalse(todo.isDone());
        assertEquals("[T][ ] finish homework", todo.toString());
    }

    @Test
    @DisplayName("Test save format")
    public void testSaveFormat() {
        Todo todo = new Todo("test task");

        // Not done
        assertEquals("T | 0 | test task", todo.toSaveFormat());

        // Done
        todo.markAsDone();
        assertEquals("T | 1 | test task", todo.toSaveFormat());
    }
}
