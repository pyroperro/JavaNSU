package calculator.commands;

import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AddTest {

    private Add addCommand;
    private Context context;
    private String[] args;

    @BeforeEach
    void setUp() {
        addCommand = new Add();
        context = new Context();
        args = new String[]{"gibberish", "42"};
    }

    @Test
    void testAddTwoNumbers() {
        context.push(1f);
        context.push(-1.25f);
        context.push(0f);

        addCommand.execute(context, args);
        assertEquals(-1.25f, context.peek());

        addCommand.execute(context, args);
        assertEquals(-0.25f, context.peek());
    }

    @Test
    void  tessAddNoValues() {
        context.push(1f);

        Exception e1 = assertThrows(NoSuchElementException.class, () -> {
            addCommand.execute(context, args);
        });

        assertEquals("Nothing to add", e1.getMessage());
        assertEquals(1f, context.peek());

        context.pop();
        Exception e2 = assertThrows(NoSuchElementException.class, () -> {
            addCommand.execute(context, args);
        });

        assertEquals("Nothing to add", e2.getMessage());
        assertTrue(context.isEmpty());
    }
}