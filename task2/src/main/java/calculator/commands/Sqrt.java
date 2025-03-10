package calculator.commands;

import calculator.Context;
import calculator.exceptions.IllegalOperationException;
import calculator.exceptions.StackUnderflowException;

import java.util.NoSuchElementException;

public class Sqrt implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.isEmpty()) {
            throw new StackUnderflowException("Nothing to take square of");
        } else {
            float a = context.pop();
            if (a < 0) {
                context.push(a);
                throw new IllegalOperationException("Cannot square a negative number");
            }
            context.push((float) Math.sqrt(a));
        }
    }
}
