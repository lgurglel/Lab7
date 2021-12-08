package commands;

import exceptions.NonAuthorizedUserException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import utils.User;


public class ShowCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show","- показывает содержимое коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String parameter, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (!parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            ResponseOutputer.append(collectionManager.showCollection() + "\n");
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
        return "show";
    }
}
