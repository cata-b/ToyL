package model;

import model.collections.MyIDictionary;
import model.collections.MyIList;
import model.collections.MyIStack;
import model.exceptions.EmptyCollectionException;
import model.statements.IStmt;
import model.values.IValue;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> out;
    private IStmt originalProgram;


    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symTable, MyIList<IValue> ot, IStmt prg) {
        exeStack = stk;
        this.symTable = symTable;
        out = ot;
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
        return //"Program: " + originalProgram.toString() + "\n" +
                "Current statement: " + (current == null ? "None" : current) + "\n" +
                "Symbol table: " + symTable.toString() + "\n" +
                "Out: " + out.toString();
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> stack){
        exeStack = stack;
    }


    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public void setOut(MyIList<IValue> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }
}
