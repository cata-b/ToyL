package model.exceptions;

public class InvalidTypeException extends StatementExecutionException {
    public InvalidTypeException(String msg) {
        super(msg);
    }
}
