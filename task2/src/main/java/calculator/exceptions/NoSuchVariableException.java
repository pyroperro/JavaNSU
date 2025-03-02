package calculator.exceptions;

public class NoSuchVariableException extends StackCalculatorException {
    public NoSuchVariableException(String varName) {
        super("No variable \"" + varName + "\" found");
    }
}
