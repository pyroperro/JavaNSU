package calculator.commands;

import calculator.Context;

public class Define implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("calculator.commands.Define takes two arguments");
        }

        String varName = args[0];
        float varValue = Float.parseFloat(args[1]);

        if (Character.isDigit(varName.charAt(0))) {
            throw new IllegalArgumentException("Variable name cannot start with a digit");
        }

        context.addVariable(varName, varValue);
    }
}