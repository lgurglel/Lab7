package commands;

import exceptions.DatabaseManagerException;
import exceptions.NonAuthorizedUserException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.DatabaseCollectionManager;
import managers.ResponseOutputer;
import tasck.Tickets;
import utils.User;


public class AddCommand extends AbstractCommand {

    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public AddCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("add", "{element} добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    @Override
    public String toString() {
        return "add";
    }


    @Override
    public boolean execute(String parameter, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (!parameter.isEmpty() || objectArgument == null) throw new WrongAmountOfParametersException();
            Tickets tickets = (Tickets) objectArgument;
            collectionManager.addToCollection(databaseCollectionManager.insertTicket(tickets, user));
            ResponseOutputer.append("Билет успешно добавлен!");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Использование: '" + getName() + " " + getDescription() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("Переданный клиентом объект неверен!");
        } catch (DatabaseManagerException exception) {
            ResponseOutputer.appendError("Произошла ошибка при обращении к базе данных!");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }
}

