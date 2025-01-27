package calculator.commands;

import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class PushTest {

    private Push pushCommand;
    private Context context;
    private String[] args;

    @BeforeEach
    void setUp() {
        pushCommand = new Push();
        context = new Context();
    }

    @Test
    void testPush() {
        args = new String[]{"5"};
        pushCommand.execute(context, args);
        assertEquals(5f, context.peek());

        args = new String[]{"-2.121"};
        pushCommand.execute(context, args);
        assertEquals(-2.121f, context.peek());

        args = new String[]{"0"};
        pushCommand.execute(context, args);
        assertEquals(0f, context.peek());
    }

    @Test
    void testPushWrongNumOfArguments() {
        args = new String[]{"2.22222", "5"};
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
           pushCommand.execute(context, args);
        });
        assertEquals("Push takes one argument", e.getMessage());

        args = new String[]{};
        e = assertThrows(IllegalArgumentException.class, () -> {
            pushCommand.execute(context, args);
        });
        assertEquals("Push takes one argument", e.getMessage());
    }

    @Test
    void testPushIllegalArgument() {
        args = new String[]{"gibberish"};
        Exception e = assertThrows(NoSuchElementException.class, () -> {
            pushCommand.execute(context, args);
        });
        assertEquals("Uninitialized variable: " + args[0], e.getMessage());
    }

    @Test
    void testPushVariable() {
        args = new String[]{"x"};
        context.addVariable("x", 5.01f);
        pushCommand.execute(context, args);
        assertEquals(5.01f, context.peek());
    }
}