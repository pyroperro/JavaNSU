package calculator.commands;

import calculator.Context;
import calculator.exceptions.IllegalOperationException;
import calculator.exceptions.InfinityException;
import calculator.exceptions.StackUnderflowException;

import java.util.NoSuchElementException;

public class Divide implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.stackSize() < 2) {
            throw new StackUnderflowException("Nothing to divide");
        }
        float a = context.pop();
        float b = context.pop();
        if (b == 0f) {
            context.push(b);
            context.push(a);
            throw new IllegalOperationException("Cannot divide by zero");
        }

        float res = b / a;

        if (Float.isInfinite(res)) {
            context.push(b);
            context.push(a);
            throw new InfinityException("Divide");
        }

        context.push(res);
    }
}
