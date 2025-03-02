package calculator;

import calculator.exceptions.NoSuchVariableException;
import calculator.exceptions.StackCalculatorException;
import calculator.exceptions.StackUnderflowException;

import java.util.*;

public class Context {
    private Stack<Float> stack;
    private Map<String, Float> varList;

    public Context() {
        this.stack = new Stack<Float>();
        this.varList = new HashMap<String, Float>();
    }

    public void push(float value) {
        stack.push(value);
    }

    public float pop() {
        return stack.pop();
    }

    public float peek() {
        if (stack.isEmpty()) {
            throw new StackUnderflowException("Cannot peek empty stack");
        }
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int stackSize() {
        return stack.size();
    }

    public void addVariable(String varName, float value) {
        varList.put(varName, value);
    }

    public float findVariable(String varName) throws StackCalculatorException {
        if (varList.get(varName) == null) {
            throw new NoSuchVariableException(varName);
        }
        return varList.get(varName);
    }
}
