package calculator.commands;

import calculator.Context;
import java.io.IOException;

public interface Command {
    abstract void execute(Context context, String[] args) throws IOException;
}


