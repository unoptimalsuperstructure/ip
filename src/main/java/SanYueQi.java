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
        System.out.printf("Welcome back! It's March.\n");
        System.out.printf("Today's date is %s and the current time is %s.", date, time);
        System.out.println("\nAre you here to play with me?");
        System.out.println("____________________________________________________________\n");
        System.out.println("See you again soon!");
    }
}
