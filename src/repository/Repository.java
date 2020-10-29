package repository;

import model.PrgState;
import model.collections.MyIList;
import model.collections.MyList;
import model.exceptions.EmptyCollectionException;
import model.exceptions.MyException;

public class Repository implements IRepository {
    MyIList<PrgState> programs = new MyList<>();

    @Override
    public PrgState getCrtPrg() throws MyException {
        return programs.get(0);
    }

    @Override
    public void addProgram(PrgState state) {
        programs.add(state);
    }

    @Override
    public void clear() {
        programs.clear();
    }
}
