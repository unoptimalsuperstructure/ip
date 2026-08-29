package SanYueQi.ui;

abstract class Task {
    private String desc;
    private boolean done;

    public Task(boolean done, String desc) {
        this.desc = desc;
        this.done = done;
    }

    public String getDesc() {
        return this.desc;
    }

    public boolean isDone() {
        return this.done;
    }

    public void markDone(boolean done) {
        this.done = done;
    }

    public String serialise(String s) {
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }

    abstract public String toCSV();
}