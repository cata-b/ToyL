package repository;

import model.PrgState;
import model.collections.interfaces.MyIList;
import model.collections.nonObservable.MyList;
import model.exceptions.FileException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.List;

public class Repository implements IRepository {
    private final MyIList<PrgState> programs;
    private final String logFilePath;

    public Repository(MyIList<PrgState> underlyingList, String logFilePath) throws FileException {
        this.logFilePath = logFilePath;
        programs = underlyingList;
        try (var logFile = new FileWriter(logFilePath, true)) {}
        catch (IOException e) {
            throw new FileException("Log file could not be accessed");
        }
    }

    public MyIList<PrgState> getPrgList() {
        return programs;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        programs.setContent(list);
    }

    @Override
    public void logPrgStateExec(@NotNull PrgState state) {
        try (var logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
                logFile.println("--------------------------------");
                logFile.println(state);
        } catch (IOException ignored) {}
    }

    @Override
    public void logPrgError(String message) {
        try (var logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println("--------------------------------");
            logFile.println("A thread encountered an exception: " + System.lineSeparator() + message);
        } catch (IOException ignored) {}
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
