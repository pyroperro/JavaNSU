package calculator.commands;

import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class PrintTest {

    private  Print printCommand;
    private Context context;
    private String[] args;

    @BeforeEach
    void setUp() {
        printCommand = new Print();
        context = new Context();
        args = new String[]{"gibberish", "42"};
    }

    @Test
    void testPrint() {
        context.push(42.15f);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            printCommand.execute(context, args);
            String out = outputStream.toString().trim();
            assertEquals("42.15", out);
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testPrintNoValue() {
        Exception e = assertThrows(NoSuchElementException.class, () -> {
           printCommand.execute(context, args);
        });
        assertEquals("Nothing to print", e.getMessage());
    }
}