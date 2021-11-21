package commands;

import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;

public class SortCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public SortCommand(CollectionManager collectionManager) {
        super("sort","- сортирует содержимое коллекции в естественном порядке");
        this.collectionManager = collectionManager;
    }

    public SortCommand() {
    }

    @Override
    public boolean execute(String parameter, Object objectArgument) {
        try {
            if (!parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            collectionManager.sort();
            ResponseOutputer.append("Коллекция отсортированна по умолчанию");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        }catch (Exception e){
            ResponseOutputer.append("Что-то не так");
        }
        return false;

    }

    @Override
    public String toString() {
        return "sort";
    }
}
