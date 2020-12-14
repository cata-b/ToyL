package model.statements;

import model.PrgState;
import model.collections.interfaces.MyIDictionary;
import model.exceptions.*;
import model.expressions.IExp;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private final @NotNull IExp fileNameExp;
    private final @NotNull String varName;

    public ReadFileStmt(@NotNull IExp fileNameExp, @NotNull String varName) {
        this.fileNameExp = fileNameExp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        var fileNameEval = fileNameExp.eval(state.getSymTable(), state.getHeap());
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
        return null;
    }

    @Override
    public @NotNull MyIDictionary<String, IType> typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) throws TypeCheckException {
        if (!fileNameExp.typeCheck(typeEnvironment).equals(new StringType()))
            throw new TypeCheckException("Read file statement: file name is not of type " + new StringType());
        if (!(new IntType()).equals(typeEnvironment.get(varName)))
            throw new TypeCheckException("Read file statement: variable " + varName + " is not of type " + new IntType());
        return typeEnvironment;
    }

    @Override
    public IStmt copy() {
        return new ReadFileStmt(fileNameExp.copy(), varName);
    }

    @Override
    public String toString() {
        return "readFile(" + fileNameExp + ", " + varName + ")";
    }

    public @NotNull IExp getFileNameExp() {
        return fileNameExp;
    }

    public @NotNull String getVarName() {
        return varName;
    }
}
