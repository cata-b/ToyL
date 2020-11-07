package model.exceptions;

public class ExpressionEvaluationException extends StatementExecutionException {
    public ExpressionEvaluationException(String msg) {
        super(msg);
    }
}