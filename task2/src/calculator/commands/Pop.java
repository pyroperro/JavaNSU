package calculator.commands;

import calculator.Context;

import java.util.NoSuchElementException;

class Pop implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.isEmpty()) {
            throw new NoSuchElementException("Nothing to pop");
        } else {
            context.pop();
        }
    }
}
