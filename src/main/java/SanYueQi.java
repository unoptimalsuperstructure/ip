import java.util.Objects;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SanYueQi {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm"));

        //String banner = " ____        _        \n"
        //        + "|  _ \\ _   _| | _____ \n"
        //        + "| | | | | | | |/ / _ \\\n"
        //        + "| |_| | |_| |   <  __/\n"
        //        + "|____/ \\__,_|_|\\_\\___|\n";
        //System.out.println(banner);
        System.out.println("____________________________________________________________\n");
        System.out.println("Welcome back! It's March.");
        System.out.printf("Today's date is %s and the current time is %s.", date, time);
        System.out.println("\nAre you here to play with me?");
        System.out.println("____________________________________________________________\n");
        String prompt = "";
        while (true) {
            Scanner scanner = new Scanner(System.in);
            prompt = scanner.nextLine();

            if (Objects.equals(prompt, "bye")) break;

            System.out.println("____________________________________________________________\n");
            System.out.println(prompt);
            System.out.println("____________________________________________________________\n");
        }
        System.out.println("____________________________________________________________\n");
        System.out.println("Thank you for today! See you again soon!");
        System.out.println("____________________________________________________________\n");
    }
}
