package calculator.commands;

import calculator.Context;

import java.util.NoSuchElementException;

public class Multiply implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.stackSize() < 2) {
            throw new NoSuchElementException("Nothing to multiply");
        } else {
            float a = context.pop();
            float b = context.pop();
            context.push(a * b);
        }
    }
}
