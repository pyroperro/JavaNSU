package calculator.commands;

import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class PopTest {

    private Pop popCommand;
    private Context context;
    private String[] args;

    @BeforeEach
    void setUp() {
        popCommand = new Pop();
        context = new Context();
        args = new String[]{"gibberish", "42"};
    }

    @Test
    void testPop() {
        context.push(5f);
        context.push(2.121f);
        context.push(0f);

        popCommand.execute(context, args);
        assertEquals(2.121f, context.peek());

        popCommand.execute(context, args);
        assertEquals(5f, context.peek());

        popCommand.execute(context, args);
        assertTrue(context.isEmpty());
    }

    @Test
    void testPopEmptyStack() {
        Exception e = assertThrows(NoSuchElementException.class, () -> {
           popCommand.execute(context, args);
        });
        assertEquals("Nothing to pop", e.getMessage());
    }
}