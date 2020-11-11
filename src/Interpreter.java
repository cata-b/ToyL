import model.expressions.ArithExp;
import model.expressions.RelationalExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.statements.*;
import model.types.IntType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import view.ExitCommand;
import view.RunExampleCommand;
import view.TextMenu;

import java.util.Arrays;

class Interpreter {

    private static IStmt connectStmts(IStmt[] stmts) {
        if (stmts.length == 1)
            return stmts[0];
        return Arrays.stream(stmts).skip(2).reduce(new CompStmt(stmts[0], stmts[1]), CompStmt::new, CompStmt::new);
    }

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        var ex1 = connectStmts(new IStmt[]{
                new VarDeclStmt("varf", new StringType()),
                new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                new OpenRFileStmt(new VarExp("varf")),
                new VarDeclStmt("varc", new IntType()),
                new ReadFileStmt(new VarExp("varf"), "varc"),
                new PrintStmt(new VarExp("varc")),
                new ReadFileStmt(new VarExp("varf"), "varc"),
                new PrintStmt(new VarExp("varc")),
                new CloseRFileStmt(new VarExp("varf"))
        });
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), ex1));

        var ex2 = connectStmts(new IStmt[]{
                new VarDeclStmt("a", new IntType()),
                new VarDeclStmt("b", new IntType()),
                new VarDeclStmt("file", new StringType()),
                new AssignStmt("file", new ValueExp(new StringValue("test.in"))),
                new OpenRFileStmt(new VarExp("file")),
                new ReadFileStmt(new VarExp("file"), "a"),
                new ReadFileStmt(new VarExp("file"), "b"),
                new CloseRFileStmt(new VarExp("file")),
                new PrintStmt(new VarExp("a")),
                new PrintStmt(new ValueExp(new StringValue("is"))),
                new IfStmt(
                        new RelationalExp(new VarExp("a"), new VarExp("b"), RelationalExp.Operation.sm),
                        new PrintStmt(new ValueExp(new StringValue("smaller than"))),
                        new PrintStmt(new ValueExp(new StringValue("greater than or equal to")))
                ),
                new PrintStmt(new VarExp("b"))
        });
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), ex2));

        var ex3 = connectStmts(new IStmt[]{

                new VarDeclStmt("n", new IntType()),
                new VarDeclStmt("file", new StringType()),
                new AssignStmt("file", new ValueExp(new StringValue("test.in"))),
                new OpenRFileStmt(new VarExp("file")),
                new ReadFileStmt(new VarExp("file"), "n"),
                new CloseRFileStmt(new VarExp("file")),
                new PrintStmt(new VarExp("n"))
        });
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), ex3));

        var ex4 = connectStmts(new IStmt[]{
                new VarDeclStmt("v", new IntType()),
                new AssignStmt("v", new ValueExp(new IntValue(2))),
                new PrintStmt(new VarExp("v"))
        });
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), ex4));

        var ex5 = connectStmts(new IStmt[]{
                new VarDeclStmt("a", new IntType()),
                new VarDeclStmt("b", new IntType()),
                new AssignStmt("a",
                        new ArithExp(ArithExp.Operation.add,
                                new ValueExp(new IntValue(2)),
                                new ArithExp(ArithExp.Operation.mul,
                                        new ValueExp(new IntValue(3)),
                                        new ValueExp(new IntValue(5))))),
                new AssignStmt("b",
                        new ArithExp(ArithExp.Operation.add,
                                new VarExp("a"),
                                new ValueExp(new IntValue(1)))),
                new PrintStmt(new VarExp("b"))
        });
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), ex5));

        menu.show();
    }
}