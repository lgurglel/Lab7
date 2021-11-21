package commands;

import consoleWork.Outputer;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;


public class ClearCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear","- удаляет содержимое коллекции");
        this.collectionManager = collectionManager;
    }

    public ClearCommand() {
    }

    @Override
    public String toString() {
        return "clear";
    }

    @Override
    public boolean execute(String parameter,Object objectArgument) {
        try {
            if(objectArgument != null) throw new WrongAmountOfParametersException();
            collectionManager.clearCollection();
            ResponseOutputer.append("Коллекция успешно очищена!\n");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        }
        return false;
    }
}
