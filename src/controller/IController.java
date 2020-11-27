package controller;

import model.PrgState;
import model.collections.MyIList;
import model.exceptions.MyException;

import java.util.List;

public interface IController {
    void setProgram(PrgState state);
    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList);
    void allStep();
}
