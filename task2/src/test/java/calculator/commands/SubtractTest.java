package calculator.commands;

import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SubtractTest {

    private Subtract subtractCommand;
    private Context context;
    private String[] args;

    @BeforeEach
    void setUp() {
        subtractCommand = new Subtract();
        context = new Context();
        args = new String[]{"gibberish", "42"};
    }

    @Test
    void testSubtractTwoNumbers() {
        context.push(5f);
        context.push(3f);
        context.push(8f);

        subtractCommand.execute(context, args);
        assertEquals(5f, context.peek());

        subtractCommand.execute(context, args);
        assertEquals(0f, context.peek());
    }

    @Test
    void testSubtractNoValues() {
        context.push(5f);

        Exception e1 = assertThrows(NoSuchElementException.class, () -> {
            subtractCommand.execute(context, args);
        });

        assertEquals("Nothing to subtract", e1.getMessage());
        assertEquals(5f, context.peek());

        context.pop();
        Exception e2 = assertThrows(NoSuchElementException.class, () -> {
            subtractCommand.execute(context, args);
        });

        assertEquals("Nothing to subtract", e2.getMessage());
        assertTrue(context.isEmpty());
    }
}