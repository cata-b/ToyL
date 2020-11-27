package model.statements;

import model.PrgState;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.types.BoolType;
import model.values.BoolValue;
import org.jetbrains.annotations.NotNull;

public class WhileStmt implements IStmt {
    private final @NotNull IExp condition;
    private final @NotNull IStmt content;

    public WhileStmt(@NotNull IExp condition, @NotNull IStmt content) {
        this.condition = condition;
        this.content = content;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        var conditionResult = condition.eval(state.getSymTable(), state.getHeap());
        if (!conditionResult.getType().equals(new BoolType()))
            throw new StatementExecutionException("While condition has to be a " + new BoolType() + ". Given expression evaluates to " + conditionResult.getType());
        if (((BoolValue)conditionResult).getVal()) {
            state.getExeStack().push(this);
            state.getExeStack().push(content);
        }
        return null;
    }

    @Override
    public IStmt copy() {
        return new WhileStmt(condition.copy(), content.copy());
    }

    @Override
    public String toString() {
        return "while (" + condition + ") do (" + content + ")";
    }

    public @NotNull IExp getCondition() {
        return condition;
    }

    public @NotNull IStmt getContent() {
        return content;
    }
}
