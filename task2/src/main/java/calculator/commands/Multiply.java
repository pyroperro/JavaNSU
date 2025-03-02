package calculator.commands;

import calculator.Context;
import calculator.exceptions.InfinityException;
import calculator.exceptions.StackUnderflowException;

import java.util.NoSuchElementException;

public class Multiply implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.stackSize() < 2) {
            throw new StackUnderflowException("Nothing to multiply");
        }

        float a = context.pop();
        float b = context.pop();
        float res = a * b;

        if (Float.isInfinite(res)) {
            context.push(b);
            context.push(a);
            throw new InfinityException("Multiply");
        }

        context.push(res);
    }
}
