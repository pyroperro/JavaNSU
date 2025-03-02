package calculator.commands;

import calculator.Context;
import calculator.exceptions.InfinityException;
import calculator.exceptions.StackUnderflowException;

import java.util.NoSuchElementException;

public class Subtract implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.stackSize() < 2) {
            throw new StackUnderflowException("Nothing to subtract");
        }

        float a = context.pop();
        float b = context.pop();
        float res = b - a;

        if (Float.isInfinite(res)) {
            context.push(b);
            context.push(a);
            throw new InfinityException("Subtract");
        }

        context.push(res);
    }
}
