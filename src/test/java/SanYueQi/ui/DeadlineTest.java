package SanYueQi.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTest {
    @Test
    void testPrintMessage1() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            Task task = SanYueQi.masterTaskList.printNewTask(Deadline.makeDeadline("deadline 3 /by 4".split("\\s+")));

            assertNotNull(task);
            assertTrue(task.toString().contains("[D][ ] 3 (by: 4)"));

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

            Task task = SanYueQi.masterTaskList.printNewTask(Deadline.makeDeadline("deadline 3 by 4".split("\\s+")));

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

            Task task = SanYueQi.masterTaskList.printNewTask(Deadline.makeDeadline("deadline  event  todo  !#$ /   /from/from /by /from \\1\" /to   /to 2\\\"".split("\\s+")));

            assertNotNull(task);
            assertTrue(task.toString().contains("[D][ ] event todo !#$ / /from/from (by: /from \\1\" /to /to 2\\\")"));

        } finally {
            System.setOut(originalOut);
        }
    }
}
