package calculator.commands;

import calculator.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefineTest {

    private Define defineCommand;
    private Context context;
    private String[] args;

    @BeforeEach
    void setUp() {
        defineCommand = new Define();
        context = new Context();
    }

    @Test
    void testDefine() {
        args = new String[]{"x", "-5.55"};

        defineCommand.execute(context, args);

        assertEquals(-5.55f, context.findVariable("x"));
    }

    @Test
    void testDefineIllegalName() {
        args = new String[]{"52", "42"};
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
            defineCommand.execute(context, args);
        });
        assertEquals("Variable name cannot start with a digit", e1.getMessage());

        args = new String[]{"52abav", "42"};
        Exception e2 = assertThrows(IllegalArgumentException.class, () -> {
            defineCommand.execute(context, args);
        });
        assertEquals("Variable name cannot start with a digit", e2.getMessage());
    }

    @Test
    void testDefineWrongArguments() {
        args = new String[]{"x", "42", "fff"};
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            defineCommand.execute(context, args);
        });
        assertEquals("Define takes two arguments", e.getMessage());
    }
}