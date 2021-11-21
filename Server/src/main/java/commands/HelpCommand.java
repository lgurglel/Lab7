package commands;

import consoleWork.ConsoleWorker;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;


public class HelpCommand extends AbstractCommand {

    private ConsoleWorker consoleWorker;
    private CollectionManager collectionManager;

    public HelpCommand(CollectionManager collectionManager) {
        super("help","- вывести справку по доступным командам");
        this.collectionManager = collectionManager;
    }

    public HelpCommand() {
        super("help","- вывести справку по доступным командам");
    }

    @Override
    public boolean execute(String parameter,Object objectArgument) {
        try {
            if (objectArgument != null) throw new WrongAmountOfParametersException();
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        }
        return false;
    }

    @Override
    public String toString() {
        return "help";
    }
}
