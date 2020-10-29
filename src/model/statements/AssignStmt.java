package model.statements;

import model.PrgState;
import model.collections.*;
import model.exceptions.InvalidTypeException;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.values.IValue;
import model.types.*;

public class AssignStmt implements IStmt {
    private String id;
    private IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IExp getExp() {
        return exp;
    }

    public void setExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, IValue> symTbl = state.getSymTable();

        if (symTbl.containsKey(id)) {
            IValue val = exp.eval(symTbl);
            IType typId = (symTbl.get(id)).getType();
            if ((val.getType()).equals(typId))
                symTbl.put(id, val);
            else
                throw new InvalidTypeException("Declared type of variable " + id + " and type of the assigned expression do not match.");
        }
        else throw new StatementExecutionException("the used variable" + id + " was not declared before");
            return state;
    }

    @Override
    public IStmt copy() {
        return new AssignStmt(id, exp != null ? exp.copy() : null);
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
}
