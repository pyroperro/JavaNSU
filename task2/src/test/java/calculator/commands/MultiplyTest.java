package calculator.commands;

import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MultiplyTest {

    private Multiply multiplyCommand;
    private Context context;
    private String[] args;

    @BeforeEach
    void setUp() {
        multiplyCommand = new Multiply();
        context = new Context();
        args = new String[]{"gibberish", "42"};
    }

    @Test
    void testMultiplyTwoNumbers() {
        context.push(0f);
        context.push(-4f);
        context.push(-1.25f);
        context.push(2f);

        multiplyCommand.execute(context, args);
        assertEquals(-2.5f, context.peek());

        multiplyCommand.execute(context, args);
        assertEquals(10f, context.peek());

        multiplyCommand.execute(context, args);
        assertEquals(0f, context.peek());
    }

    @Test
    void tessMultiplyNoValues() {
        context.push(1f);

        Exception e1 = assertThrows(NoSuchElementException.class, () -> {
            multiplyCommand.execute(context, args);
        });

        assertEquals("Nothing to multiply", e1.getMessage());
        assertEquals(1f, context.peek());

        context.pop();
        Exception e2 = assertThrows(NoSuchElementException.class, () -> {
            multiplyCommand.execute(context, args);
        });
        assertEquals("Nothing to multiply", e2.getMessage());
        assertTrue(context.isEmpty());
    }
}