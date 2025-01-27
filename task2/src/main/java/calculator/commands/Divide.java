package calculator.commands;

import calculator.Context;

import java.util.NoSuchElementException;

public class Divide implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.stackSize() < 2) {
            throw new NoSuchElementException("Nothing to divide");
        } else {
            float a = context.pop();
            float b = context.pop();
            if (b == 0f) {
                context.push(b);
                context.push(a);
                throw new IllegalArgumentException("Cannot divide by zero");
            }
            context.push(a / b);
        }
    }
}
