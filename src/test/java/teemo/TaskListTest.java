package teemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import teemo.task.Todo;
import teemo.task.Task;

public class TaskListTest {
    private TaskList taskList;
    private Todo sampleTodo;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        sampleTodo = new Todo("test task");
    }

    @Test
    @DisplayName("Test adding and retrieving tasks")
    public void testAddAndGet() {
        // Initially empty
        assertEquals(0, taskList.size());

        // Add task
        taskList.add(sampleTodo);
        assertEquals(1, taskList.size());
        assertEquals(sampleTodo, taskList.get(1));

        // Add another task
        Todo anotherTodo = new Todo("another task");
        taskList.add(anotherTodo);
        assertEquals(2, taskList.size());
        assertEquals(anotherTodo, taskList.get(2));
    }

    @Test
    @DisplayName("Test index validation")
    public void testIsValidIndex() {
        // Empty list - no valid indices
        assertFalse(taskList.isValidIndex(0));
        assertFalse(taskList.isValidIndex(1));
        assertFalse(taskList.isValidIndex(-1));

        // Add one task
        taskList.add(sampleTodo);
        assertFalse(taskList.isValidIndex(0)); // 0 is invalid (1-based indexing)
        assertTrue(taskList.isValidIndex(1));  // 1 is valid
        assertFalse(taskList.isValidIndex(2)); // 2 is out of range
        assertFalse(taskList.isValidIndex(-1)); // negative is invalid
    }

    @Test
    @DisplayName("Test marking and unmarking tasks")
    public void testMarkAndUnmark() {
        taskList.add(sampleTodo);

        // Initially not done
        assertFalse(sampleTodo.isDone());

        // Mark as done
        taskList.markTask(1);
        assertTrue(sampleTodo.isDone());

        // Unmark
        taskList.unmarkTask(1);
        assertFalse(sampleTodo.isDone());
    }

    @Test
    @DisplayName("Test deleting tasks")
    public void testDelete() {
        taskList.add(sampleTodo);
        Todo anotherTodo = new Todo("another task");
        taskList.add(anotherTodo);

        assertEquals(2, taskList.size());

        taskList.delete(1); // This deletes the first task
        assertEquals(1, taskList.size());
        assertEquals(anotherTodo, taskList.get(1));
    }
}
