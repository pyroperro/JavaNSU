package calculator.commands;

import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgressionTest {

    private Progression progressionCommand;
    private Context context;
    private String[] args;

    @BeforeEach
    void setUp() {
        progressionCommand = new Progression();
        context = new Context();
    }

    @Test
    void testArithmeticProgression() {
        args = new String[]{"True", "10"};
        context.push(1f);
        context.push(1f);

        progressionCommand.execute(context, args);
        assertEquals(55f, context.peek());
    }

    @Test
    void testGeometricProgression() {
        args = new String[]{"False", "10"};
        context.push(1f);
        context.push(2f);

        progressionCommand.execute(context, args);
        assertEquals(1023f, context.peek());
    }

    @Test
    void testDefineIllegalName() {
        args = new String[]{"True", "-10"};
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            progressionCommand.execute(context, args);
        });
        assertEquals("Number of progression elements should not be negative", e1.getMessage());
    }
}