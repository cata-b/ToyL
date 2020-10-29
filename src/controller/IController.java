package controller;

import model.PrgState;
import model.exceptions.MyException;

public interface IController {
    void setProgram(PrgState state);
    String allStep(boolean printStates) throws MyException;
}
