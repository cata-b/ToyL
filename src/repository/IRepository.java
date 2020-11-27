package repository;

import model.PrgState;

import java.util.List;

public interface IRepository {
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
    void logPrgStateExec(PrgState state);
    void logPrgError(String message);
    void addProgram(PrgState state);
    void clear();
}
