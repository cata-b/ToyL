package model.statements;

import model.PrgState;
import model.exceptions.FileException;
import model.exceptions.InvalidTypeException;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.types.StringType;
import model.values.StringValue;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    private final @NotNull IExp fileNameExp;

    public CloseRFileStmt(@NotNull IExp fileNameExp) {
        this.fileNameExp = fileNameExp;
    }


    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        var fileNameVal = fileNameExp.eval(state.getSymTable(), state.getHeap());
        if (!fileNameVal.getType().equals(new StringType()))
            throw new InvalidTypeException("Invalid type in closeRFile: expected string, got " + fileNameVal.getType());
        var fileNameConv = (StringValue)fileNameVal;

        var fileTbl = state.getFileTable();
        if (!fileTbl.containsKey(fileNameConv))
            throw new StatementExecutionException("The used file " + fileNameConv + " is not opened");
        var file = fileTbl.get(fileNameConv);
        try {
            file.close();
        } catch (IOException e) {
            throw new FileException("Exception occurred while trying to close " + fileNameConv + ": " + e);
        }
        fileTbl.remove(fileNameConv);
        return state;
    }

    @Override
    public IStmt copy() {
        return new CloseRFileStmt(fileNameExp.copy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + fileNameExp + ")";
    }

    public @NotNull IExp getFileNameExp() {
        return fileNameExp;
    }
}
