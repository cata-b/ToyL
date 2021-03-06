package model.statements;

import model.PrgState;
import model.collections.interfaces.MyIDictionary;
import model.exceptions.*;
import model.expressions.IExp;
import model.types.IType;
import model.types.StringType;
import model.values.StringValue;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt {
    private final @NotNull IExp fileNameExp;

    public OpenRFileStmt(@NotNull IExp fileNameExp) {
        this.fileNameExp = fileNameExp;
    }

    public @NotNull IExp getFileNameExp() {
        return fileNameExp;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        var file_name = fileNameExp.eval(state.getSymTable(), state.getHeap());
        var fileTable = state.getFileTable();

        if (!file_name.getType().equals(new StringType()))
            throw new InvalidTypeException("Invalid type in openRFile: expected string, got " + file_name.getType());

        var fileNameConv = (StringValue)file_name;

        if (fileTable.containsKey(fileNameConv))
            throw new StatementExecutionException("File " + fileNameConv + " is already open");

        BufferedReader fileReader;
        try {
            fileReader = new BufferedReader(new FileReader(fileNameConv.getVal()));
        }
        catch (IOException e) {
            throw new FileException("File exception: " + e);
        }

        fileTable.put(fileNameConv, fileReader);
        return null;
    }

    @Override
    public @NotNull MyIDictionary<String, IType> typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) throws TypeCheckException {
        if (!fileNameExp.typeCheck(typeEnvironment).equals(new StringType()))
            throw new TypeCheckException("Open read file statement: file name is not of type " + new StringType());
        return typeEnvironment;
    }

    @Override
    public IStmt copy() {
        return new OpenRFileStmt(fileNameExp.copy());
    }

    @Override
    public String toString() {
        return "openRFile(" + fileNameExp.toString() + ")";
    }
}
