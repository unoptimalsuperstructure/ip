package sanyueqi.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {
    @Test
    void normalTestPrintMessage() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            String s = SanYueQi.masterTaskList.printNewTask(Event.makeEvent("event 3 /from 4 /to 5".split("\\s+")));

            assertNotNull(s);
            assertTrue(s.contains("[E][ ] 3 (from: 4 to: 5)"));

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void errorTestPrintMessage() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            String s = SanYueQi.masterTaskList.printNewTask(Event.makeEvent("event 3 /to 4 /from 5".split("\\s+")));

            assertNull(s);

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void stressTestPrintMessage() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            String s = SanYueQi.masterTaskList.printNewTask(Event.makeEvent("event  todo  deadline  !#$ /   /from/from /by /from \\1\" /to   /to 2\\\"".split("\\s+")));

            assertNotNull(s);
            assertTrue(s.contains("[E][ ] todo deadline !#$ / /from/from /by (from: \\1\" to: /to 2\\\")"));

        } finally {
            System.setOut(originalOut);
        }
    }
}
