package repository;

import model.PrgState;
import model.collections.interfaces.MyIList;

import java.util.List;

public interface IRepository {
    MyIList<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
    void logPrgStateExec(PrgState state);
    void logPrgError(String message);
    void addProgram(PrgState state);
    void clear();
}
