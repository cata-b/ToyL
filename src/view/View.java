package view;

import controller.Controller;
import controller.IController;
import model.PrgState;
import model.collections.MyDictionary;
import model.collections.MyList;
import model.collections.MyStack;
import model.exceptions.MyException;
import model.expressions.*;
import model.statements.*;
import model.values.*;
import model.types.*;
import repository.Repository;

import java.util.Scanner;

public class View {
    private static String[] options = {
            "0. Exit",
            "1. Input a program",
            "2. Run the current program"
    };
    private static IStmt[] programs = {
            new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                            new PrintStmt(new VarExp("v")))),
            new CompStmt(new VarDeclStmt("a", new IntType()),
                    new CompStmt(new VarDeclStmt("b", new IntType()),
                            new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
                                    ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                    new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new
                                            ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b")))))),
            new CompStmt(new VarDeclStmt("a",new BoolType()),
                    new CompStmt(new VarDeclStmt("v", new IntType()),
                            new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                    new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                            IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                            new PrintStmt(new VarExp("v"))))))

    };
    private static int currentProgram = -1;
    private static void selectProgram() {
        String ans = "n";
        while (!ans.toLowerCase().equals("y")) {
            currentProgram = (currentProgram + 1) % programs.length;
            System.out.println("Current program: " + programs[currentProgram]);
            System.out.println("Use this one? (Y if yes) ");
            ans = in.nextLine();
        }
    }

    private static void runProgram() {
        System.out.println("Would you like to print the program state at each step? (Y if yes)");
        String ans = in.nextLine().toLowerCase();
        String result = "";
        controller.setProgram(new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), programs[currentProgram]));
        try {
            result = controller.allStep(ans.equals("y"));
        }
        catch (MyException e)
        { System.out.println(e); }
        System.out.println(result);
    }

    private static final IController controller = new Controller(new Repository());
    private static final Scanner in = new Scanner(System.in);


    public static void main(String[] args) {
        int choice = -1;
        while (choice != 0) {
            for (String option: options)
                System.out.println(option);

            try {
                choice = Integer.parseInt(in.nextLine());
            }
            catch (NumberFormatException e) {choice = -1;}

            if (choice < 0 || choice > 2) {
                System.out.println("Invalid choice.");
                continue;
            }

            if (choice == 1)
                selectProgram();
            else if (choice == 2)
                runProgram();
        }
    }
}
