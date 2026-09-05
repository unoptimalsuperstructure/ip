package sanyueqi.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTest {
    @Test
    void normalTestPrintMessage() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            String s = SanYueQi.masterTaskList.printNewTask(Deadline.makeDeadline("deadline 3 /by 4".split("\\s+")));

            assertNotNull(s);
            assertTrue(s.contains("[D][ ] 3 (by: 4)"));

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

            String s = SanYueQi.masterTaskList.printNewTask(Deadline.makeDeadline("deadline 3 by 4".split("\\s+")));

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

            String s = SanYueQi.masterTaskList.printNewTask(Deadline.makeDeadline("deadline  event  todo  !#$ /   /from/from /by /from \\1\" /to   /to 2\\\"".split("\\s+")));

            assertNotNull(s);
            assertTrue(s.contains("[D][ ] event todo !#$ / /from/from (by: /from \\1\" /to /to 2\\\")"));

        } finally {
            System.setOut(originalOut);
        }
    }
}
