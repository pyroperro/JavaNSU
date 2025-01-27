package java.calculator.commands;

import java.calculator.Context;
import java.io.IOException;
import java.util.NoSuchElementException;

public interface Command {
    abstract void execute(Context context, String[] args) throws IOException;
}

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

class Push implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("java.calculator.commands.Push takes one argument");
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

class Add implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.stackSize() < 2) {
            throw new NoSuchElementException("Nothing to add");
        } else {
            float a = context.pop();
            float b = context.pop();
            context.push(a + b);
        }
    }
}

class Subtract implements Command {
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

class Multiply implements Command {
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

class Divide implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.stackSize() < 2) {
            throw new NoSuchElementException("Nothing to divide");
        } else {
            float a = context.pop();
            float b = context.pop();
            context.push(a / b);
        }
    }
}

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

class Print implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.isEmpty()) {
            throw new NoSuchElementException("Nothing to print");
        } else {
            float a = context.peek();
            System.out.println(a);
        }
    }
}

class Define implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("java.calculator.commands.Define takes two arguments");
        }

        String varName = args[0];
        float varValue = Float.parseFloat(args[1]);

        if (Character.isDigit(varName.charAt(0))) {
            throw new IllegalArgumentException("Variable name cannot start with a digit");
        }

        context.addVariable(varName, varValue);
    }
}