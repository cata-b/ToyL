package repository;

import model.PrgState;
import model.collections.MyIList;
import model.collections.MyList;
import model.exceptions.FileException;
import model.exceptions.IndexOutOfBoundsException;
import model.exceptions.MyException;
import model.exceptions.NoProgramLoadedException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.List;

public class Repository implements IRepository {
    private final MyIList<PrgState> programs = new MyList<>();
    private final String logFilePath;

    public Repository(String logFilePath) throws FileException {
        this.logFilePath = logFilePath;
        try (var logFile = new FileWriter(logFilePath, true)) {}
        catch (IOException e) {
            throw new FileException("Log file could not be accessed");
        }
    }

    public List<PrgState> getPrgList() {
        return programs.getContent();
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
