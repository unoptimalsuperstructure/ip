import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SanYueQi {
    abstract private static class Task {
        private String desc;
        private boolean done;
        private static ArrayList<Task> taskList = new ArrayList<>();

        private Task(String desc) {
            this.desc = desc;
            this.done = false;
        }

        public static void printNewTask(Task task) {
            taskList.add(task);
            System.out.println("Okay! I've added a new task:\n");
            System.out.printf("\t%s\n", task);
            System.out.printf("You currently have %d tasks in the list.\n", taskList.size());
        }

        public static int getNumOfTasks() {
            return taskList.size();
        }

        public static void mark(String[] parts, boolean done) {
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
        }

        public static void printTasks() {
            int i = 1;
            System.out.println("Here are the tasks in your list:\n");
            for (Task task : taskList) {
                System.out.printf("%d. %s\n", i, task);
                i += 1;
            }
        }
    }

    private static class ToDo extends Task {
        private ToDo(String desc) {
            super(desc);
        }

        @Override
        public String toString() {
            return String.format("[T][%s]%s", super.done ? "X" : " ", super.desc);
        }

        public static void makeToDo(String[] parts) {
            if (parts.length == 1) {
                System.out.println("Sorry! Description cannot be empty!");
            } else {
                StringBuilder desc = new StringBuilder();
                for (int i = 1; i < parts.length; i++) {
                    desc.append(" ");
                    desc.append(parts[i]);
                }
                ToDo todo = new ToDo(desc.toString());
                printNewTask(todo);
            }
        }
    }

    private static class Deadline extends Task {
        private String dueDate;

        private Deadline(String desc) {
            super(desc);
        }

        @Override
        public String toString() {
            return String.format("[D][%s]%s(by:%s)", super.done ? "X" : " ", super.desc, this.dueDate);
        }

        public static void makeDeadline(String[] parts) {
            StringBuilder desc = new StringBuilder();
            int i = 1;
            while (i < parts.length) {
                desc.append(" ");
                if (parts[i].equals("/by")) {
                    if (i == 1) {
                        System.out.println("Sorry! Description cannot be empty!");
                    } else if (i == parts.length - 1) {
                        System.out.println("Sorry! Deadline cannot be empty!");
                    } else {
                        Deadline deadline = new Deadline(desc.toString());
                        StringBuilder dueDate = new StringBuilder();
                        for (int j = i + 1; j < parts.length; j++) {
                            dueDate.append(" ");
                            dueDate.append(parts[j]);
                        }
                        deadline.dueDate = dueDate.toString();
                        printNewTask(deadline);
                    }
                    break;
                } else {
                    desc.append(parts[i]);
                }
                i += 1;
            }
            if (i == parts.length) {
                System.out.println("Sorry! You didn't indicate the deadline!");
            }
        }
    }

    private static class Event extends Task {
        private String from;
        private String to;

        private Event(String desc) {
            super(desc);
        }

        @Override
        public String toString() {
            return String.format("[E][%s]%s (from:%s to:%s)", super.done ? "X" : " ", super.desc, this.from, this.to);
        }

        public static void makeEvent(String[] parts) {
            StringBuilder desc = new StringBuilder();
            int i = 1;
            int foundFrom = -1;
            while (i < parts.length) {
                if (parts[i].equals("/to")) {
                    if (foundFrom == -1) {
                        System.out.println("Sorry! End time must be indicated after start time!");
                    } else if (i == foundFrom + 1) {
                        System.out.println("Sorry! Starting time cannot be empty!");
                    } else if (i == parts.length - 1) {
                        System.out.println("Sorry! Ending time cannot be empty!");
                    } else {
                        Event event = new Event(desc.toString());
                        StringBuilder from = new StringBuilder();
                        for (int j = foundFrom + 1; j < i; j++) {
                            from.append(" ");
                            from.append(parts[j]);
                        }
                        StringBuilder to = new StringBuilder();
                        for (int k = i + 1; k < parts.length; k++) {
                            to.append(" ");
                            to.append(parts[k]);
                        }
                        event.from = from.toString();
                        event.to = to.toString();
                        printNewTask(event);
                    }
                    break;
                }
                else if (parts[i].equals("/from") && foundFrom == -1) {
                    if (i == 1) {
                        System.out.println("Sorry! Description cannot be empty!");
                        break;
                    } else {
                        foundFrom = i;
                    }
                } else if (foundFrom == -1) {
                    desc.append(" ");
                    desc.append(parts[i]);
                }
                i += 1;
            }
            if (i == parts.length) {
                System.out.println("Sorry! You didn't indicate either the start or end time!");
            }
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
            String[] parts = prompt.split("\\s+");
            System.out.println("____________________________________________________________\n");
            switch (parts[0]) {
                case "bye" -> running = false;
                case "list" -> Task.printTasks();
                case "mark" -> Task.mark(parts, true);
                case "unmark" -> Task.mark(parts, false);
                case "todo" -> ToDo.makeToDo(parts);
                case "deadline" -> Deadline.makeDeadline(parts);
                case "event" -> Event.makeEvent(parts);
                default -> System.out.println("Sorry, I don't understand your request!");
            }
            System.out.println("____________________________________________________________\n");
        }
        System.out.println("____________________________________________________________\n");
        System.out.println("Thank you for today! See you again soon!");
        System.out.println("____________________________________________________________\n");
    }
}
