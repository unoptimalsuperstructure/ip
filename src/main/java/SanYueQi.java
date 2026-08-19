import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SanYueQi {
    private static class Task {
        private String desc;
        private boolean done;
        private static ArrayList<Task> taskList = new ArrayList<>();

        private Task(String desc) {
            this.desc = desc;
            this.done = false;
        }

        @Override
        public String toString() {
            return String.format("[%s] %s", this.done ? "X" : " ", this.desc);
        }

        public static void addTask(String desc) {
            Task task = new Task(desc);
            taskList.add(task);
            System.out.println("____________________________________________________________\n");
            System.out.printf("Task added: %s\n", desc);
            System.out.println("____________________________________________________________\n");
        }

        public static int getNumOfTasks() {
            return taskList.size();
        }

        public static void mark(String[] parts, boolean done) {
            System.out.println("____________________________________________________________\n");
            if (parts.length < 2) {
                System.out.println("Sorry, you need to specify the task number!");
            } else if (parts.length > 2) {
                System.out.println("Sorry, you've specified too many inputs!");
            } else {
                try {
                    int num = Integer.parseInt(parts[1]);
                    if (num < 1 || num > taskList.size()) {
                        System.out.printf("Sorry, I can't %smark task %d. You have %d items in your list!\n", done ? "" : "un", num, taskList.size());
                    } else {
                        Task task = taskList.get(num - 1);
                        if (task.done != done) {
                            task.done = done;
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

            System.out.println("____________________________________________________________\n");
        }

        public static void printTasks() {
            int i = 1;
            System.out.println("____________________________________________________________\n");
            System.out.println("Here are the tasks in your list:\n");
            for (Task task : taskList) {
                System.out.printf("%d. %s\n", i, task);
                i += 1;
            }
            System.out.println("____________________________________________________________\n");
        }
    }
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm"));

        System.out.println("____________________________________________________________\n");
        System.out.println("Welcome back! It's March.");
        System.out.printf("Today's date is %s and the current time is %s.", date, time);
        System.out.println("\nAre you here to play with me?");
        System.out.println("____________________________________________________________\n");
        String prompt;
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            prompt = scanner.nextLine();
            String[] parts = prompt.split(" ");
            switch (parts[0]) {
                case "bye" -> running = false;
                case "list" -> Task.printTasks();
                case "mark" -> Task.mark(parts, true);
                case "unmark" -> Task.mark(parts, false);
                default -> Task.addTask(prompt);
            }
        }
        System.out.println("____________________________________________________________\n");
        System.out.println("Thank you for today! See you again soon!");
        System.out.println("____________________________________________________________\n");
    }
}
