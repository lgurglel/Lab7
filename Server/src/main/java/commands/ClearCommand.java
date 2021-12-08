package commands;

import consoleWork.Outputer;
import exceptions.*;
import managers.CollectionManager;
import managers.DatabaseCollectionManager;
import managers.ResponseOutputer;
import tasck.Ticket;
import utils.User;


public class ClearCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public ClearCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            databaseCollectionManager.clearCollection(user);
            collectionManager.clearCollection(user);
            ResponseOutputer.append("Коллекция очищена!");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Использование: '" + getName() + " " + getDescription() + "'");
        } catch (DatabaseManagerException exception) {
            ResponseOutputer.appendError("Произошла ошибка при обращении к базе данных!");
        }catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }
}
