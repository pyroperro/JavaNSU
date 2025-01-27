package calculator.commands;

import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SqrtTest {

    private Sqrt sqrtCommand;
    private Context context;
    private String[] args;

    @BeforeEach
    void setUp() {
        sqrtCommand = new Sqrt();
        context = new Context();
        args = new String[]{"gibberish", "42"};
    }

    @Test
    void testSqrt() {
        context.push(196f);
        sqrtCommand.execute(context, args);
        assertEquals(14f, context.peek());
    }

    @Test
    void testSqrtNegative() {
        context.push(-1f);
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
           sqrtCommand.execute(context, args);
        });
        assertEquals("Cannot square a negative number", e.getMessage());
    }

    @Test
    void testSqrtNoValues() {
        Exception e = assertThrows(NoSuchElementException.class, () -> {
           sqrtCommand.execute(context, args);
        });
        assertEquals("Nothing to take square of", e.getMessage());
        assertTrue(context.isEmpty());
    }
}