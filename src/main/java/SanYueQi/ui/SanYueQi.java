package SanYueQi.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SanYueQi {
    static final TaskList masterTaskList = new TaskList();

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
                masterTaskList.addTask(todo);
                break;
            case 'D':
                Deadline deadline = new Deadline(done, args[0].toString(), args[1].toString());
                masterTaskList.addTask(deadline);
                break;
            case 'E':
                Event event = new Event(done, args[0].toString(), args[2].toString(), args[3].toString());
                masterTaskList.addTask(event);
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
                case "todo" -> masterTaskList.printNewTask(ToDo.makeToDo(parts));
                case "deadline" -> masterTaskList.printNewTask(Deadline.makeDeadline(parts));
                case "event" -> masterTaskList.printNewTask(Event.makeEvent(parts));
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
