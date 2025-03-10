package calculator.commands;

import calculator.Context;
import calculator.exceptions.StackUnderflowException;

import java.util.NoSuchElementException;

public class Print implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.isEmpty()) {
            throw new StackUnderflowException("Nothing to print");
        } else {
            float a = context.peek();
            System.out.println(a);
        }
    }
}
