package commands;

import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import tasck.Ticket;

import java.time.ZonedDateTime;


public class AddCommand extends AbstractCommand {

    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", "- добавляет новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public String toString() {
        return "add";
    }


    @Override
    public boolean execute(String parameter, Object objectArgument) {
        try {
            if (!parameter.isEmpty() || objectArgument == null) throw new WrongAmountOfParametersException();
            Ticket ticket = (Ticket) objectArgument;
            collectionManager.addToCollection(new Ticket(
                    collectionManager.generateId(),
                    ticket.getName(),
                    ticket.getCoordinates(),
                    ZonedDateTime.now(),
                    ticket.getPrice(),
                    ticket.getDiscount(),
                    ticket.getComment(),
                    ticket.getType(),
                    ticket.getVenue()));
            ResponseOutputer.append("Билет успешно добавлен!\n");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Использование: '" + getName() + " " + getDescription() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("Переданный клиентом объект неверен!");
        }
        return false;
    }
}

