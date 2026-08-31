package SanYueQi.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {
    @Test
    void testPrintMessage1() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            Task task = SanYueQi.masterTaskList.printNewTask(Event.makeEvent("event 3 /from 4 /to 5".split("\\s+")));

            assertNotNull(task);
            assertTrue(task.toString().contains("[E][ ] 3 (from: 4 to: 5)"));

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testPrintMessage2() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            Task task = SanYueQi.masterTaskList.printNewTask(Event.makeEvent("event 3 /to 4 /from 5".split("\\s+")));

            assertNull(task);

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testPrintMessage3() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            Task task = SanYueQi.masterTaskList.printNewTask(Event.makeEvent("event  todo  deadline  !#$ /   /from/from /by /from \\1\" /to   /to 2\\\"".split("\\s+")));

            assertNotNull(task);
            assertTrue(task.toString().contains("[E][ ] todo deadline !#$ / /from/from /by (from: \\1\" to: /to 2\\\")"));

        } finally {
            System.setOut(originalOut);
        }
    }
}
