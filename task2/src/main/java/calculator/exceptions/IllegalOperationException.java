package calculator.exceptions;

public class IllegalOperationException extends StackCalculatorException {
    public IllegalOperationException(String message) {
        super(message);
    }
}
