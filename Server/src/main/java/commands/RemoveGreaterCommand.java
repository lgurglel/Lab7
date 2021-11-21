package commands;

import exceptions.EmptyCollectionException;
import exceptions.NotFoundTicketException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import tasck.Ticket;
import utils.Tickets;

import java.time.ZonedDateTime;

public class RemoveGreaterCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private String parameter;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater","- удаляет из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    public RemoveGreaterCommand() {
        super("remove_greater","- удаляет из коллекции все элементы, превышающие заданный");
    }


    @Override
    public boolean execute(String parameter, Object objectArgument) {
        try {
            if (!parameter.isEmpty() || objectArgument == null) throw new WrongAmountOfParametersException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            Tickets ticketLite = (Tickets) objectArgument;
            Ticket ticketToCompare = new Ticket(
                    collectionManager.generateId(),
                    ticketLite.getName(),
                    ticketLite.getCoordinates(),
                    ZonedDateTime.now(),
                    ticketLite.getPrice(),
                    ticketLite.getDiscount(),
                    ticketLite.getComment(),
                    ticketLite.getType(),
                    ticketLite.getVenue());
            collectionManager.removeGreater(ticketToCompare);
                Ticket ticketFromCollection = collectionManager.getByValue(ticketToCompare);
                if (ticketFromCollection == null) throw new NotFoundTicketException();
                collectionManager.removeGreater(ticketFromCollection);
                ResponseOutputer.append("Билеты успешно удалены!");
                return true;
            } catch (WrongAmountOfParametersException exception) {
                ResponseOutputer.append("Использование: '" + getName() + " " + getDescription() + "'");
            } catch (EmptyCollectionException exception) {
                ResponseOutputer.appendError("Коллекция пуста!");
            } catch (NotFoundTicketException exception) {
                ResponseOutputer.appendError("Билета с такими характеристиками в коллекции нет!");
            } catch (ClassCastException exception) {
                ResponseOutputer.appendError("Переданный клиентом объект неверен!");
            }
            return false;

    }

    @Override
    public String toString() {
        return "remove_greater";
    }
}
