package model;

import model.collections.interfaces.MyIDictionary;
import model.collections.interfaces.MyIHeap;
import model.collections.interfaces.MyIList;
import model.collections.interfaces.MyIStack;
import model.exceptions.EmptyCollectionException;
import model.exceptions.MyException;
import model.exceptions.ProgramFinishedException;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;

public class PrgState {

    private static final AtomicInteger programCount = new AtomicInteger(0);
    private static synchronized int getNewID() {
        return programCount.addAndGet(1);
    }

    private final int id;
    private final @NotNull MyIStack<IStmt> exeStack;
    private final @NotNull MyIDictionary<String, IValue> symTable;
    private final @NotNull MyIList<IValue> out;
    private final @NotNull MyIDictionary<StringValue, BufferedReader> fileTable;
    private final @NotNull MyIHeap heap;
    private @NotNull IStmt originalProgram;


    public PrgState(@NotNull MyIStack<IStmt> stk, @NotNull MyIDictionary<String, IValue> symTable, @NotNull MyIList<IValue> ot, @NotNull MyIDictionary<StringValue, BufferedReader> fileTable, @NotNull MyIHeap heap, @NotNull IStmt prg) {
        id = getNewID();
        exeStack = stk;
        this.symTable = symTable;
        out = ot;
        this.fileTable = fileTable;
        this.heap = heap;
        originalProgram = prg.copy();
        stk.push(prg);
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.empty())
            throw new ProgramFinishedException("Program is already finished");
        try {
            return exeStack.pop().execute(this);
        } catch (MyException e) {
            exeStack.clear(); // stop execution
            throw e;
        }

    }

    @Override
    public String toString() {
        IStmt current = null;
        try {
            current = exeStack.peek();
        }
        catch (EmptyCollectionException ignored) {}
        return "ID: " + id + System.lineSeparator() +
                "ExeStack:" + System.lineSeparator() +
                exeStack.toString() + System.lineSeparator() +
                "SymTable:" + System.lineSeparator() +
                symTable.toString() + System.lineSeparator() +
                "Out:" + System.lineSeparator() +
                out.toString() + System.lineSeparator() +
                "FileTable:" + System.lineSeparator() +
                fileTable.toString() +
                "Heap:" + System.lineSeparator() +
                heap.toString();
    }

    public Boolean isNotCompleted() {
        return !exeStack.empty();
    }

    public @NotNull MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public @NotNull MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public @NotNull MyIList<IValue> getOut() {
        return out;
    }

    public @NotNull MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public @NotNull IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(@NotNull IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public @NotNull MyIHeap getHeap() {
        return heap;
    }

    public int getId() {
        return id;
    }
}
