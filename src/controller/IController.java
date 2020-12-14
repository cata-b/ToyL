package controller;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import model.PrgState;
import model.exceptions.ProgramFinishedException;
import model.statements.IStmt;
import model.values.IValue;
import repository.IRepository;

import java.util.List;
import java.util.Map;

public interface IController {
    void setProgram(PrgState state);
    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList);
    void allStep();
    void oneStep() throws ProgramFinishedException;
}
