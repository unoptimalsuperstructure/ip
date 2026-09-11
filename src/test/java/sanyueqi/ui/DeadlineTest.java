package sanyueqi.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        } catch (SYQException e) {
            fail();

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

        } catch (SYQException e) {
            assertTrue(true);

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

        } catch (SYQException e) {
            fail();

        } finally {
            System.setOut(originalOut);
        }
    }
}
