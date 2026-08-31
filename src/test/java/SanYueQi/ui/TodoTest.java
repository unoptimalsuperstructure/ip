package SanYueQi.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoTest {
    @Test
    void testPrintMessage1() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            Task task = SanYueQi.masterTaskList.printNewTask(ToDo.makeToDo("todo 3 4".split("\\s+")));

            assertNotNull(task);
            assertTrue(task.toString().contains("[T][ ] 3 4"));

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

            Task task = SanYueQi.masterTaskList.printNewTask(ToDo.makeToDo("todo".split("\\s+")));

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

            Task task = SanYueQi.masterTaskList.printNewTask(ToDo.makeToDo("todo  deadline  event  !#$ /   /from/from /by /from \\1\" /to   /to 2\\\"".split("\\s+")));

            assertNotNull(task);
            assertTrue(task.toString().contains("[T][ ] deadline event !#$ / /from/from /by /from \\1\" /to /to 2\\\""));

        } finally {
            System.setOut(originalOut);
        }
    }
}
