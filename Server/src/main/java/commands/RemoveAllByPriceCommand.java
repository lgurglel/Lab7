package commands;

import exceptions.*;
import managers.CollectionManager;
import managers.DatabaseCollectionManager;
import managers.ResponseOutputer;
import tasck.Ticket;
import utils.User;

import java.util.List;

public class RemoveAllByPriceCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveAllByPriceCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_all_by_price", "- удаляет из коллекции все элементы, значение поля price которого эквивалентно заданному");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    @Override
    public boolean execute(String parameter, Object objectArgument, User user) {
       try {
           if (user == null) throw new NonAuthorizedUserException();
           if (user == null) throw new NonAuthorizedUserException();
            if (parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            Float price = Float.valueOf(parameter);
            List<Ticket> tickets = collectionManager.getAllByPrice(price);
            for (Ticket ticket : tickets) {
                if (!ticket.getUser().equals(user)) continue;
                if (!databaseCollectionManager.checkTicketByIdAndUserId(ticket.getId(), user)) throw new IllegalDatabaseEditException();
                databaseCollectionManager.deleteTicketById(ticket.getId());
                collectionManager.removeById(ticket.getId());
            }
            ResponseOutputer.append("Все билеты с ценой " + parameter + " успешно удалены!\n");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Вместе с этой командой должен быть передан параметр! Наберит 'help' для справки\n");
        } catch (EmptyCollectionException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (DatabaseManagerException e) {
            ResponseOutputer.append("Произошла ошибка при обращении к базе данных!\n");
        } catch (IllegalDatabaseEditException exception) {
            ResponseOutputer.append("Произошло нелегальное изменение объекта в базе данных!\n");
            ResponseOutputer.append("Перезапустите клиент для избежания ошибок!\n");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }

    @Override
    public String toString() {
        return "remove_all_by_price";
    }
}
