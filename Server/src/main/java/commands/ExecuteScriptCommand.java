package commands;

import exceptions.WrongAmountOfParametersException;
import managers.FileManager;
import managers.CollectionManager;
import managers.ResponseOutputer;


public class ExecuteScriptCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private String parameter;
    private FileManager fileManager;

    public ExecuteScriptCommand(CollectionManager collectionManager, String parameter,FileManager fileManager) {
        super("execute_script","-  считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.parameter = parameter;
    }

    public ExecuteScriptCommand() {
        super("execute_script","-  считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");

    }

    @Override
    public String toString() {
        return "execute_script";
    }

    @Override
    public boolean execute(String parameter,Object objectArgument) {
        try {
            if (parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Вместе с этой командой должен быть передан параметр! Наберит 'help' для справки\n");
        }
        return false;
    }
}
