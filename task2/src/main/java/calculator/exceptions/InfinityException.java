package calculator.exceptions;

public class InfinityException extends StackCalculatorException {
    public InfinityException(String operation) {
        super("Infinite value occured in " + operation + " operation");
    }
}
