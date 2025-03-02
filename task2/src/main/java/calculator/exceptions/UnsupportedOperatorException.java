package calculator.exceptions;

public class UnsupportedOperatorException extends StackCalculatorException {
    public UnsupportedOperatorException(String operator) {
        super("Unsupported operator: " + operator);
    }
}
