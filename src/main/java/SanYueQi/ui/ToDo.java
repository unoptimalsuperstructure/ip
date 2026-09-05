package sanyueqi.ui;

class ToDo extends Task {
    public ToDo(String desc) {
        super(false, desc);
    }

    public ToDo(boolean done, String desc) {
        super(done, desc);
    }

    @Override
    public String toString() {
        return String.format("[T][%s] %s", super.isDone() ? "X" : " ", super.getDesc());
    }

    /**
     * Serializes a to-do for writing to a CSV.
     *
     * @return The serialized string.
     */
    @Override
    public String toCSV() {
        return String.format("T,%s,%s", super.isDone() ? "1" : "0", super.serialise(super.getDesc()));
    }

    /**
     * Makes a 'to-do' type Task.
     *
     * @param parts The parts of the to-do.
     * @return A to-do instance.
     */
    public static ToDo makeToDo(String[] parts) {
        if (parts.length == 1) {
            System.out.println("Sorry! Description cannot be empty!");
        } else {
            StringBuilder desc = new StringBuilder();
            for (int i = 1; i < parts.length; i++) {
                desc.append(parts[i]);
                if (i < parts.length - 1) desc.append(" ");
            }
            return new ToDo(desc.toString());
        }
        return null;
    }
}