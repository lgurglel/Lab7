package commands;


import exceptions.*;
import managers.CollectionManager;
import managers.DatabaseCollectionManager;
import managers.ResponseOutputer;
import tasck.Coordinates;
import tasck.Ticket;
import tasck.TicketType;
import tasck.Venue;
import tasck.Tickets;
import utils.User;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public UpdateCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("update", "- обновляет элемент в колекции по указанному id");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    @Override
    public boolean execute(String parameter, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (parameter.isEmpty() || objectArgument == null) throw new WrongAmountOfParametersException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            Long id = Long.parseLong(parameter);
            Ticket ticketOld = collectionManager.getFromCollection(id);
            if (ticketOld == null) throw new NotFoundTicketException();
            if (!ticketOld.getUser().equals(user)) throw new PermissionDeniedException();
            if (!databaseCollectionManager.checkTicketByIdAndUserId(ticketOld.getId(), user)) throw new IllegalDatabaseEditException();
            Tickets ticketLite = (Tickets) objectArgument;
            databaseCollectionManager.updateTicketByID(id, ticketLite);
            String name = ticketLite.getName() == null ? ticketOld.getName() : ticketLite.getName();
            Coordinates coordinates = ticketLite.getCoordinates() == null ? ticketOld.getCoordinates() : ticketLite.getCoordinates();
            LocalDateTime creationDate = ticketOld.getCreationDate();
            Float price = ticketLite.getPrice() == -1 ? ticketOld.getPrice() : ticketLite.getPrice();
            Long discount = ticketLite.getDiscount() == -1 ? ticketOld.getDiscount() : ticketLite.getDiscount();
            String comment = ticketLite.getComment() == null ? ticketOld.getComment() : ticketLite.getComment();
            TicketType type = ticketLite.getType() == null ? ticketOld.getType() : ticketLite.getType();
            Venue venue = ticketLite.getVenue() == null ? ticketOld.getVenue() : ticketLite.getVenue();
            collectionManager.removeFromCollection(ticketOld);
            collectionManager.addToCollection( new Ticket(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    price,
                    discount,
                    comment,
                    type,
                    venue,
                    user
            ));
            ResponseOutputer.append("Успешно изменено!\n");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Вместе с этой командой должен быть передан параметр! Наберит 'help' для справки!\n");
        } catch (EmptyCollectionException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (NotFoundTicketException e) {
            ResponseOutputer.append("Ticket с таким id в коллекции нет!\n");
        } catch (PermissionDeniedException e) {
            ResponseOutputer.append("Принадлежащие другим пользователям объекты доступны только для чтения!\n");
        } catch (DatabaseManagerException e) {
            ResponseOutputer.append("Произошла ошибка при обращении к базе данных!\n");
        } catch (IllegalDatabaseEditException e) {
            ResponseOutputer.append("Произошло нелегальное изменение объекта в базе данных!\n");
            ResponseOutputer.append("Перезапустите клиент для избежания ошибок!\n");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }

    @Override
    public String toString() {
        return "update";
    }
}
