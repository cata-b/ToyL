package CLI;

import model.ExamplePrograms;
import CLI.view.ExitCommand;
import CLI.view.RunExampleCommand;
import CLI.view.TextMenu;

import java.util.AbstractMap;

class Interpreter {

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        int[] index = {1};
        ExamplePrograms.getExamples().getContent().stream()
                .map(ex -> new AbstractMap.SimpleEntry<>(index[0]++, ex))
                .forEach(exp -> menu.addCommand(new RunExampleCommand(exp.getKey().toString(), exp.getValue().toString(), exp.getValue())));
        menu.show();
    }
}