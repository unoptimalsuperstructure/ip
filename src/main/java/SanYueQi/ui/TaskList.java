package SanYueQi.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class TaskList {
    private final ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public void writeTasks() {
        ArrayList<String> serialisedTasks = new ArrayList<>();
        for (Task task : this.taskList) {
            serialisedTasks.add(task.toCSV());
        }
        try {
            Files.write(Paths.get("logbook.csv"), serialisedTasks);
        } catch (IOException e) {
            System.out.println("Critical error: Writing tasks failed.");
        }
    }

    public Task printNewTask(Task task) {
        if (task != null) {
            addTask(task);
            writeTasks();
            System.out.println("Okay! I've added a new task:\n");
            System.out.printf("\t%s\n", task);
            System.out.printf("You currently have %d tasks in the list.\n", taskList.size());
        }
        return task;
    }

    public int getNumOfTasks() {
        return this.taskList.size();
    }

    public void markTask(String[] parts, boolean done) {
        if (parts.length < 2) {
            System.out.println("Sorry, you need to specify the task number!");
        } else if (parts.length > 2) {
            System.out.println("Sorry, you've specified too many inputs!");
        } else {
            try {
                int num = Integer.parseInt(parts[1]);
                if (num < 1 || num > this.taskList.size()) {
                    System.out.printf("Sorry, I can't %smark task %d. You have %d items in your list!\n", done ? "" : "un", num, taskList.size());
                } else {
                    Task task = this.taskList.get(num - 1);
                    if (task.isDone() != done) {
                        task.markDone(done);
                        writeTasks();
                        System.out.println(done ? "Great job on completing this task!" : "Okay, I've marked this task as not done yet:");
                    } else {
                        System.out.println(done ? "You've already marked the following task as done!" : "This task is already currently marked as not done yet!");
                    }
                    System.out.printf("\t%s\n", task);
                }
            } catch (NumberFormatException e) {
                System.out.println("Sorry, you've entered an invalid task number!");
            }
        }
    }

    public void printTasks() {
        int i = 1;
        System.out.println("Here are the tasks in your list:\n");
        for (Task task : this.taskList) {
            System.out.printf("%d. %s\n", i, task);
            i += 1;
        }
    }

    public void deleteTask(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Sorry, you need to specify the task number!");
        } else if (parts.length > 2) {
            System.out.println("Sorry, you've specified too many inputs!");
        } else {
            try {
                int num = Integer.parseInt(parts[1]);
                if (num < 1 || num > this.taskList.size()) {
                    System.out.printf("Sorry, I can't delete task %d. You have %d items in your list!\n", num, taskList.size());
                } else {
                    Task task = this.taskList.get(num - 1);
                    this.taskList.remove(num - 1);
                    writeTasks();
                    System.out.println("Okay, I've deleted this task:");
                    System.out.printf("\t%s\n", task);
                    System.out.printf("You currently have %d tasks in the list.\n", this.taskList.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Sorry, you've entered an invalid task number!");
            }
        }
    }
}