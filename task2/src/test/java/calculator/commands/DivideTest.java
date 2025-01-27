package calculator.commands;

import calculator.Context;
import jdk.jshell.execution.JdiDefaultExecutionControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DivideTest {

    private Divide divideCommand;
    private Context context;
    private String[] args;

    @BeforeEach
    void setUp() {
        divideCommand = new Divide();
        context = new Context();
        args = new String[]{"gibberish", "42"};
    }

    @Test
    void testDivideTwoNumbers() {
        context.push(0.25f);
        context.push(4);
        context.push(10);


        divideCommand.execute(context, args);
        assertEquals(2.5f, context.peek());

        divideCommand.execute(context, args);
        assertEquals(10f, context.peek());
    }

    @Test
    void testDivideNoValues() {
        context.push(0.0f);

        Exception e1 = assertThrows(NoSuchElementException.class, () -> {
            divideCommand.execute(context, args);
        });
        assertEquals("Nothing to divide", e1.getMessage());
        assertEquals(0.0f, context.peek());

        context.pop();
        Exception e2 = assertThrows(NoSuchElementException.class, () -> {
            divideCommand.execute(context, args);
        });
        assertEquals("Nothing to divide", e2.getMessage());
        assertTrue(context.isEmpty());
    }

    @Test
    void testDivideByZero() {
        context.push(0.0f);
        context.push(11.11f);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
           divideCommand.execute(context, args);
        });
        assertEquals("Cannot divide by zero", e.getMessage());
        assertEquals(11.11f, context.peek());
    }
}