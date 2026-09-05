package sanyueqi.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Event extends Task {
    private String from;
    private String to;

    public Event(String desc) {
        super(false, desc);
    }

    public Event(boolean done, String desc, String from, String to) {
        super(done, desc);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (from: %s to: %s)", super.isDone() ? "X" : " ", super.getDesc(), this.from, this.to);
    }

    /**
     * Serializes an event for writing to a CSV.
     *
     * @return The serialized string.
     */
    @Override
    public String toCSV() {
        return String.format("E,%s,%s,%s,%s", super.isDone() ? "1" : "0", super.serialise(super.getDesc()), super.serialise(this.from), super.serialise(this.to));
    }

    /**
     * Makes an 'event' type Task.
     *
     * @param parts The parts of the event.
     * @return An event instance.
     */
    public static Event makeEvent(String[] parts) {
        StringBuilder desc = new StringBuilder();
        int i = 1;
        int foundIndex = -1;
        while (i < parts.length) {
            if (parts[i].equals("/to")) {
                if (foundIndex == -1) {
                    System.out.println("Sorry! End time must be indicated after start time!");
                } else if (i == foundIndex + 1) {
                    System.out.println("Sorry! Starting time cannot be empty!");
                } else if (i == parts.length - 1) {
                    System.out.println("Sorry! Ending time cannot be empty!");
                } else {
                    Event event = new Event(desc.toString());
                    StringBuilder from = new StringBuilder();
                    for (int j = foundIndex + 1; j < i; j++) {
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
                    return event;
                }
                break;
            }
            else if (parts[i].equals("/from") && foundIndex == -1) {
                desc.deleteCharAt(desc.length() - 1);
                if (i == 1) {
                    System.out.println("Sorry! Description cannot be empty!");
                    break;
                } else {
                    foundIndex = i;
                }
            } else if (foundIndex == -1) {
                desc.append(parts[i]);
                desc.append(" ");
            }
            i += 1;
        }
        if (i == parts.length) {
            System.out.println("Sorry! You didn't indicate either the start or end time!");
        }
        return null;
    }
}