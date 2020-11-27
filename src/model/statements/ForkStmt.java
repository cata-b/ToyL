package model.statements;

import model.PrgState;
import model.collections.MyDictionary;
import model.collections.MyIDictionary;
import model.collections.MyIStack;
import model.collections.MyStack;
import model.exceptions.MyException;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ForkStmt implements IStmt {
    private final @NotNull IStmt innerProgram;

    public ForkStmt(@NotNull IStmt innerProgram) {
        this.innerProgram = innerProgram;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {

        MyIDictionary<String, IValue> childSymTbl = new MyDictionary<>();
        childSymTbl.setContent(state.getSymTable().getContent().entrySet().stream()
            .map((Map.Entry<String, IValue> e) -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().copy()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        return new PrgState(new MyStack<>(), childSymTbl, state.getOut(), state.getFileTable(), state.getHeap(), innerProgram);
    }

    @Override
    public IStmt copy() {
        return new ForkStmt(innerProgram.copy());
    }

    @Override
    public String toString() {
        return "fork(" + innerProgram + ")";
    }

    public @NotNull IStmt getInnerProgram() {
        return innerProgram;
    }
}
