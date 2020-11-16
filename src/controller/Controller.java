package controller;

import model.PrgState;
import model.collections.MyIStack;
import model.exceptions.MyException;
import model.statements.IStmt;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller implements IController {
    private final IRepository repository;

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
        if (stk.empty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    @Override
    public String allStep(boolean printStates) throws MyException {
        StringBuilder result = new StringBuilder();
        PrgState prg = repository.getCrtPrg();
        if (printStates)
            result.append(prg).append("\n");
        repository.logPrgStateExec();
        while (!prg.getExeStack().empty()) {
            oneStep(prg);

            prg.getHeap().setContent(garbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContent().values()),
                    getAddrFromHeap(prg.getHeap().getContent()),
                    prg.getHeap().getContent()));

            if (printStates)
                result.append(prg).append("\n");
            repository.logPrgStateExec();
        }
        return result.toString();
    }

    private Map<Integer, IValue> garbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue)v).getAddress())
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeap(Map<Integer, IValue> heap) {
        return heap.values().stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue)v).getAddress())
                .collect(Collectors.toList());
    }
}
