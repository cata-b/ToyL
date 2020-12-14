package CLI.view;

import controller.Controller;
import model.PrgState;
import model.collections.nonObservable.MyDictionary;
import model.collections.nonObservable.MyHeap;
import model.collections.nonObservable.MyList;
import model.collections.nonObservable.MyStack;
import model.exceptions.MyException;
import model.statements.IStmt;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunExampleCommand extends Command {
    private final IStmt stmt;

    public RunExampleCommand(String key, String desc, IStmt stmt) {
        super(key, desc);
        this.stmt = stmt;
    }

    private static Controller createControllerWithProgram(IStmt program, IRepository repository) throws MyException {
        program.typeCheck(new MyDictionary<>());
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), program);
        repository.addProgram(state);
        return new Controller(repository);
    }

    @Override
    public void execute() {
        String logfile;
        System.out.print("Enter logfile name: ");
        try {
            var br = new BufferedReader(new InputStreamReader(System.in));
            logfile = br.readLine();
        } catch (IOException e) {
            System.out.println("Error reading logfile name. Try to run the command again.");
            return;
        }
        Controller ctr;
        try {
            ctr = createControllerWithProgram(stmt.copy(), new Repository(new MyList<>(), logfile));
        } catch (MyException e) {
            System.out.println("Exception has occurred when trying to begin execution: " + e.getMessage());
            return;
        }
        ctr.allStep();
    }
}