package teemo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import teemo.task.Task;
import teemo.task.Todo;
import teemo.task.Deadline;
import teemo.task.Event;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path must be valid for Storage initialization";
        this.filePath = filePath;
    }

    public ArrayList<Task> loadTasks() {
        assert filePath != null : "File path should be set before loading tasks";

        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Create file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
                return tasks;
            }

            // Read existing file
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Task task = parseTaskFromLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
            fileScanner.close();

        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }
        return tasks;
    }

    private Task parseTaskFromLine(String line) {
        assert line != null && !line.trim().isEmpty() : "Line to parse should not be null or empty";

        try {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                return null; // Invalid format
            }

            String type = parts[0].trim();
            boolean isDone = parts[1].trim().equals("1");
            String description = parts[2].trim();

            Task task = null;
            switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length >= 4) {
                    String dateStr = parts[3].trim();
                    LocalDate date = LocalDate.parse(dateStr);
                    task = new Deadline(description, date);
                }
                break;
            case "E":
                if (parts.length >= 5) {
                    String fromStr = parts[3].trim();
                    String toStr = parts[4].trim();
                    LocalDateTime from = LocalDateTime.parse(fromStr);
                    LocalDateTime to = LocalDateTime.parse(toStr);
                    task = new Event(description, from, to);
                }
                break;
            default:
                assert false : "Unknown task type: " + type;
            }
            if (task != null && isDone) {
                task.markAsDone();
            }

            // Post-condition: if we created a task, it should have the correct description
            assert task == null || task.getDescription().equals(description) :
                    "Created task should have matching description";

            return task;
        } catch (Exception e) {
            System.out.println("Error parsing line: " + line);
            return null;
        }
    }

    public void saveTasks(ArrayList<Task> tasks) {
        assert tasks != null : "Cannot save null task list";
        assert filePath != null && !filePath.isEmpty() : "File path must be set before saving";

        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }


            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toSaveFormat() + System.lineSeparator());
            }
            writer.close();

            // Post-condition: file should exist after successful save
            assert file.exists() : "File should exist after saving";
        } catch (IOException e) {
            System.out.println("ERROR: Failed to save file!");
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace(); // This will show you exactly what went wrong
        }
    }

}
