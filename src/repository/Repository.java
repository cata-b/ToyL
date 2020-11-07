package repository;

import model.PrgState;
import model.collections.MyIList;
import model.collections.MyList;
import model.exceptions.FileException;
import model.exceptions.IndexOutOfBoundsException;
import model.exceptions.MyException;
import model.exceptions.NoProgramLoadedException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository {
    private final MyIList<PrgState> programs = new MyList<>();
    private final String logFilePath;

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() throws MyException {
        return programs.get(0);
    }

    @Override
    public void logPrgStateExec() throws FileException, NoProgramLoadedException {
        try {
            try (var logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
                logFile.println(programs.get(0));
            } catch (IndexOutOfBoundsException e) {
                throw new NoProgramLoadedException("No program has been loaded yet");
            }
        } catch (IOException e) {
            throw new FileException("File exception: " + e);
        }
    }

    @Override
    public void addProgram(PrgState state) {
        programs.add(state);
    }

    @Override
    public void clear() {
        programs.clear();
    }

    public String getLogFilePath() {
        return logFilePath;
    }
}
