package calculator.commands;

import calculator.Context;
import calculator.exceptions.OperatorArgumentException;

public class Define implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 2) {
            throw new OperatorArgumentException("Define takes two arguments");
        }

        String varName = args[0];
        float varValue = Float.parseFloat(args[1]);

        if (Character.isDigit(varName.charAt(0))) {
            throw new OperatorArgumentException("Variable name cannot start with a digit");
        }

        context.addVariable(varName, varValue);
    }
}