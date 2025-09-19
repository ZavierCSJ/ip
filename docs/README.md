# Teemo – Swift Scout Task Manager
> *"Never underestimate the power of the Scout's code."* - Teemo

![Teemo GUI](docs/Ui.png)

Teemo helps you keep track of tasks so you never get ganked by deadlines. It's:
- **Text-based** and **GUI-powered**
- **Easy to learn**
- ~~SLOW~~ **SUPER SWIFT** to use

All you need to do:
1. [Download Teemo here](https://github.com/ZavierCSJ/ip/releases)
2. Double-click the JAR or run from terminal
3. Add your tasks
4. Let Teemo ambush your to-do list for you 😎🍄

And it's **FREE**!

## Features

### 🎯 Core Task Management
- [x] **Managing Todos** - Simple task creation and tracking
- [x] **Deadline Management** - Never miss important due dates
- [x] **Event Scheduling** - Track meetings and appointments
- [x] **Smart Search** - Find tasks quickly with keyword matching
- [x] **Task Status Control** - Mark tasks as complete/incomplete
- [x] **Persistent Storage** - Your tasks are automatically saved

### 🚀 Advanced Features
- [x] **Natural Date Parsing** - Use phrases like "tomorrow", "next Friday", "in 3 days"
- [x] **Dual Interface** - Both command-line and beautiful GUI support

## Commands
### Todo task
Users can add task using the `todo` command.

Command: `todo [description]` - Adds a simple to-do task.

**Examples**:
*   `todo read book`
*   `todo complete CS2103 iP`

```
Affirmative! Task registered in the field manual:
[T][ ] read book
Total active missions: 13. All systems operational!
```
### Deadline task
User can add task with deadline using the `deadline` command.

**Command**: `deadline [description] /by [date]` - Adds a task with a due date.

**Examples**:
*   `deadline submit report /by 2025-10-05`
*   `deadline return books /by tomorrow`
*   `deadline prepare presentation /by next Friday`

```
Copy that, commander! Target acquired and filed:
[D][ ] return books (by: Sep 20 2025)
Total active missions: 14. All systems operational!
```
### Event task
User can schedule an event with a specific start and end time using the `event` command.

**Command**: `event [description] /from [start_time] /to [end_time]`

**Examples**:
*   `event team meeting /from today 2pm /to today 4pm`
*   `event project discussion /from 2025-09-22 10:00 /to 2025-09-22 11:30`
*   `event birthday party /from Sat 7pm /to Sat 11pm`

```
Copy that, commander! Target acquired and filed:
[E][ ] team meeting (from: Sep 19 2025 2:00pm to: Sep 19 2025 4:00pm)
Total active missions: 15. All systems operational!
```
### List all tasks
Displays all current tasks using the `list` command

**Command**: `list`

**Example**: `list`
```
Current tactical overview - Active missions:
1. [T][X] read book
2. [D][X] return book (by: Sep 17 2025)
3. [T][ ] buy food
4. [T][ ] revise CS2100
5. [D][ ] submit report (by: Sep 22 2025)
```
### Find a task
Performs a search on your task list to find tasks matching a specific keyword.

**Command**: `find [keyword]`

**Example**: `find report`
```
Intel gathered! Found 1 mission(s) matching 'report':
1. [D][ ] submit report (by: Sep 22 2025)
```

### Mark a task

**Command**: `mark [task_index]`

**Example**: `mark 1`
```
Objective secured! Excellent execution of:
[T][X] read book
Ready for next assignment, commander!
```
### Unmark a task

**Command**: `unmark [task_index]`

**Example**: `unmark 1`
```
Mission status updated! Returning to active duty:
[T][ ] read book
Task reactivated and back in the field!
```
### Delete a task
Permanently removes a task from your list.

**Command**: `delete [task_index]`

**Example**: `delete 3`
```
Mission aborted! Clearing from tactical database:
[D][ ] submit report (by: Sep 22 2025)
Area secured. What's the next objective?
```

### Bye (Save and Exit)
Ends your session with Teemo and safely shuts down the application.

**Command**: `bye`

**Example**: `bye`

```
Mission complete! Teemo signing off.
Stay tactical, commander! Over and out!
```

