package repository;

import model.PrgState;
import model.exceptions.EmptyCollectionException;
import model.exceptions.MyException;

public interface IRepository {
    PrgState getCrtPrg() throws MyException;
    void logPrgStateExec() throws MyException;
    void addProgram(PrgState state);
    void clear();
}
