package controller;

import model.PrgState;
import model.exceptions.FileException;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {
    private ExecutorService executor;
    private final IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setProgram(PrgState state) {
        repository.clear();
        repository.addProgram(state);
    }

    @Override
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private void oneStepForAllPrg(List<PrgState> prgList) {
        prgList.forEach(repository::logPrgStateExec);
        List<Callable<PrgState>> callList = prgList.stream()
                .map(p -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            repository.logPrgError(e.toString());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            repository.logPrgError(e.toString());
        }
        if (newPrgList != null)
            prgList.addAll(newPrgList);
        prgList.forEach(repository::logPrgStateExec);

        repository.setPrgList(prgList);
    }

    @Override
    public void allStep() {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while (prgList.size() > 0) {
            var heap = prgList.get(0).getHeap();
            var symTblAddr =
            heap.setContent(garbageCollector(
                    new ArrayList<>(prgList.stream()
                            .flatMap(prg -> getAddrFromSymTable(prg.getSymTable().getContent().values()).stream())
                            .collect(Collectors.toSet())),
                    getAddrFromHeap(heap.getContent()),
                    heap.getContent()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    private Map<Integer, IValue> garbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeap(Map<Integer, IValue> heap) {
        return heap.values().stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }
}
