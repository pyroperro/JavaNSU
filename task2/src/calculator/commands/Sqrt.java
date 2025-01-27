package calculator.commands;

import calculator.Context;

import java.util.NoSuchElementException;

class Sqrt implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.isEmpty()) {
            throw new NoSuchElementException("Nothing to take square of");
        } else {
            float a = context.pop();
            if (a < 0) {
                throw new IllegalArgumentException("Cannot square a negative number");
            }
            context.push((float) Math.sqrt(a));
        }
    }
}
