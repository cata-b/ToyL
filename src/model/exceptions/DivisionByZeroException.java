package model.exceptions;

public class DivisionByZeroException extends ExpressionEvaluationException {
    public DivisionByZeroException(String msg) {
        super(msg);
    }
}
