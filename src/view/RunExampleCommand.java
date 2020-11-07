package view;

import controller.Controller;
import model.PrgState;
import model.collections.MyDictionary;
import model.collections.MyList;
import model.collections.MyStack;
import model.exceptions.MyException;
import model.statements.IStmt;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunExampleCommand extends Command {
    private final IStmt stmt;

    private Controller createControllerWithProgram(IStmt program, String logfile) {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), program);
        IRepository repo = new Repository(logfile);
        repo.addProgram(state);
        return new Controller(repo);
    }

    public RunExampleCommand(String key, String desc, IStmt stmt) {
        super(key, desc);
        this.stmt = stmt;
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
        var ctr = createControllerWithProgram(stmt.copy(), logfile);
        try {
            ctr.allStep(false);
        } catch (MyException e) {
            System.out.println("Exception occurred during execution: " + e);
        }
    }
}