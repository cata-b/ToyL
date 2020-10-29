package controller;

import model.PrgState;
import model.collections.MyIStack;
import model.exceptions.MyException;
import model.statements.IStmt;
import repository.IRepository;

public class Controller implements IController {
    private IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setProgram(PrgState state) {
        repository.clear();
        repository.addProgram(state);
    }

    private PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        if(stk.empty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    @Override
    public String allStep(boolean printStates) throws MyException {
        StringBuilder result = new StringBuilder();
        PrgState prg = repository.getCrtPrg();
        if (printStates)
            result.append(prg).append("\n");
        while (!prg.getExeStack().empty()){
            oneStep(prg);
            if (printStates)
                result.append(prg).append("\n");
        }
        for (int i = 0; i < prg.getOut().size(); i++)
            result.append("\nProgram output: \n").append(prg.getOut().get(i));
        return result.toString();
    }
}
