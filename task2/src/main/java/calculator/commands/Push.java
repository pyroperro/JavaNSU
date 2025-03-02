package calculator.commands;

import calculator.Context;
import calculator.exceptions.OperatorArgumentException;

public class Push implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 1) {
            throw new OperatorArgumentException("Push takes one argument");
        }

        String argument = args[0];

        try {
            float value = Float.parseFloat(argument);
            context.push(value);
        } catch (NumberFormatException e) {
            float value = context.findVariable(argument);
            context.push(value);
        }
    }
}
