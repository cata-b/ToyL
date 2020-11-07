package model;

import model.collections.MyIDictionary;
import model.collections.MyIList;
import model.collections.MyIStack;
import model.exceptions.EmptyCollectionException;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;

public class PrgState {
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, IValue> symTable;
    private final MyIList<IValue> out;
    private final MyIDictionary<StringValue, BufferedReader> fileTable;
    private IStmt originalProgram;


    public PrgState(@NotNull MyIStack<IStmt> stk, @NotNull MyIDictionary<String, IValue> symTable, @NotNull MyIList<IValue> ot, @NotNull MyIDictionary<StringValue, BufferedReader> fileTable, @NotNull IStmt prg) {
        exeStack = stk;
        this.symTable = symTable;
        out = ot;
        this.fileTable = fileTable;
        originalProgram = prg.copy();
        stk.push(prg);
    }

    @Override
    public String toString() {
        IStmt current = null;
        try {
            current = exeStack.peek();
        }
        catch (EmptyCollectionException ignored) {}
        return "ExeStack:" + System.lineSeparator() +
                exeStack.toString() + System.lineSeparator() +
                "SymTable:" + System.lineSeparator() +
                symTable.toString() + System.lineSeparator() +
                "Out:" + System.lineSeparator() +
                out.toString() + System.lineSeparator() +
                "FileTable:" + System.lineSeparator() +
                fileTable.toString();
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

}
