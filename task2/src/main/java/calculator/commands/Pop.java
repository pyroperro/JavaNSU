package calculator.commands;

import calculator.Context;
import calculator.exceptions.StackUnderflowException;

import java.util.NoSuchElementException;

public class Pop implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.isEmpty()) {
            throw new StackUnderflowException("Nothing to pop");
        } else {
            context.pop();
        }
    }
}
