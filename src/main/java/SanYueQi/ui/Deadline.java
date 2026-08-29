package SanYueQi.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Deadline extends Task {
    private String dueDate;

    public Deadline(String desc) {
        super(false, desc);
    }

    public Deadline(boolean done, String desc, String dueDate) {
        super(done, desc);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", super.isDone() ? "X" : " ", super.getDesc(), this.dueDate);
    }

    @Override
    public String toCSV() {
        return String.format("D,%s,%s,%s", super.isDone() ? "1" : "0", super.serialise(super.getDesc()), super.serialise(this.dueDate));
    }

    public static Deadline makeDeadline(String[] parts) {
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
                    return deadline;
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
        return null;
    }
}