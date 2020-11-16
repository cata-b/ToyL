import com.sun.jdi.Value;
import model.expressions.*;
import model.statements.*;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import view.ExitCommand;
import view.RunExampleCommand;
import view.TextMenu;

import java.util.Arrays;

class Interpreter {

    private static IStmt connectStmts(IStmt[] stmts) {
        if (stmts.length == 0)
            return new NopStmt();
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

        var ex6 = connectStmts(new IStmt[] {
                new VarDeclStmt("v", new RefType(new IntType())),
                new NewStmt("v", new ValueExp(new IntValue(20))),
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new NewStmt("a", new VarExp("v")),
                new NewStmt("v", new ValueExp(new IntValue(30))),
                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
        });
        menu.addCommand(new RunExampleCommand("6", ex6.toString(), ex6));

        var ex7 = connectStmts(new IStmt[] {
                new VarDeclStmt("v", new RefType(new IntType())),
                new NewStmt("v", new ValueExp(new IntValue(0))),
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new NewStmt("a", new VarExp("v")),
                new VarDeclStmt("b", new RefType(new RefType(new RefType(new IntType())))),
                new NewStmt("b", new VarExp("a")),
                new WriteHeapStmt("v", new ValueExp(new IntValue(20))),
                new IfStmt(
                        new LogicExp(
                                new RelationalExp(new ReadHeapExp(new ReadHeapExp(new ReadHeapExp(new VarExp("b")))), new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), RelationalExp.Operation.eq),
                                new RelationalExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(20)), RelationalExp.Operation.eq),
                                LogicExp.Operation.and
                        ),
                        new PrintStmt(new ValueExp(new StringValue("References are ok"))),
                        new PrintStmt(new ValueExp(new StringValue("References are not ok"))))
        });
        menu.addCommand(new RunExampleCommand("7", ex7.toString(), ex7));

        var ex8 = connectStmts(new IStmt[] {
                new VarDeclStmt("n", new IntType()),
                new VarDeclStmt("file", new StringType()),
                new AssignStmt("file", new ValueExp(new StringValue("test.in"))),
                new OpenRFileStmt(new VarExp("file")),
                new ReadFileStmt(new VarExp("file"), "n"),
                new CloseRFileStmt(new VarExp("file")),

                new VarDeclStmt("x", new IntType()),
                new VarDeclStmt("y", new IntType()),
                new VarDeclStmt("z", new IntType()),
                new AssignStmt("x", new ValueExp(new IntValue(1))),
                new AssignStmt("y", new ValueExp(new IntValue(1))),
                new WhileStmt(new RelationalExp(new VarExp("y"), new VarExp("n"), RelationalExp.Operation.smeq),
                        connectStmts(new IStmt[] {
                                new AssignStmt("z", new ArithExp(ArithExp.Operation.add, new VarExp("x"), new VarExp("y"))),
                                new AssignStmt("x", new VarExp("y")),
                                new AssignStmt("y", new VarExp("z"))
                        })),
                new PrintStmt(new VarExp("x"))
        });
        menu.addCommand(new RunExampleCommand("8", ex8.toString(), ex8));
        menu.show();
    }
}