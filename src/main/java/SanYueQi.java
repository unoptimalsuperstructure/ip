import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SanYueQi {
    private static class Task {
        private String desc;
        private static ArrayList<Task> taskList = new ArrayList<>();

        private Task(String desc) {
            this.desc = desc;
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

        public static void printTasks() {
            int i = 1;
            System.out.println("____________________________________________________________\n");
            for (Task task : taskList) {
                System.out.printf("%d. %s\n", i, task.desc);
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
        while (true) {
            Scanner scanner = new Scanner(System.in);
            prompt = scanner.nextLine();
            if (Objects.equals(prompt, "bye")) break;
            else if (Objects.equals(prompt, "list")) Task.printTasks();
            else Task.addTask(prompt);
        }
        System.out.println("____________________________________________________________\n");
        System.out.println("Thank you for today! See you again soon!");
        System.out.println("____________________________________________________________\n");
    }
}
