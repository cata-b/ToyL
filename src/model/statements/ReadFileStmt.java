package model.statements;

import model.PrgState;
import model.exceptions.FileException;
import model.exceptions.InvalidTypeException;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.types.IntType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private final IExp fileNameExp;
    private final String varName;

    public ReadFileStmt(@NotNull IExp fileNameExp, @NotNull String varName) {
        this.fileNameExp = fileNameExp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        var fileNameEval = fileNameExp.eval(state.getSymTable());
        if (!fileNameEval.getType().equals(new StringType()))
            throw new InvalidTypeException("Invalid type for readFile filename: expected string, got " + fileNameEval.getType());
        var fileNameConv = (StringValue)fileNameEval;

        var symTbl = state.getSymTable();
        if (!symTbl.containsKey(varName))
            throw new StatementExecutionException("The used variable" + varName + " was not declared before");
        var val = symTbl.get(varName);
        if (!val.getType().equals(new IntType()))
            throw new InvalidTypeException("Declared type of variable " + varName + " is " + val.getType() + " and " + new IntType() + " is expected");

        var fileTable = state.getFileTable();
        if (!fileTable.containsKey(fileNameConv))
            throw new StatementExecutionException("The used file " + fileNameConv + " is not opened");
        var file = fileTable.get(fileNameConv);

        int number = 0;
        try {
            number = Integer.parseInt(file.readLine());
        } catch (IOException e) {
            throw new FileException("File " + fileNameConv +" does not have lines left");
        } catch (NumberFormatException e) {
           throw new FileException("File " + fileNameConv + " is not in the correct format");
        }

        symTbl.put(varName, new IntValue(number));
        return state;
    }

    @Override
    public IStmt copy() {
        return new ReadFileStmt(fileNameExp.copy(), varName);
    }

    @Override
    public String toString() {
        return "readFile(" + fileNameExp + ", " + varName + ")";
    }

    public IExp getFileNameExp() {
        return fileNameExp;
    }

    public String getVarName() {
        return varName;
    }
}
