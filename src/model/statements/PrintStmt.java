package model.statements;

import model.PrgState;
import model.collections.MyIDictionary;
import model.exceptions.StatementExecutionException;
import model.exceptions.TypeCheckException;
import model.expressions.IExp;
import model.types.IType;
import org.jetbrains.annotations.NotNull;

public class PrintStmt implements IStmt {
    private final @NotNull IExp exp;

    public PrintStmt(@NotNull IExp exp) {
        this.exp = exp;
    }

    public @NotNull IExp getExp() {
        return exp;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws StatementExecutionException {
        state.getOut().add(exp.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public @NotNull MyIDictionary<String, IType> typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) throws TypeCheckException {
        exp.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public IStmt copy() {
        return new PrintStmt(exp.copy());
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }

}
