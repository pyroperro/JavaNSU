package calculator.commands;

import calculator.Context;

import java.util.NoSuchElementException;

public class Subtract implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.stackSize() < 2) {
            throw new NoSuchElementException("Nothing to subtract");
        } else {
            float a = context.pop();
            float b = context.pop();
            context.push(a - b);
        }
    }
}
