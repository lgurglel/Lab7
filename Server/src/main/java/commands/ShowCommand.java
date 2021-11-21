package commands;

import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;


public class ShowCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show","- показывает содержимое коллекции");
        this.collectionManager = collectionManager;
    }

    public ShowCommand() {

    }

    @Override
    public boolean execute(String parameter, Object objectArgument) {
        try {
            if (!parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            ResponseOutputer.append(collectionManager.showCollection() + "\n");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        }
        return false;
    }

    @Override
    public String toString() {
        return "show";
    }
}
