package commands;

import exceptions.*;
import managers.CollectionManager;
import managers.DatabaseCollectionManager;
import managers.DatabaseManager;
import managers.ResponseOutputer;
import tasck.Ticket;
import utils.User;


public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id","- удаляет элемент из колекции по введенному id");
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            long id = Long.parseLong(stringArgument);
            Ticket ticketToRemove = collectionManager.getFromCollection(id);
            if (ticketToRemove == null) throw new NotFoundTicketException();
            if (!ticketToRemove.getUser().equals(user)) throw new PermissionDeniedException();
            if (!databaseCollectionManager.checkTicketByIdAndUserId(ticketToRemove.getId(), user)) throw new IllegalDatabaseEditException();
            databaseCollectionManager.deleteTicketById(id);
            collectionManager.removeFromCollection(ticketToRemove);
            ResponseOutputer.append("Билет успешно удален!");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Использование: '" + getName() + " " + getDescription() + "'");
        } catch (EmptyCollectionException exception) {
            ResponseOutputer.appendError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appendError("ID должен быть представлен числом!");
        } catch (NotFoundTicketException exception) {
            ResponseOutputer.appendError("Солдата с таким ID в коллекции нет!");
        } catch (DatabaseManagerException exception) {
            ResponseOutputer.appendError("Произошла ошибка при обращении к базе данных!");
        } catch (PermissionDeniedException exception) {
            ResponseOutputer.appendError("Недостаточно прав для выполнения данной команды!");
            ResponseOutputer.append("Принадлежащие другим пользователям объекты доступны только для чтения.");
        } catch (IllegalDatabaseEditException exception) {
            ResponseOutputer.appendError("Произошло прямое изменение базы данных!");
            ResponseOutputer.append("Перезапустите клиент для избежания возможных ошибок.");
        }catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }
    @Override
    public String toString() {
        return "remove_by_id";
    }
}
