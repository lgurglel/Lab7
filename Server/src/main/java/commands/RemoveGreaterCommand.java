package commands;

import exceptions.*;
import managers.CollectionManager;
import managers.DatabaseCollectionManager;
import managers.ResponseOutputer;
import tasck.Ticket;
import tasck.Tickets;
import utils.User;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class RemoveGreaterCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_greater","- удаляет из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }


    @Override
    public boolean execute(String parameter, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (!parameter.isEmpty() || objectArgument == null) throw new WrongAmountOfParametersException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            Tickets ticketLite = (Tickets) objectArgument;
            Ticket ticketToCompare = new Ticket(
                    collectionManager.generateId(),
                    ticketLite.getName(),
                    ticketLite.getCoordinates(),
                    LocalDateTime.now(),
                    ticketLite.getPrice(),
                    ticketLite.getDiscount(),
                    ticketLite.getComment(),
                    ticketLite.getType(),
                    ticketLite.getVenue(),
                    user);
            List<Ticket> tickets = collectionManager.getGreater(ticketToCompare);
            for (Ticket ticket : tickets) {
                if (!ticket.getUser().equals(user)) continue;
                if (!databaseCollectionManager.checkTicketByIdAndUserId(ticket.getId(), user))
                    throw new IllegalDatabaseEditException();
                databaseCollectionManager.deleteTicketById(ticket.getId());
                collectionManager.removeById(ticket.getId());
            }
            ResponseOutputer.append("Билеты успешно удалены!\n");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
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
        return "remove_greater";
    }
}
