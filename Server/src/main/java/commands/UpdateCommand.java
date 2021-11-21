package commands;


import exceptions.EmptyCollectionException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import tasck.Coordinates;
import tasck.Ticket;
import tasck.TicketType;
import tasck.Venue;
import utils.Tickets;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private String parameter;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update", "- обновляет элемент в колекции по указанному id");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String parameter, Object objectArgument) {
        try {
            if (parameter.isEmpty() || objectArgument == null) throw new WrongAmountOfParametersException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            long id = Long.parseLong(parameter);
            collectionManager.getFromCollection(id);
            Ticket ticketOld = collectionManager.getFromCollection(id);
            Tickets ticketsLite = (Tickets) objectArgument;
            String name = ticketsLite.getName() == null ? ticketOld.getName() : ticketsLite.getName();
            Coordinates coordinates = ticketsLite.getCoordinates() == null ? ticketOld.getCoordinates() : ticketsLite.getCoordinates();
            ZonedDateTime creationDate = ticketOld.getCreationDate();
            Float price = ticketsLite.getPrice() == -1 ? ticketOld.getPrice() : ticketsLite.getPrice();
            Long discount = ticketsLite.getDiscount() == -1 ? ticketOld.getDiscount() : ticketsLite.getDiscount();
            TicketType ticketType = ticketsLite.getType() == null ? ticketOld.getType() : ticketsLite.getType();
            String comment = ticketsLite.getComment() == null ? ticketOld.getComment() : ticketsLite.getComment();
            Venue venue = ticketsLite.getVenue() == null ? ticketOld.getVenue() : ticketsLite.getVenue();
            collectionManager.removeById(id);
            collectionManager.addToCollection(new Ticket(id, name, coordinates, creationDate, price, discount, comment, ticketType, venue));
            ResponseOutputer.append("Успешно изменено!\n");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            System.out.println("Вместе с этой командой должен быть передан параметр! Наберит 'help' для справки");
        } catch (EmptyCollectionException exception) {
            System.out.println("Коллекция пуста!");
        }
        return false;
    }

    @Override
    public String toString() {
        return "update";
    }
}
