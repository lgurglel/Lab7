package commands;

import exceptions.NonAuthorizedUserException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import utils.User;

public class SortCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public SortCommand(CollectionManager collectionManager) {
        super("sort", "- сортирует содержимое коллекции в естественном порядке");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String parameter, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (!parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            collectionManager.sort();
            ResponseOutputer.append("Коллекция отсортированна по умолчанию");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;

    }

    @Override
    public String toString() {
        return "sort";
    }
}
