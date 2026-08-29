import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SanYueQi {
    private static final TaskList masterTaskList = new TaskList();

    private static class TaskList {
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

        public void printNewTask(Task task) {
            this.taskList.add(task);
            writeTasks();
            System.out.println("Okay! I've added a new task:\n");
            System.out.printf("\t%s\n", task);
            System.out.printf("You currently have %d tasks in the list.\n", taskList.size());
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
                        if (task.done != done) {
                            task.done = done;
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

    abstract private static class Task {
        private String desc;
        private boolean done;

        private Task(boolean done, String desc) {
            this.desc = desc;
            this.done = done;
        }

        public String serialise(String s) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }

        abstract public String toCSV();

    }

    private static class ToDo extends Task {
        private ToDo(String desc) {
            super(false, desc);
        }

        private ToDo(boolean done, String desc) {
            super(done, desc);
            masterTaskList.addTask(this);
        }

        @Override
        public String toString() {
            return String.format("[T][%s] %s", super.done ? "X" : " ", super.desc);
        }

        @Override
        public String toCSV() {
            return String.format("T,%s,%s", super.done ? "1" : "0", serialise(super.desc));
        }

        public static void makeToDo(String[] parts) {
            if (parts.length == 1) {
                System.out.println("Sorry! Description cannot be empty!");
            } else {
                StringBuilder desc = new StringBuilder();
                for (int i = 1; i < parts.length; i++) {
                    desc.append(parts[i]);
                    if (i < parts.length - 1) desc.append(" ");
                }
                ToDo todo = new ToDo(desc.toString());
                masterTaskList.printNewTask(todo);
            }
        }
    }

    private static class Deadline extends Task {
        private String dueDate;

        private Deadline(String desc) {
            super(false, desc);
        }

        private Deadline(boolean done, String desc, String dueDate) {
            super(done, desc);
            this.dueDate = dueDate;
            masterTaskList.addTask(this);
        }

        @Override
        public String toString() {
            return String.format("[D][%s] %s (by: %s)", super.done ? "X" : " ", super.desc, this.dueDate);
        }

        @Override
        public String toCSV() {
            return String.format("D,%s,%s,%s", super.done ? "1" : "0", serialise(super.desc), serialise(this.dueDate));
        }

        public static void makeDeadline(String[] parts) {
            StringBuilder desc = new StringBuilder();
            int i = 1;
            while (i < parts.length) {
                if (parts[i].equals("/by")) {
                    desc.deleteCharAt(desc.length() - 1);
                    if (i == 1) {
                        System.out.println("Sorry! Description cannot be empty!");
                    } else if (i == parts.length - 1) {
                        System.out.println("Sorry! Deadline cannot be empty!");
                    } else {
                        Deadline deadline = new Deadline(desc.toString());
                        StringBuilder dueDate = new StringBuilder();
                        for (int j = i + 1; j < parts.length; j++) {
                            dueDate.append(parts[j]);
                            if (j < parts.length - 1) dueDate.append(" ");
                        }
                        String formattedDueDate;
                        try {
                            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
                            formattedDueDate = LocalDate.parse(dueDate.toString(), inputFormatter)
                                                        .format(outputFormatter);
                        } catch (DateTimeParseException e) {
                            formattedDueDate = dueDate.toString();
                        }
                        deadline.dueDate = formattedDueDate;
                        masterTaskList.printNewTask(deadline);
                    }
                    break;
                } else {
                    desc.append(parts[i]);
                }
                desc.append(" ");
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
            super(false, desc);
        }

        private Event(boolean done, String desc, String from, String to) {
            super(done, desc);
            this.from = from;
            this.to = to;
            masterTaskList.addTask(this);
        }

        @Override
        public String toString() {
            return String.format("[E][%s] %s (from: %s to: %s)", super.done ? "X" : " ", super.desc, this.from, this.to);
        }

        @Override
        public String toCSV() {
            return String.format("E,%s,%s,%s,%s", super.done ? "1" : "0", serialise(super.desc), serialise(this.from), serialise(this.to));
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
                            from.append(parts[j]);
                            if (j < i - 1) from.append(" ");
                        }
                        StringBuilder to = new StringBuilder();
                        for (int k = i + 1; k < parts.length; k++) {
                            to.append(parts[k]);
                            if (k < parts.length - 1) to.append(" ");
                        }
                        String formattedFrom;
                        String formattedTo;
                        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
                        try {
                            formattedFrom = LocalDate.parse(from.toString(), inputFormatter)
                                                     .format(outputFormatter);
                        } catch (DateTimeParseException e) {
                            formattedFrom = from.toString();
                        }
                        try {
                            formattedTo = LocalDate.parse(to.toString(), inputFormatter)
                                                   .format(outputFormatter);
                        } catch (DateTimeParseException e) {
                            formattedTo = to.toString();
                        }
                        event.from = formattedFrom;
                        event.to = formattedTo;
                        masterTaskList.printNewTask(event);
                    }
                    break;
                }
                else if (parts[i].equals("/from") && foundFrom == -1) {
                    desc.deleteCharAt(desc.length() - 1);
                    if (i == 1) {
                        System.out.println("Sorry! Description cannot be empty!");
                        break;
                    } else {
                        foundFrom = i;
                    }
                } else if (foundFrom == -1) {
                    desc.append(parts[i]);
                    desc.append(" ");
                }
                i += 1;
            }
            if (i == parts.length) {
                System.out.println("Sorry! You didn't indicate either the start or end time!");
            }
        }
    }

    private static boolean parseLine(String line) {
        if (line.length() <= 4) return false;
        boolean quote = false;
        StringBuilder[] args = new StringBuilder[4]; // description, dueDate, from, to
        for (int j = 0; j < 4; j++) {
            args[j] = new StringBuilder();
        }
        char type;
        boolean done;
        int index = 0;
        String temp1 = new String(new char[]{line.charAt(0), line.charAt(1)});
        String temp2 = new String(new char[]{line.charAt(2), line.charAt(3)});
        switch (temp1) {
            case "T,":
                type = 'T';
                break;
            case "D,":
                type = 'D';
                break;
            case "E,":
                type = 'E';
                break;
            default:
                return false;
        }
        switch (temp2) {
            case "0,":
                done = false;
                break;
            case "1,":
                done = true;
                break;
            default:
                return false;
        }
        for (int i = 4; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (!quote) {
                    quote = true;
                } else {
                    if (i == line.length() - 1) {
                        break;
                    } else {
                        i++;
                        if (line.charAt(i) == ',') {
                            quote = false;
                            if (index == 0) {
                                index = type == 'D' ? 1 : 2;
                            } else {
                                index = 3;
                            }
                        } else if (line.charAt(i) == '"') {
                            args[index].append('"');
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                args[index].append(c);
            }
        }
        switch (type) {
            case 'T':
                ToDo todo = new ToDo(done, args[0].toString());
                System.out.println(todo);
                break;
            case 'D':
                new Deadline(done, args[0].toString(), args[1].toString());
                break;
            case 'E':
                new Event(done, args[0].toString(), args[2].toString(), args[3].toString());
                break;
            default:
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("logbook.csv"));
            System.out.println("Existing logbook found and loaded. Parsing...");
        } catch (IOException e1) {
            try {
                Files.createFile(Paths.get("logbook.csv"));
                lines = new ArrayList<>();
                System.out.println("Existing logbook not found. New logbook created.");
            } catch (IOException e2) {
                System.out.println("Critical error: Unable to load or create logbook");
                return;
            }
        }
        for (int i = 0; i < lines.size(); i++) {
            if (!parseLine(lines.get(i))) {
                System.out.printf("Critical error: Malformed logbook at line %d", i + 1);
                return;
            }
        }

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
                case "list" -> masterTaskList.printTasks();
                case "mark" -> masterTaskList.markTask(parts, true);
                case "unmark" -> masterTaskList.markTask(parts, false);
                case "todo" -> ToDo.makeToDo(parts);
                case "deadline" -> Deadline.makeDeadline(parts);
                case "event" -> Event.makeEvent(parts);
                case "delete" -> masterTaskList.deleteTask(parts);
                default -> System.out.println("Sorry, I don't understand your request!");
            }
            System.out.println("____________________________________________________________\n");
        }
        System.out.println("____________________________________________________________\n");
        System.out.println("Thank you for today! See you again soon!");
        System.out.println("____________________________________________________________\n");
    }
}
