package model.exceptions;

public class InvalidTypeException extends ExpressionEvaluationException {
    public InvalidTypeException(String msg) {
        super(msg);
    }
}
