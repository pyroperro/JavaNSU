package calculator.commands;

import calculator.Context;
import calculator.exceptions.StackCalculatorException;

import java.io.IOException;

public interface Command {
    void execute(Context context, String[] args) throws StackCalculatorException;
}


