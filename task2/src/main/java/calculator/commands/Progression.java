package calculator.commands;
import calculator.Context;
import calculator.exceptions.InfinityException;
import calculator.exceptions.OperatorArgumentException;
import calculator.exceptions.StackUnderflowException;

import java.util.NoSuchElementException;

public class Progression implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 2) {
            throw new OperatorArgumentException("Progression takes two arguments");
        }

        if (context.stackSize() < 2) {
            throw new StackUnderflowException("Progression needs two values on stack: n_0 and n");
        }

        boolean isArithmetic = Boolean.parseBoolean(args[0]);
        int num = Integer.parseInt(args[1]);

        if (num < 0) {
            throw new OperatorArgumentException("Number of progression elements should not be negative");
        }

        float step = context.pop();
        float first = context.pop();
        float res;

        if (isArithmetic) {
            res = ((first + (first +  step * (num - 1))) * num / 2);
        } else {
            if (step == 1) {
                res = (num * first);
            }
            else {
                res = ((float) (first * (Math.pow(step, num) - 1)) / (step - 1));
            }
        }

        if (Float.isInfinite(res)) {
            context.push(first);
            context.push(step);
            throw new InfinityException("Progression");
        }

        context.push(res);
    }
}